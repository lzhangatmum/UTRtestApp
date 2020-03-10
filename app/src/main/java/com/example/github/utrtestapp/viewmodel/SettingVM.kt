package com.example.github.utrtestapp.viewmodel

import android.util.Log
import androidx.fragment.app.Fragment
import com.example.github.utrtestapp.bean.BaseBean
import com.example.github.utrtestapp.bean.TotleBean
import com.example.github.utrtestapp.listener.BaseLoadListener
import com.example.github.utrtestapp.model.LoadDetailImp
import com.example.github.utrtestapp.view.ISettingView

class SettingVM : BaseLoadListener<TotleBean>{


    constructor(fragments:List<Fragment> ){


    }

    override fun loadSuccess(bean: TotleBean) {

    }


    override fun loadFailure(message: String) {
        Log.e("SettingVM",message)
    }

    override fun loadStart() {

    }

    override fun loadComplete() {

    }

}