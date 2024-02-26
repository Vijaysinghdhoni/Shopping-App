package com.vijaydhoni.shoppingapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    getAccessoriesProductsUseCase: GetAccessoriesProductsUseCase,
    getFootWearProductsUseCase: GetFootWearProductsUseCase,
    getMensProductsUseCase: GetMensProductsUseCase,
    getSportsProductsUseCase: GetSportsProductsUseCase,
    getBestDealsPagingUseCase: GetBestDealsPagingUseCase,
    getSpecialProductPagingUseCase: GetSpecialProductPagingUseCase
) : ViewModel() {


    val sportsProduct = getSportsProductsUseCase.execute().cachedIn(viewModelScope)

    val mensProduct = getMensProductsUseCase.execute().cachedIn(viewModelScope)

    val footwearProducts = getFootWearProductsUseCase.execute().cachedIn(viewModelScope)

    val accessoriesProducts = getAccessoriesProductsUseCase.execute().cachedIn(viewModelScope)

    val bestDealsPaging = getBestDealsPagingUseCase.execute().cachedIn(viewModelScope)

    val specialProductPaging = getSpecialProductPagingUseCase.execute().cachedIn(viewModelScope)

}