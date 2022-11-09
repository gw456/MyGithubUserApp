package com.example.mygithubuserapp3.data

import androidx.lifecycle.LiveData
import com.example.mygithubuserapp3.data.local.entity.User
import com.example.mygithubuserapp3.data.local.room.UserDao

class UserRepository private constructor(
    private val userDao: UserDao
){
    suspend fun insertFavoriteUser(user: User) {
        userDao.insert(user)
    }

    suspend fun deleteFavoriteUser(user: User) {
        userDao.delete(user)
    }

   suspend  fun updateFavoriteUser(user: User, favoriteState: Boolean) {
       user.isFavorite = favoriteState
       userDao.update(user)
    }

    fun getFavoriteUser(): LiveData<List<User>> {
        return userDao.getFavoriteUser()
    }

    fun isFavoriteUser(username: String): LiveData<Boolean> {
        return userDao.isFavoriteUser(username)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userDao: UserDao
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userDao)
            }.also { instance = it}
    }
}