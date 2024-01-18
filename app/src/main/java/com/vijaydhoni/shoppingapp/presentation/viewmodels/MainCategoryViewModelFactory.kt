package com.vijaydhoni.shoppingapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.*

class MainCategoryViewModelFactory(

    private val getBestProductsPagingSourceUSeCase: BestProductsPagingSourceUSeCase,
    private val getBestDealsPagingUseCase: GetBestDealsPagingUseCase,
    private val getSpecialProductPagingUseCase: GetSpecialProductPagingUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return MainCategoryViewModel(
            getBestProductsPagingSourceUSeCase,
            getBestDealsPagingUseCase,
            getSpecialProductPagingUseCase

        ) as T
    }
}