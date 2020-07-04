package com.example.demo.ui.main.adapters

import android.service.autofill.TextValueSanitizer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import kotlinx.android.synthetic.main.movie_row_item.view.*

class MovieListAdapter  : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        fun loadData(){
            itemView.tvTitle?.text="Love Song"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.movie_row_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
       return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.loadData()
    }
}