package com.example.github.utrtestapp.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.example.github.utrtestapp.R
import com.example.github.utrtestapp.base.BaseActivity
import com.example.github.utrtestapp.http.HttpUtils
import com.example.github.utrtestapp.utils.SharedPreferencesUtils
import com.example.github.utrtestapp.utils.ToastUtils
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.signup_activity.*
import org.w3c.dom.Text
import java.util.regex.Pattern


class SignUPActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_activity)
        icon_back.setOnClickListener(this)
        sign_up.setOnClickListener(this)

        password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                checkRequest(editable.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.icon_back -> finish()
                R.id.sign_up -> {
                    HttpUtils.create().signUp()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ result ->
                            val sp = SharedPreferencesUtils(this)
                            sp.username = result.name
                            sp.avatar_url = result.avatar_url
                            sp.location = result.location
                            sp.subscriptions_url = result.subscriptions_url
                            sp.received_events = result.received_events;
                            sp.followers = result.followers;
                            sp.repos_url = result.repos_url
                            startActivity(Intent(this, SettingActiviy::class.java))
                            finish()
                        }, { error -> Log.e("SiggnUpActivity", error.message) })
                }
            }
        }
    }


    fun checkRequest(str: String) {

        if (Pattern.compile(".*?[0-9]").matcher(str).matches()) {
            check3.setTextColor(Color.parseColor("#00BAB6"))
        } else {
            check3.setTextColor(Color.parseColor("#000000"))
        }
        if (Pattern.compile(".*?[A-Z]").matcher(str).matches()) {
            check2.setTextColor(Color.parseColor("#00BAB6"))
        } else {
            check2.setTextColor(Color.parseColor("#000000"))
        }
        if (Pattern.compile(".{8,}").matcher(str).matches()) {
            check1.setTextColor(Color.parseColor("#00BAB6"))
        } else {
            check1.setTextColor(Color.parseColor("#000000"))
        }

    }


}

