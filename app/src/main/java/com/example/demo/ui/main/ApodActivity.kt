package com.example.demo.ui.main

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.R
import com.example.demo.base.ViewModelProviderFactory
import com.example.demo.data.model.ApodResponse
import com.example.demo.data.model.ContentItem
import com.example.demo.data.model.Movie
import com.example.demo.local.Entities.ApodEntity
import com.example.demo.ui.main.adapters.MovieListAdapter
import com.example.demo.util.MyConnection.Companion.isNetworkAvailable
import com.example.demo.viewModel.ApodListViewModel
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class ApodActivity : DaggerAppCompatActivity() {

    private var apodListViewModel: ApodListViewModel? = null
    val apodList: ArrayList<ApodResponse>? by lazy { ArrayList<ApodResponse>() }
    val apodListEntity: ArrayList<ApodEntity>? by lazy { ArrayList<ApodEntity>() }
    var layoutManager: LinearLayoutManager? = null
    var cal = Calendar.getInstance()
    var strDate: String = ""


    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var movieListAdapter: MovieListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBarValue()

        apodListViewModel =
            ViewModelProvider(this, providerFactory).get(ApodListViewModel::class.java)

        layoutManager = LinearLayoutManager(this)

        moviesRecyclerView.layoutManager = layoutManager
        moviesRecyclerView.adapter = movieListAdapter

        observeData()
        progress.visibility = View.VISIBLE

        apodListViewModel?.getData()


        btnFab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val location = IntArray(2)
                getDataFromDate()
            }
        })

    }


    private fun getDataFromDate() {
        val mcurrentDate = Calendar.getInstance()
        val year = mcurrentDate[Calendar.YEAR]
        val month = mcurrentDate[Calendar.MONTH]
        val day = mcurrentDate[Calendar.DAY_OF_MONTH]


        val datePikker = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                //   var strdate =""+year+"-"+month+"-"+dayOfMonth
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "yyyy-MM-dd" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                strDate = sdf.format(cal.getTime())
                apodListViewModel?.getDataFromDate(strDate)

            }
        }, year, month, day)
        datePikker.getDatePicker().setMaxDate(System.currentTimeMillis())
        datePikker.show()
    }


    private fun setActionBarValue() {
        setSupportActionBar(toolbar)
        toolbar.title = resources.getString(R.string.toolHead)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
    }


    fun observeData() {
        apodListViewModel?.strtext?.observe(this, Observer {

            Toast.makeText(this, it, Toast.LENGTH_LONG).show()

        })

        apodListViewModel?.errorMessageLiveData?.observe(this, Observer {
            hideProgressBar()
            showToast("Something went wrong")
        })

        apodListViewModel?.apodListResponse?.observe(this, Observer {
            if (it.size == 0) {



                if (isNetworkAvailable(this)) {
                    apodListViewModel?.parseJsonApodData()
                } else {
                    hideProgressBar()
                    showToast(resources.getString(R.string.InterNetError))
                }

            } else {
                if (it.size >= 30) {
                    apodList?.clear()
                    for (item in it) {

                        apodList?.add(
                            ApodResponse(
                                item.date ?: "",
                                item.copyright ?: "",
                                item.mediaType ?: "",
                                item.hdurl ?: "",
                                item.serviceVersion ?: "",
                                item.explanation ?: "",
                                item.title ?: "",
                                item.url ?: ""
                            )
                        )
                    }
                    movieListAdapter.addItem(apodList!!)
                    hideProgressBar()
                }

            }
        })

        apodListViewModel?.apodListResponseFromDate?.observe(this, Observer {
            if (it.size > 0) {
                Toast.makeText(this, "Data already in list", Toast.LENGTH_SHORT).show()
            } else {
                if (isNetworkAvailable(this)) {
                    progress.visibility = View.VISIBLE
                    apodListViewModel?.getDataFromDateNetwork(strDate)
                } else {
                    hideProgressBar()
                    showToast(resources.getString(R.string.InterNetError))
                }
            }
        })

        apodListViewModel?.apodResponseFronSingleDate?.observe(this, Observer {
            apodList?.clear()
            apodList?.add(it)
            apodList?.let {
                apodListEntity?.clear()
                movieListAdapter.addItem(it)
                for (item in apodList!!) {
                    apodListEntity?.add(
                        ApodEntity(
                            item.date ?: "",
                            item.copyright ?: "",
                            item.mediaType ?: "",
                            item.hdurl ?: "",
                            item.serviceVersion ?: "",
                            item.explanation ?: "",
                            item.title ?: "",
                            item.url ?: ""
                        )
                    )
                }
                apodListViewModel?.storeDataInLocalDb(apodListEntity!!)
                hideProgressBar()
            }
        })

        apodListViewModel?.apodResponse?.observe(this, Observer {

            apodList?.add(it)
            //apodListEntity?.add(ApodEntity(it.date!!, it.copyright!!,it.mediaType!!,it.hdurl!!,it.serviceVersion!!,it.explanation!!,it.title!!,it.url!!))

            apodList?.let { it1 ->
                if (apodList!!.size == 30) {
                    movieListAdapter.addItem(it1)
                    for (item in apodList!!) {
                        apodListEntity?.add(
                            ApodEntity(
                                item.date ?: "",
                                item.copyright ?: "",
                                item.mediaType ?: "",
                                item.hdurl ?: "",
                                item.serviceVersion ?: "",
                                item.explanation ?: "",
                                item.title ?: "",
                                item.url ?: ""
                            )
                        )
                    }

                    apodListViewModel?.storeDataInLocalDb(apodListEntity!!)

                }

            }

            hideProgressBar()
        })
    }

    private fun hideProgressBar() {
        progress.visibility = View.GONE
    }

    private fun showToast(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

}

