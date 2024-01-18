package com.vijaydhoni.shoppingapp.domain.usecases.userAuthUsecases

import com.google.firebase.auth.FirebaseUser
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.domain.repositorys.UserAuthenticationRepository

class LoginUseCase(private val userAuthenticationRepository: UserAuthenticationRepository) {

    suspend fun execute(email: String, password: String): Resource<FirebaseUser?> =
        userAuthenticationRepository.login(email, password)
}