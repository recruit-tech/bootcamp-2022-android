package com.example.mygithubapp.data

import com.google.gson.annotations.SerializedName

data class UserListResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    val items: List<UserInfomation>
)
