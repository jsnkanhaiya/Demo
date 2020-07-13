package com.example.demo.ui.main.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demo.R
import com.example.demo.data.model.ApodResponse
import com.example.demo.local.Entities.ApodEntity
import kotlinx.android.synthetic.main.movie_row_item.view.*

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    val apodItemList: ArrayList<ApodResponse> by lazy { ArrayList<ApodResponse>() }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun loadData(item: ApodResponse) {

            Glide.with(itemView)  //2
                .load(item.hdurl) //3
                .centerCrop() //4
                .placeholder(R.drawable.placeholder_for_missing_posters) //5
                .error(R.drawable.placeholder_for_missing_posters) //6
                .fallback(R.drawable.placeholder_for_missing_posters) //7
                .into(itemView.imgMovie)

            itemView.tvTitle.text = item.title
            itemView.tvDate.text = item.date
            itemView.tvDetail.text = item.explanation
            itemView.tvDetail.addShowMoreText("More")
            itemView.tvDetail.setShowMoreColor(Color.WHITE); // or other color
            itemView.tvDetail.setShowLessTextColor(Color.WHITE); // or other color
            itemView.tvDetail.setShowingLine(2)


            /*   itemView.tvTitle?.text = item.name

               when (item.posterImage) {
                   "poster1.jpg" -> itemView.imgMovie.setImageResource(R.drawable.poster1)
                   "poster2.jpg" -> itemView.imgMovie.setImageResource(R.drawable.poster2)
                   "poster3.jpg" -> itemView.imgMovie.setImageResource(R.drawable.poster3)
                   "poster4.jpg" -> itemView.imgMovie.setImageResource(R.drawable.poster4)
                   "poster5.jpg" -> itemView.imgMovie.setImageResource(R.drawable.poster5)
                   "poster6.jpg" -> itemView.imgMovie.setImageResource(R.drawable.poster6)
                   "poster7.jpg" -> itemView.imgMovie.setImageResource(R.drawable.poster7)
                   "poster8.jpg" -> itemView.imgMovie.setImageResource(R.drawable.poster8)
                   "poster9.jpg" -> itemView.imgMovie.setImageResource(R.drawable.poster9)
                   else ->
                       itemView.imgMovie.setImageResource(R.drawable.placeholder_for_missing_posters)
               }*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.movie_row_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return apodItemList.size
    }

    fun addItem(listItem: ArrayList<ApodResponse>) {
        apodItemList.addAll(listItem)
        notifyDataSetChanged()
    }

    fun clearData() {
        apodItemList.clear()
    }

    fun getItem(): ArrayList<ApodResponse> {
        return apodItemList;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        apodItemList.get(position)?.let { holder.loadData(it) }
    }
}