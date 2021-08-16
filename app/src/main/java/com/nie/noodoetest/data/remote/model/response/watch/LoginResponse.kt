package com.nie.noodoetest.data.remote.model.response.watch

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("objectId")
    val objectId: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("isVerifiedReportEmail")
    val isVerifiedReportEmail: Boolean,
    @SerializedName("reportEmail")
    val reportEmail: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("parameter")
    val parameter: Int,
    @SerializedName("number")
    val number: Int,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("sessionToken")
    val sessionToken: String
)
