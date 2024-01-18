package com.vijaydhoni.shoppingapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetAllOrdersUseCase

class AllOrdersViewModelFactory(
    private val getAllOrdersUseCase: GetAllOrdersUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AllOrdersViewModel(getAllOrdersUseCase) as T
    }

}