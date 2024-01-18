package com.vijaydhoni.shoppingapp.presentation.di

import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetSearchedProductUseCase
import com.vijaydhoni.shoppingapp.presentation.viewmodels.SearchViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SearchfactoryModule {

    @Provides
    @Singleton
    fun providesSearchViewModelFactory(getSearchedProductUseCase: GetSearchedProductUseCase) =
        SearchViewModelFactory(getSearchedProductUseCase)


}