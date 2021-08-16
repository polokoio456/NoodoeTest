package com.nie.noodoetest.data.remote

import android.content.Context
import hu.autsoft.krate.SimpleKrate
import hu.autsoft.krate.stringPref

class NoodoeSharedPreferences(context: Context) : SimpleKrate(context) {

    companion object {
        const val USER_DATA_KEY = "user_data_key"
    }

    var userData by stringPref(USER_DATA_KEY, "")
}