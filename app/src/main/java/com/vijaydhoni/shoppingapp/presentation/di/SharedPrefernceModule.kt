package com.vijaydhoni.shoppingapp.presentation.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import com.vijaydhoni.shoppingapp.data.util.Constants.INTRODUCTION_SP
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPrefernceModule {


    @Provides
    @Singleton
    fun providesIntroductionSharedPrefernce(
        app: Application
    ) = app.getSharedPreferences(INTRODUCTION_SP, MODE_PRIVATE)

}