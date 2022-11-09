package com.example.mygithubuserapp3.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("company")
    val company: Any,

    @field:SerializedName("public_repos")
    val publicRepos: Int,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("name")
    val name: Any,

    @field:SerializedName("location")
    val location: Any
)
