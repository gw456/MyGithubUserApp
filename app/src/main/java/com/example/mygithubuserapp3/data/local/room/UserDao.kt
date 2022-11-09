package com.example.mygithubuserapp3.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mygithubuserapp3.data.local.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Update
    suspend fun update(user: User)

    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username)")
    fun isFavoriteUser(username: String): LiveData<Boolean>

    @Query("SELECT * FROM user")
    fun getFavoriteUser():LiveData<List<User>>
}