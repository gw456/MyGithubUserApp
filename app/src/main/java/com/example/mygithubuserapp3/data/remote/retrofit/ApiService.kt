package com.example.mygithubuserapp3.data.remote.retrofit

import com.example.mygithubuserapp3.BuildConfig
import com.example.mygithubuserapp3.data.remote.response.UserDetailResponse
import com.example.mygithubuserapp3.data.remote.response.UserFollowersResponseItem
import com.example.mygithubuserapp3.data.remote.response.UserFollowingResponseItem
import com.example.mygithubuserapp3.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: "+ BuildConfig.apiKey)
    fun findUser(
        @Query("q") username: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: "+ BuildConfig.apiKey)
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: "+ BuildConfig.apiKey)
    fun getListFollowers(
        @Path("username") username: String
    ): Call<List<UserFollowersResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: "+ BuildConfig.apiKey)
    fun getListFollowing(
        @Path("username") username: String
    ): Call<List<UserFollowingResponseItem>>
}