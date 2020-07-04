package com.example.demo.viewModel

import android.content.res.AssetManager
import androidx.lifecycle.MutableLiveData
import com.example.demo.base.BaseViewModel
import com.example.demo.data.model.Movie
import com.google.gson.Gson
import java.io.IOException
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val gson: Gson,
    private val assetManager: AssetManager
)  : BaseViewModel() {




    val strtext: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val movieList: MutableLiveData<ArrayList<Movie>> by lazy { MutableLiveData<ArrayList<Movie>>() }

    fun showToast(){
        strtext.value="Hello Anil"
    }

    fun parseJsonMovieData(){
      val strdata =  getJsonDataFromAsset("PAGE1.json")
        movieList.value = ArrayList();
    }

    fun getJsonDataFromAsset(fileName: String): String? {
        val jsonString: String
        try {
            jsonString = assetManager.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

}