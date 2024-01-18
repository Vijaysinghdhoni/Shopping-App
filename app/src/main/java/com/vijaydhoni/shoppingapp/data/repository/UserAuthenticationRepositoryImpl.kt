package com.vijaydhoni.shoppingapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.vijaydhoni.shoppingapp.data.model.User
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.domain.repositorys.UserAuthenticationRepository
import kotlinx.coroutines.tasks.await

class UserAuthenticationRepositoryImpl(private val firebaseAuth: FirebaseAuth) :
    UserAuthenticationRepository {


    override suspend fun login(email: String, password: String): Resource<FirebaseUser?> {
        return try {
            val auth = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(auth.user)
        } catch (e: java.lang.Exception) {
            Resource.Error("invalid user! please make account" )
        }

    }


    override suspend fun signup(user: User, password: String): Resource<FirebaseUser?> {
        return try {
            val auth = firebaseAuth.createUserWithEmailAndPassword(user.email, password).await()
            Resource.Success(auth.user)
        } catch (e: java.lang.Exception) {
            Resource.Error(e.message ?: "SignUp Failed")
        }
    }

    override fun logout(): Resource<Unit> {
        return try {
            firebaseAuth.signOut()
            Resource.Success(Unit)
        } catch (e: java.lang.Exception) {
            Resource.Error(e.message!! )
        }
    }

    override fun getCurrentuser(): Resource<FirebaseUser?> {
        return try {
            val user = firebaseAuth.currentUser
            Resource.Success(user)
        } catch (e: java.lang.Exception) {
            Resource.Error(e.message!!)
        }
    }

    override suspend fun sendPasswordReset(email: String): Resource<String> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Resource.Success(email)
        } catch (e: java.lang.Exception) {
            Resource.Error(e.message ?: "Failed to send password reset email")
        }
    }


}