package com.vijaydhoni.shoppingapp.domain.usecases.userAuthUsecases

import com.google.firebase.auth.FirebaseUser
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.domain.repositorys.UserAuthenticationRepository

class GetCurrentUserUseCase(private val userAuthenticationRepository: UserAuthenticationRepository) {

    fun execute(): Resource<FirebaseUser?> = userAuthenticationRepository.getCurrentuser()

}