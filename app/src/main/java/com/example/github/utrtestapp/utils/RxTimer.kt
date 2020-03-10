package com.example.github.utrtestapp.utils

import android.content.Context
import android.util.AttributeSet
import  io.reactivex.disposables.Disposable
import  io.reactivex.Observable
import  io.reactivex.Observer
import  io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

open class RxTimer(){

    lateinit var subscribe: Disposable

     fun interval(milliseconds : Long, next : IRxNext){
         subscribe =  Observable.interval(milliseconds,TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                next.doNext()
            }
    }

     fun stop() {
        subscribe.dispose()//取消订阅
        return
    }

    interface  IRxNext{

        fun doNext()
    }



}