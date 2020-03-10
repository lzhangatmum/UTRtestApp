package com.example.github.utrtestapp.listener

import com.example.github.utrtestapp.bean.TotleBean

interface BaseLoadListener<T> {
    fun loadSuccess(bean: T)
    fun loadFailure(message: String)
    fun loadStart()
    fun loadComplete()
}