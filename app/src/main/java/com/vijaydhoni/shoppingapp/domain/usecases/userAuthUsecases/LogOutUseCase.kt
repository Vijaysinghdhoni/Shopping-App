package com.vijaydhoni.shoppingapp.domain.usecases.userAuthUsecases

import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.domain.repositorys.UserAuthenticationRepository

class LogOutUseCase(private val userAuthenticationRepository: UserAuthenticationRepository) {

    fun execute(): Resource<Unit> = userAuthenticationRepository.logout()

}