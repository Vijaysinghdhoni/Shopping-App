package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.data.model.User
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class SaveUserInfoUSeCase(private val repository: ShoppingRepository) {

    suspend fun execute(user: User, shouldRetrievedOldImage: Boolean): Resource<User> {
        return repository.saveUserInfo(user, shouldRetrievedOldImage)
    }
}