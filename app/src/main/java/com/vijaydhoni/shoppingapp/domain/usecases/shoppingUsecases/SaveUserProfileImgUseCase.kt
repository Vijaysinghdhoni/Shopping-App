package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class SaveUserProfileImgUseCase(private val shoppingRepository: ShoppingRepository) {
    suspend fun execute(imgByteArray: ByteArray) =
        shoppingRepository.saveUserProfileImg(imgByteArray)
}