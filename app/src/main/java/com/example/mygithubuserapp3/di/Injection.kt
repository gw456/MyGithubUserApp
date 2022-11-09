package com.example.mygithubuserapp3.di

import android.content.Context
import com.example.mygithubuserapp3.data.UserRepository
import com.example.mygithubuserapp3.data.local.room.UserRoomDatabase

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val database = UserRoomDatabase.getInstance(context)
        val dao = database.userDao()
        return UserRepository.getInstance(dao)
    }
}