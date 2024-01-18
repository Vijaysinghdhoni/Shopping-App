package com.vijaydhoni.shoppingapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.*

class CategoryViewModelFactory(
    private val getAccessoriesProductsUseCase: GetAccessoriesProductsUseCase,
    private val getFootWearProductsUseCase: GetFootWearProductsUseCase,
    private val getMensProductsUseCase: GetMensProductsUseCase,
    private val getSportsProductsUseCase: GetSportsProductsUseCase,
    private val getBestDealsPagingUseCase: GetBestDealsPagingUseCase,
    private val getSpecialProductPagingUseCase: GetSpecialProductPagingUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(
            getAccessoriesProductsUseCase,
            getFootWearProductsUseCase,
            getMensProductsUseCase,
            getSportsProductsUseCase,
            getBestDealsPagingUseCase,
            getSpecialProductPagingUseCase
        ) as T
    }

}