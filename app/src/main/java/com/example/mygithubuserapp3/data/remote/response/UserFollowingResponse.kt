package com.example.mygithubuserapp3.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserFollowingResponseItem(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String
)
