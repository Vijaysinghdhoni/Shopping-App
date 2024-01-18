package com.vijaydhoni.shoppingapp.presentation.di

import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetAllOrdersUseCase
import com.vijaydhoni.shoppingapp.presentation.viewmodels.AllOrdersViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AllOrdersViewModelFactoryUseCase {

    @Singleton
    @Provides
    fun providesAllOrdersViewModelFactory(
        getAllOrdersUseCase: GetAllOrdersUseCase
    ) = AllOrdersViewModelFactory(getAllOrdersUseCase)

}