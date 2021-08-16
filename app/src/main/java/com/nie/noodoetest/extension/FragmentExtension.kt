package com.nie.noodoetest.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.nie.noodoetest.R

fun FragmentManager.addFragment(
    containerId: Int,
    fragment: Fragment,
    isSlide: Boolean = true
) {
    this.beginTransaction().apply {
        if (isSlide) {
            setCustomAnimations(
                R.anim.slide_in_from_right,
                R.anim.slide_out_from_left
            )
        }

        add(containerId, fragment)
        addToBackStack(null)
        commitAllowingStateLoss()
    }
}