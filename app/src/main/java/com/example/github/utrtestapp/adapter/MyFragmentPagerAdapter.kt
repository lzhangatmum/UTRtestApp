package com.example.github.utrtestapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MyFragmentPagerAdapter(var fm: FragmentManager,var fragments :List<Fragment>,var names :Array<String>) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
      return fragments.get(position)
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (names != null) {
            names[position]
        } else {
            ""
        }
    }

}