package com.nie.noodoetest.extension

fun String.isValidEmailFormat(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}