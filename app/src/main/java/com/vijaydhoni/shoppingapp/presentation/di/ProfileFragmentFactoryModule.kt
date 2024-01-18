package com.vijaydhoni.shoppingapp.presentation.di

import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetRecentOrderUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetUserUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.LogoutUseCase
import com.vijaydhoni.shoppingapp.presentation.viewmodels.ProfileFragmentViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProfileFragmentFactoryModule {

    @Provides
    @Singleton
    fun providesProfileFragmentViewModelFactory(
        getUserUseCase: GetUserUseCase,
        logoutUseCase: LogoutUseCase,
        getRecentOrderUseCase: GetRecentOrderUseCase
    ) = ProfileFragmentViewModelFactory(getUserUseCase, logoutUseCase, getRecentOrderUseCase)

}