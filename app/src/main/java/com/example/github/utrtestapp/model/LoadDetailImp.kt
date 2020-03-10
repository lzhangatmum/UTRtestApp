package com.example.github.utrtestapp.model

import android.annotation.SuppressLint
import android.util.Log
import com.example.github.utrtestapp.bean.*
import com.example.github.utrtestapp.http.HttpUtils
import com.example.github.utrtestapp.listener.BaseLoadListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class LoadDetailImp :ILoadDetail{

    var totleBean:TotleBean = TotleBean();
    var index :Int = 0;

    override fun loadResult():TotleBean{

        Observable.concat(HttpUtils.create().loadFollower().subscribeOn(Schedulers.io())
            ,HttpUtils.create().loadRecevied().subscribeOn(Schedulers.io())
            ,HttpUtils.create().loadRepos().subscribeOn(Schedulers.io())
            ,HttpUtils.create().loadSubscriptions().subscribeOn(Schedulers.io())
            ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<ArrayList<InfoBean>>(){
                override fun onComplete() {

                }


                override fun onNext(result: ArrayList<InfoBean>) {
                    when(index){
                        1 ->totleBean.followersBean.addAll(result)
                        2->totleBean.subscriptionsBean .addAll(result)
                        3-> totleBean.reposBean .addAll(result)
                        4-> totleBean.receivedBean .addAll(result)
                    }
                    index++
                }



                override fun onError(e: Throwable) {
                    Log.e("error",e.message)
                }

                override fun onStart() {

                }

            })
        return totleBean;


//        val observable1 = HttpUtils.create().loadFollower().subscribeOn(Schedulers.io())
//        val observable2 = HttpUtils.create().loadRecevied().subscribeOn(Schedulers.io())
//        val observable3 = HttpUtils.create().loadRepos().subscribeOn(Schedulers.io())
//        val observable4 = HttpUtils.create().loadSubscriptions().subscribeOn(Schedulers.io())


    }


}