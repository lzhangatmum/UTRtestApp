package com.example.github.utrtestapp.view

import android.app.Activity
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.example.github.utrtestapp.listener.CustomTabEntity
import com.example.github.utrtestapp.widget.CommonTabLayout
import java.util.ArrayList

interface ISettingView {

    fun initTab(
        fragmentManager: FragmentManager,
        fragments: List<Fragment>,
        names: Array<String>,
        mTabEntities: ArrayList<CustomTabEntity>,
        mViewPager: ViewPager,
        tabLayout: CommonTabLayout
    )

    fun initStatus(activty: Activity, height: Int, titleContainer: ViewGroup)

}