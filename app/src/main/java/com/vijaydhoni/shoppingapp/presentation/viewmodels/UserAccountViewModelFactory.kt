package com.vijaydhoni.shoppingapp.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetUserUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.SaveUserInfoUSeCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.SaveUserProfileImgUseCase

class UserAccountViewModelFactory(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserInfoUSeCase: SaveUserInfoUSeCase,
    private val saveUserProfileImgUseCase: SaveUserProfileImgUseCase,
    val app: Application
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserAccountViewModel(
            getUserUseCase, saveUserInfoUSeCase, saveUserProfileImgUseCase, app
        ) as T
    }

}