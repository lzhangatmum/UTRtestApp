package com.example.github.utrtestapp.view

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.example.github.utrtestapp.adapter.MyFragmentPagerAdapter
import com.example.github.utrtestapp.listener.CustomTabEntity
import com.example.github.utrtestapp.widget.CommonTabLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.jaeger.library.StatusBarUtil
import java.util.ArrayList

class SettingViewImp() : ISettingView {
    override fun initStatus(activty: Activity, height: Int, titleContainer: ViewGroup) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setTransparentForImageView(activty, null)
            val statusBarHeight = height
            val lp1 = titleContainer.getLayoutParams() as CollapsingToolbarLayout.LayoutParams
            lp1.topMargin = statusBarHeight
        }
    }


    override fun initTab(
        fragmentManager: FragmentManager,
        fragments: List<Fragment>,
        names: Array<String>,
        mTabEntities: ArrayList<CustomTabEntity>,
        mViewPager: ViewPager,
        tabLayout: CommonTabLayout

    ) {
        val myFragmentPagerAdapter =
            MyFragmentPagerAdapter(fragmentManager, fragments, names)

        tabLayout.setTabData(mTabEntities)
        mViewPager.setAdapter(myFragmentPagerAdapter)
    }

}