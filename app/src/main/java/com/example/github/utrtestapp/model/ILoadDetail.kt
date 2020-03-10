package com.example.github.utrtestapp.model

import com.example.github.utrtestapp.bean.*
import com.example.github.utrtestapp.listener.BaseLoadListener
import io.reactivex.Observable
import retrofit2.http.GET

interface ILoadDetail {

    fun loadResult():TotleBean

}