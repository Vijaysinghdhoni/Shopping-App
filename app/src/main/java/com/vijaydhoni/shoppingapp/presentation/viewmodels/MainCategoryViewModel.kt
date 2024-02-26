package com.vijaydhoni.shoppingapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.BestProductsPagingSourceUSeCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetBestDealsPagingUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetSpecialProductPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainCategoryViewModel @Inject constructor(
    getBestProductsPagingSourceUSeCase: BestProductsPagingSourceUSeCase,
    getBestDealsPagingUseCase: GetBestDealsPagingUseCase,
    getSpecialProductPagingUseCase: GetSpecialProductPagingUseCase
) : ViewModel() {


    val bestProductsPaging = getBestProductsPagingSourceUSeCase.execute().cachedIn(viewModelScope)
    val bestDealsPaging = getBestDealsPagingUseCase.execute().cachedIn(viewModelScope)
    val specialProductPaging = getSpecialProductPagingUseCase.execute().cachedIn(viewModelScope)


}