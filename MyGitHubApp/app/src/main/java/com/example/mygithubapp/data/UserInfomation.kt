package com.example.mygithubapp.data

import com.google.gson.annotations.SerializedName

data class UserInfomation (
    @SerializedName("login") val name: String,
    val id: Int,
    @SerializedName("avatar_url") val avaterUrl: String,
    val url: String,
    val html_url: String,
    val type: String,
    val score: Float,
    val favorite: Boolean = false
)
