package com.vijaydhoni.shoppingapp.domain.usecases.userAuthUsecases

import com.google.firebase.auth.FirebaseUser
import com.vijaydhoni.shoppingapp.data.model.User
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.domain.repositorys.UserAuthenticationRepository

class SignUpUseCase(private val userAuthenticationRepository: UserAuthenticationRepository) {

    suspend fun execute(user: User, password: String): Resource<FirebaseUser?> =
        userAuthenticationRepository.signup(user, password)

}