package com.example.github.utrtestapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ViewSwitcher
import com.bumptech.glide.Glide
import com.example.github.utrtestapp.R
import com.example.github.utrtestapp.base.BaseActivity
import com.example.github.utrtestapp.utils.RxTimer
import kotlinx.android.synthetic.main.login_activity.*
import java.util.*
import kotlin.collections.ArrayList
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.github.utrtestapp.view.SignInActivity
import com.example.github.utrtestapp.view.SignUPActivity


class LoginActivity : BaseActivity(),RxTimer.IRxNext, View.OnClickListener{


    var imageArray: ArrayList<Int>? = null;

    var position : Int = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        RxTimer().interval(1000,this)
        imageArray = arrayListOf<Int>(R.mipmap.login_1,R.mipmap.login_2,R.mipmap.login_3,R.mipmap.login_4,R.mipmap.login_5)
        im_bg.setInAnimation(this, android.R.anim.fade_in)
        im_bg.setOutAnimation(this, android.R.anim.fade_out)
        im_bg?.setFactory({
            val imgView = ImageView(this)
            imgView.layoutParams  = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT)
            imgView.scaleType = ImageView.ScaleType.CENTER_CROP
            imgView
        })
        im_bg?.setImageResource(imageArray!![0])
        sign_in.setOnClickListener(this)
        sign_up.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        if (view != null) {
            when(view.id){
                R.id.sign_in -> signin();
                R.id.sign_up -> signup();
            }
        }
    }
    fun signin(){
        startActivity(Intent(this, SignInActivity::class.java))
    }

    fun signup(){
        startActivity(Intent(this, SignUPActivity::class.java))
    }

    override fun doNext() {
        position++
        im_bg?.setImageResource(imageArray!![position % imageArray!!.size])

    }

    override fun onDestroy() {
        RxTimer().stop()
        super.onDestroy()
    }


}

