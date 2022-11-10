package com.example.associate.training.views.layout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        if (position == 0) {
            fragment = FragmentA()
        } else if (position == 1) {
            fragment = FragmentB()
        } else if (position == 2) {
            fragment = FragmentC()
        }
        return fragment!!
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null
        if (position == 0) {
            title = "Tab-1"
        } else if (position == 1) {
            title = "Tab-2"
        } else if (position == 2) {
            title = "Tab-3"
        }
        return title
    }
}