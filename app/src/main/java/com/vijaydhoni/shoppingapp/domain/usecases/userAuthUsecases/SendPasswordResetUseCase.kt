package com.vijaydhoni.shoppingapp.domain.usecases.userAuthUsecases

import com.vijaydhoni.shoppingapp.domain.repositorys.UserAuthenticationRepository

class SendPasswordResetUseCase(private val userAuthenticationRepository: UserAuthenticationRepository) {

    suspend fun execute(email: String) = userAuthenticationRepository.sendPasswordReset(email)

}