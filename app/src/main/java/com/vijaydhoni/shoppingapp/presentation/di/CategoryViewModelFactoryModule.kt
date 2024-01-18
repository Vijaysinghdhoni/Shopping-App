package com.vijaydhoni.shoppingapp.presentation.di

import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.*
import com.vijaydhoni.shoppingapp.presentation.viewmodels.CategoryViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CategoryViewModelFactoryModule {

    @Provides
    @Singleton
    fun providesCategoryViewModelFactory(
        getAccessoriesProductsUseCase: GetAccessoriesProductsUseCase,
        getFootWearProductsUseCase: GetFootWearProductsUseCase,
        getMensProductsUseCase: GetMensProductsUseCase,
        getSportsProductsUseCase: GetSportsProductsUseCase,
        getBestDealsPagingUseCase: GetBestDealsPagingUseCase,
        getSpecialProductPagingUseCase: GetSpecialProductPagingUseCase
    ) = CategoryViewModelFactory(
        getAccessoriesProductsUseCase,
        getFootWearProductsUseCase,
        getMensProductsUseCase,
        getSportsProductsUseCase,
        getBestDealsPagingUseCase,
        getSpecialProductPagingUseCase
    )


}