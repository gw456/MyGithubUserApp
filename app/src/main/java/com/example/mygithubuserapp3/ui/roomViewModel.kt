package com.example.mygithubuserapp3.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygithubuserapp3.data.UserRepository
import com.example.mygithubuserapp3.data.local.entity.User
import kotlinx.coroutines.launch

class roomViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun insertFavoriteUser(user: User) {
        viewModelScope.launch {
            userRepository.insertFavoriteUser(user)
        }
    }

    fun deleteFavoriteUser(user: User) {
        viewModelScope.launch {
            userRepository.deleteFavoriteUser(user)
        }
    }

    fun getFavoriteUser() = userRepository.getFavoriteUser()

    fun isFavoriteUser(username: String) = userRepository.isFavoriteUser(username)

    fun updateFavoriteUser(user: User, favoriteState: Boolean) {
        viewModelScope.launch {
            userRepository.updateFavoriteUser(user, favoriteState)
        }
    }
}