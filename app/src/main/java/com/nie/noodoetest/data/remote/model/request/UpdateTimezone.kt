package com.nie.noodoetest.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class UpdateTimezone(
    @SerializedName("timezone")
    val timezone: Int
)
