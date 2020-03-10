package com.example.github.utrtestapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.github.utrtestapp.R
import com.example.github.utrtestapp.utils.SharedPreferencesUtils
import okhttp3.logging.HttpLoggingInterceptor

//This page is used to initialize plugins, registration information, advertising pages, etc.

class SplashActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activiy)
        val preferences = SharedPreferencesUtils(this);

        //Use the timestamp to determine if you can log in.
        if (preferences.timestamp != 0L){
            startActivity(Intent(this,MainActivity::class.java))
        }else{
            startActivity(Intent(this,LoginActivity::class.java))
        }
        finish();
    }
}