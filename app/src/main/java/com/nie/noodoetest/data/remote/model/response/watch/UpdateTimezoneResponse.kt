package com.nie.noodoetest.data.remote.model.response.watch

import com.google.gson.annotations.SerializedName

data class UpdateTimezoneResponse(
    @SerializedName("updatedAt")
    val updatedAt: String
)
