package com.example.github.utrtestapp.ui.fragment

import com.example.github.utrtestapp.listener.CustomTabEntity

class TabEntity(var title: String): CustomTabEntity {
    override fun getTabTitle(): String {
        return title
    }

    override fun getTabSelectedIcon(): Int {
       return 0;
    }

    override fun getTabUnselectedIcon(): Int {
        return 0
    }

    override fun getSubTitle(): String {
        return "";
    }

}