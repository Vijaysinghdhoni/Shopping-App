package com.vijaydhoni.shoppingapp.presentation.di

import android.app.Application
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetUserUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.SaveUserInfoUSeCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.SaveUserProfileImgUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserAccountViewModelFactory {

    @Singleton
    @Provides
    fun providesUserAccountViewModelFactory(
        getUserUseCase: GetUserUseCase,
        saveUserInfoUSeCase: SaveUserInfoUSeCase,
        saveUserProfileImgUseCase: SaveUserProfileImgUseCase,
        app: Application
    ) = com.vijaydhoni.shoppingapp.presentation.viewmodels.UserAccountViewModelFactory(
        getUserUseCase,
        saveUserInfoUSeCase,
        saveUserProfileImgUseCase,
        app
    )

}