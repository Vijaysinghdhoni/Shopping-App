package com.vijaydhoni.shoppingapp.presentation.di

import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.*
import com.vijaydhoni.shoppingapp.presentation.viewmodels.MainCategoryViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainCategoryFactoryModule {

    @Provides
    @Singleton
    fun providesCategoryViewModelFatory(
        getBestProductsPagingSourceUSeCase: BestProductsPagingSourceUSeCase,
        getSpecialProductPagingUseCase: GetSpecialProductPagingUseCase,
        getBestDealsPagingUseCase: GetBestDealsPagingUseCase
    ): MainCategoryViewModelFactory {
        return MainCategoryViewModelFactory(
            getBestProductsPagingSourceUSeCase,
            getBestDealsPagingUseCase,
            getSpecialProductPagingUseCase
        )
    }

}

