package com.example.github.utrtestapp.ui.fragment

import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.github.utrtestapp.R
import com.example.github.utrtestapp.adapter.MyFollowerRecyclerViewAdapter
import com.example.github.utrtestapp.adapter.MyItemRecyclerViewAdapter
import com.example.github.utrtestapp.bean.TotleBean
import com.example.github.utrtestapp.utils.DummyContent
import java.util.*
import kotlin.collections.ArrayList

class ItemFragment() : Fragment() {

    var list :ArrayList<Long> = ArrayList();
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_item_list, container, false)
        if (view is RecyclerView) {
            val context = view.getContext()
            var recyclerView = view as RecyclerView
             recyclerView.setLayoutManager(LinearLayoutManager(context))
            recyclerView.setAdapter(MyItemRecyclerViewAdapter(DummyContent.ITEMS))

        }
        return view
    }



}