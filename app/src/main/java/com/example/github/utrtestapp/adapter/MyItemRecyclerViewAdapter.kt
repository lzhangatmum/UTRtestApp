package com.example.github.utrtestapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.github.utrtestapp.R
import com.example.github.utrtestapp.utils.DummyContent
import kotlinx.android.synthetic.main.fragment_item.view.*

class MyItemRecyclerViewAdapter(private val mValues: List<DummyContent.DummyItem>) :
    RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return  mValues.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.first.setText(mValues[position].id)
        holder.last.setText(mValues[position].content)
    }

     class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val first: TextView
        val last: TextView
        var mItem: DummyContent.DummyItem? = null

        init {
            first = mView.first as TextView
            last = mView.last as TextView
        }

    }
}
