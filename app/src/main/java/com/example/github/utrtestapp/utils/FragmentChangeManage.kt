package com.example.github.utrtestapp.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentChangeManage {
    var fm:FragmentManager ? = null;
    var containerViewId : Int? = null;
    var fragments: ArrayList<Fragment> = ArrayList();
     var mCurrentTab: Int = 0

    constructor(fm: FragmentManager,containerViewId :Int,fragments :ArrayList<Fragment>){
        this.fm = fm;
        this.containerViewId = containerViewId;
        this.fragments.addAll(fragments)
        initFragments()
    }


    fun initFragments(){
        for (fragment in fragments) {
            containerViewId?.let {
                fm!!.beginTransaction().add(it, fragment).hide(fragment)
                    .commit()
            }
        }
        setFragments(0)
    }


    fun setFragments(index : Int){
        for (i in fragments.indices) {
            val ft = fm!!.beginTransaction()
            val fragment = fragments.get(i)
            if (i == index) {
                ft.show(fragment)
            } else {
                ft.hide(fragment)
            }
            ft.commit()
        }
        mCurrentTab = index
    }
}