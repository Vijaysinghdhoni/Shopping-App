package com.vijaydhoni.shoppingapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetRecentOrderUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetUserUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.LogoutUseCase

class ProfileFragmentViewModelFactory(
    private val getUserUseCase: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getRecentOrderUseCase: GetRecentOrderUseCase
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileFragmentViewModel(getUserUseCase, logoutUseCase, getRecentOrderUseCase) as T
    }

}