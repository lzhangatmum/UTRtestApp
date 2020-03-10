package com.example.github.utrtestapp.listener

import androidx.annotation.DrawableRes

interface CustomTabEntity {
    fun getTabTitle(): String

    @DrawableRes
    fun getTabSelectedIcon(): Int

    @DrawableRes
    fun getTabUnselectedIcon(): Int

    fun getSubTitle(): String
}