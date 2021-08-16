package com.nie.noodoetest.extension

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import java.text.SimpleDateFormat
import java.util.*

fun String.toWatchDate(): Date {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
    return dateFormat.parse(this) ?: Date()
}

fun String.toGovernmentDate(): Date {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return dateFormat.parse(this) ?: Date()
}

fun Date.toDateString(pattern: String = "yyyy/MM/dd", zone: TimeZone = TimeZone.getDefault()): String {
    return SimpleDateFormat(pattern, getDefaultLocale()).apply {
        timeZone = zone
    }.format(this)
}

fun getDefaultLocale(): Locale {
    return ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
}