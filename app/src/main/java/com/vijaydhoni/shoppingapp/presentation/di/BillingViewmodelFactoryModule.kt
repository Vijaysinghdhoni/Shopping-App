package com.vijaydhoni.shoppingapp.presentation.di

import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetUserAddressesUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.PlaceOrderUseCase
import com.vijaydhoni.shoppingapp.presentation.viewmodels.BillingViewModelFactory
import com.vijaydhoni.shoppingapp.presentation.viewmodels.BillingViewmodel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BillingViewmodelFactoryModule {


    @Provides
    @Singleton
    fun providesBillingViewmodelFactory(
        getUserAddressesUseCase: GetUserAddressesUseCase,
        placeOrderUseCase: PlaceOrderUseCase
    ) =
        BillingViewModelFactory(getUserAddressesUseCase, placeOrderUseCase)


}