package com.example.mygithubuserapp3.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.mygithubuserapp3.data.remote.response.*
import com.example.mygithubuserapp3.data.remote.retrofit.ApiConfig
import com.example.mygithubuserapp3.helper.Event
import com.example.mygithubuserapp3.ui.insert.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollowers = MutableLiveData<List<UserFollowersResponseItem>>()
    val listFollowers: LiveData<List<UserFollowersResponseItem>> = _listFollowers

    private val _listFollowing = MutableLiveData<List<UserFollowingResponseItem>>()
    val listFollowing: LiveData<List<UserFollowingResponseItem>> = _listFollowing

    private val _snackbarEror = MutableLiveData<Event<String>>()
    val snackbarEror: LiveData<Event<String>> = _snackbarEror

    private val _detailUser = MutableLiveData<UserDetailResponse>()
    val detailUser: LiveData<UserDetailResponse> = _detailUser

    companion object {
        private const val TAG = "MainViewModel"
    }

    fun findUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().findUser(username)

        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listUser.value = responseBody.items
                    }
                } else {
                    Log.e(TAG, "onFailure client: ${response.message()}")
                    _snackbarEror.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure client: ${t.message}")
                _snackbarEror.value = Event(t.message.toString())
            }
        })
    }

    fun findDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailUser.value = responseBody!!
                    }
                } else {
                    Log.e(DetailActivity.TAG, "onFailure client: ${response.message()}")
                    _snackbarEror.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailActivity.TAG, "onFailure client: ${t.message}")
            }

        })
    }

    fun findFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListFollowers(username)
        client.enqueue(object : Callback<List<UserFollowersResponseItem>> {
            override fun onResponse(
                call: Call<List<UserFollowersResponseItem>>,
                response: Response<List<UserFollowersResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollowers.value = responseBody!!
                    }
                } else {
                    Log.e(TAG, "onFailure response: ${response.message()}")
                    _snackbarEror.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<List<UserFollowersResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                _snackbarEror.value = Event(t.message.toString())
            }

        })
    }

    fun findFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListFollowing(username)
        client.enqueue(object : Callback<List<UserFollowingResponseItem>> {
            override fun onResponse(
                call: Call<List<UserFollowingResponseItem>>,
                response: Response<List<UserFollowingResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollowing.value = responseBody!!
                    }
                } else {
                    Log.e(TAG, "onFailure response: ${response.message()}")
                    _snackbarEror.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<List<UserFollowingResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                _snackbarEror.value = Event(t.message.toString())
            }

        })
    }
}