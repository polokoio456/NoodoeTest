package com.nie.noodoetest.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class UpdateTimeZone(
    @SerializedName("timezone")
    val timezone: Int
)
