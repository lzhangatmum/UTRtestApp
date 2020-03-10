package com.example.github.utrtestapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.github.utrtestapp.R
import com.example.github.utrtestapp.bean.InfoBean
import com.example.github.utrtestapp.bean.TotleBean
import kotlinx.android.synthetic.main.fragment_item.view.*

class MyFollowerRecyclerViewAdapter(var data: ArrayList<Long>) : RecyclerView.Adapter<MyFollowerRecyclerViewAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.first.text= data[position].toString()

    }


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view){
        val first: TextView = view.first



    }
}