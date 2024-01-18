package com.vijaydhoni.shoppingapp.presentation.di

import com.google.firebase.auth.FirebaseAuth
import com.vijaydhoni.shoppingapp.data.repository.UserAuthenticationRepositoryImpl
import com.vijaydhoni.shoppingapp.domain.repositorys.UserAuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UserAuthRepositoryModule {

    @Provides
    @Singleton
    fun providesUserAuthRepository(firebaseAuth: FirebaseAuth): UserAuthenticationRepository {
        return UserAuthenticationRepositoryImpl(firebaseAuth)
    }

}