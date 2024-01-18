package com.vijaydhoni.shoppingapp.domain.repositorys

import com.google.firebase.auth.FirebaseUser
import com.vijaydhoni.shoppingapp.data.model.User
import com.vijaydhoni.shoppingapp.data.util.Resource

interface UserAuthenticationRepository {


    suspend fun login(email: String, password: String): Resource<FirebaseUser?>

    suspend fun signup(user: User, password: String): Resource<FirebaseUser?>

    fun logout(): Resource<Unit>

    fun getCurrentuser(): Resource<FirebaseUser?>

    suspend fun sendPasswordReset(email: String): Resource<String>


}