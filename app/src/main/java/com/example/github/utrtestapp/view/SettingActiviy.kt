package com.example.github.utrtestapp.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.github.utrtestapp.R
import com.example.github.utrtestapp.adapter.MyFollowerRecyclerViewAdapter
import com.example.github.utrtestapp.adapter.MyFragmentPagerAdapter
import com.example.github.utrtestapp.base.BaseActivity
import com.example.github.utrtestapp.bean.TotleBean
import com.example.github.utrtestapp.listener.CustomTabEntity
import com.example.github.utrtestapp.listener.OnTabSelectListener
import com.example.github.utrtestapp.model.LoadDetailImp
import com.example.github.utrtestapp.ui.fragment.ItemFragment
import com.example.github.utrtestapp.ui.fragment.TabEntity
import com.example.github.utrtestapp.utils.SharedPreferencesUtils
import com.example.github.utrtestapp.viewmodel.SettingVM
import com.example.github.utrtestapp.widget.AppBarLayoutOverScrollViewBehavior
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.layout_uc_content.*
import kotlinx.android.synthetic.main.layout_uc_head_middle.*
import kotlinx.android.synthetic.main.layout_uc_head_title.*
import kotlinx.android.synthetic.main.setting_activity.*
import java.util.ArrayList
import kotlin.math.log

class SettingActiviy : BaseActivity() {

    var mTabEntities = ArrayList<CustomTabEntity>()



    var lastState: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_activity)

        initListener()
        initTab();
        initStatus()

//        SettingViewImp().initTab(
//            getSupportFragmentManager(),
//            getFragments(),
//            getNames(),
//            mTabEntities,
//            uc_viewpager,
//            uc_tablayout
//        )

//        SettingViewImp().initStatus(this, getStatusBarHeight(this), title_layout)


    }

    fun initStatus(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setTransparentForImageView(this, null)
           val statusBarHeight = getStatusBarHeight(this)
            val lp1 = title_layout.getLayoutParams() as CollapsingToolbarLayout.LayoutParams
            lp1.topMargin = statusBarHeight
            title_layout.setLayoutParams(lp1)
            val lp2 = toolbar_layout.getLayoutParams() as CollapsingToolbarLayout.LayoutParams
            lp2.topMargin = statusBarHeight
            toolbar_layout.setLayoutParams(lp2)
        }
    }

    private fun initTab() {
        var fragments = getFragments()
        val myFragmentPagerAdapter =
            MyFragmentPagerAdapter(supportFragmentManager, fragments, getNames())
        uc_tablayout.setTabData(mTabEntities)
        uc_viewpager.setAdapter(myFragmentPagerAdapter)
    }


    private fun getStatusBarHeight(context: Context): Int {

        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    fun getFragments(): List<Fragment> {
        val fragments = ArrayList<Fragment>()

        fragments.add(ItemFragment())
        fragments.add(ItemFragment())
        fragments.add(ItemFragment())
        fragments.add(ItemFragment())
        return fragments

    }

    fun getNames(): Array<String> {
        val mNames = arrayOf("follower", "repo", "sub", "received")
        for (str in mNames) {
            mTabEntities.add(TabEntity(str))
        }

        return mNames
    }

    fun initListener(){
        frag_uc_nickname_tv.text = SharedPreferencesUtils(this).username

        appbar_layout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                val percent =
                    Math.abs(verticalOffset).toFloat() /
                            appBarLayout.getTotalScrollRange()

                if (title_center_layout != null && uc_avater != null) {
                    title_center_layout.setAlpha(percent)
                    StatusBarUtil.setTranslucentForImageView(
                        this@SettingActiviy,
                        (255f * percent).toInt(),
                        null
                    )
                    if (percent == 0f) {
                        groupChange(1f, 1)
                    } else if (percent == 1f) {
                        if (uc_avater.getVisibility() !== View.GONE) {
                            uc_avater.setVisibility(View.GONE)
                        }
                        groupChange(1f, 2)
                    } else {
                        if (uc_avater.getVisibility() !== View.VISIBLE) {
                            uc_avater.setVisibility(View.VISIBLE)
                        }
                        groupChange(percent, 0)
                    }

                }
            }
        })

        val myAppBarLayoutBehavoir =
            (appbar_layout.getLayoutParams() as CoordinatorLayout.LayoutParams).getBehavior() as AppBarLayoutOverScrollViewBehavior

        myAppBarLayoutBehavoir.setOnProgressChangeListener(object :
            AppBarLayoutOverScrollViewBehavior.OnProgressChangeListener {
            override fun onProgressChange(progress: Float, isRelease: Boolean) {
                uc_progressbar.setProgress((progress * 360).toInt())
                if (progress == 1f && !uc_progressbar.isSpinning && isRelease) {
                    // 刷新viewpager里的fragment
                }
            }
        })

        uc_tablayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                uc_viewpager.setCurrentItem(position)
            }

            override fun onTabReselect(position: Int) {

            }
        })
        uc_viewpager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                uc_tablayout.setCurrentTab(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }

    fun groupChange(alpha: Float, state: Int) {
        lastState = state

        when (state) {
            1
            -> {
                uc_viewpager.setNoScroll(false)
            }
            2
            -> {
                uc_viewpager.setNoScroll(false)
            }
            0
            -> {
                uc_viewpager.setNoScroll(true)
            }
        }
    }



}