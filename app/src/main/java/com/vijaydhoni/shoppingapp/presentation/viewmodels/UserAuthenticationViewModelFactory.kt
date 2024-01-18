package com.vijaydhoni.shoppingapp.presentation.viewmodels

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.vijaydhoni.shoppingapp.domain.usecases.userAuthUsecases.GetCurrentUserUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.userAuthUsecases.LoginUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.userAuthUsecases.SendPasswordResetUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.userAuthUsecases.SignUpUseCase

class UserAuthenticationViewModelFactory(
    private val loginUseCase: LoginUseCase,
    private val app: Application,
    private val signUpUseCase: SignUpUseCase,
    private val sendPasswordResetUseCase: SendPasswordResetUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val sharedPreferences: SharedPreferences,
    private val db: FirebaseFirestore

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserAuthenticationViewmodel(
            loginUseCase,
            app,
            signUpUseCase,
            sendPasswordResetUseCase,
            getCurrentUserUseCase,
            sharedPreferences,
            db
        ) as T
    }


}