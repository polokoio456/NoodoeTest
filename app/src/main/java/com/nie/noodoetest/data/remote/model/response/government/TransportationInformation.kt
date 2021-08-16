package com.nie.noodoetest.data.remote.model.response.government

import com.google.gson.annotations.SerializedName

data class TransportationInformation(
    @SerializedName("updateTime")
    val updateTime: String,
    @SerializedName("News")
    val news: List<TransportationInfoItem>
)

data class TransportationInfoItem(
    @SerializedName("chtmessage")
    val chtmessage: String,
    @SerializedName("starttime")
    val starttime: String,
    @SerializedName("endtime")
    val endtime: String,
    @SerializedName("updatetime")
    val updatetime: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("url")
    val url: String?
)
