package com.example.mygithubuserapp3.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mygithubuserapp3.data.UserRepository
import com.example.mygithubuserapp3.di.Injection
import java.lang.IllegalArgumentException

class RoomViewModelFactory private constructor(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(roomViewModel::class.java)) {
            return roomViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: RoomViewModelFactory? = null
        fun getInstance(context: Context): RoomViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: RoomViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}