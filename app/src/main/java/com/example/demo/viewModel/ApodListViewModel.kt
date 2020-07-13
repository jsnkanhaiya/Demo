package com.example.demo.viewModel

import android.content.res.AssetManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.demo.base.BaseViewModel
import com.example.demo.data.model.ApodResponse
import com.example.demo.data.model.Movie
import com.example.demo.local.Entities.ApodEntity
import com.example.demo.local.dao.ApodDao
import com.example.demo.util.APODKEY
import com.google.gson.Gson
import com.example.demo.network.ApiService
import com.example.demo.util.BASEURL
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class ApodListViewModel @Inject constructor(
    private val gson: Gson,
    private val apodDao: ApodDao,
    private val apiService: ApiService,
    private val assetManager: AssetManager
) : BaseViewModel() {


    val dateList: ArrayList<String> by lazy { ArrayList<String>() }
    val strtext: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val movieList: MutableLiveData<Movie> by lazy { MutableLiveData<Movie>() }
    val apodResponse: MutableLiveData<ApodResponse> by lazy { MutableLiveData<ApodResponse>() }
    val apodResponseFronSingleDate: MutableLiveData<ApodResponse> by lazy { MutableLiveData<ApodResponse>() }
    val apodListResponse: MutableLiveData<ArrayList<ApodEntity>> by lazy { MutableLiveData<ArrayList<ApodEntity>>() }
    val apodListResponseFromDate: MutableLiveData<ArrayList<ApodEntity>> by lazy { MutableLiveData<ArrayList<ApodEntity>>() }
    val errorMessageLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun getDataFromDb(): Single<ArrayList<ApodEntity>> {
        return Single.create { emitter ->
            apodDao.getApodAllData().subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.isNotEmpty()) {
                        emitter.onSuccess(ArrayList(it))
                    } else
                        emitter.onSuccess(ArrayList())
                }, {
                    emitter.onSuccess(ArrayList())
                })
        }
    }

    fun isDataAvailableInDb(strDate: String): Single<ArrayList<ApodEntity>> {

        return Single.create { emitter ->
            apodDao.isDataAvailable(strDate).subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.isNotEmpty()) {
                        emitter.onSuccess(ArrayList(it))
                    } else
                        emitter.onSuccess(ArrayList())
                }, {
                    emitter.onSuccess(ArrayList())
                })
        }

    }

    fun getDataFromDate(strDate: String) {
        compositeDisposable.add(
            isDataAvailableInDb(strDate).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    apodListResponseFromDate.value = it
                }, {
                    errorMessageLiveData.value=true
                })
        )
    }


    fun getData() {
        compositeDisposable.add(
            getDataFromDb().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    apodListResponse.value = it
                }, {
                })
        )

    }

    fun storeDataInLocalDb(apodList: ArrayList<ApodEntity>) {
        compositeDisposable.add(
            getcompletableObsev(apodList).subscribeOn(Schedulers.io()).subscribe {

            }
        )
    }

    fun parseJsonApodData() {

        compositeDisposable.add(
            getDateObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { items ->
                    items.map {
                        apiService.getAOPDData(BASEURL, it, false, APODKEY)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                                apodResponse.value = it
                            }, {
                                errorMessageLiveData.value=true
                                Log.d("Error", it.message)
                            })
                    }
                }.subscribe({}, {
                    errorMessageLiveData.value=true
                    Log.d("Error", it.message)
                })
        )

    }

    fun getDataFromDateNetwork(strDate: String) {

        compositeDisposable.add(
            apiService.getAOPDData(BASEURL, strDate, false, APODKEY).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    apodResponseFronSingleDate.value = it
            },{
                    errorMessageLiveData.value=true
                })
        )

    }


    fun getDateObservable(): Observable<ArrayList<String>> {

        var dateListItem = getLastDaysDate()

        return Observable.create {
            it.onNext(dateListItem)
        }

    }

    fun getcompletableObsev(apodList: ArrayList<ApodEntity>): Completable {
        return Completable.create {
            apodDao.insertApodAllData(apodList)
        }
    }


    fun getLastDaysDate(): ArrayList<String> {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())
        dateList.add(currentDate.toString())

        for (item: Int in 1..29) {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, -item)
            val date = sdf.format(cal.time)
            dateList.add(date)
        }
        return dateList
    }

}