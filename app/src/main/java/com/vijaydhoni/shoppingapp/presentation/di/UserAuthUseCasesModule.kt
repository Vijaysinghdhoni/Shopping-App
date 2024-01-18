package com.vijaydhoni.shoppingapp.presentation.di

import com.vijaydhoni.shoppingapp.domain.repositorys.UserAuthenticationRepository
import com.vijaydhoni.shoppingapp.domain.usecases.*
import com.vijaydhoni.shoppingapp.domain.usecases.userAuthUsecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UserAuthUseCasesModule {


    @Provides
    @Singleton
    fun providesLoginUseCase(userAuthenticationRepository: UserAuthenticationRepository): LoginUseCase {
        return LoginUseCase(userAuthenticationRepository)
    }


    @Provides
    @Singleton
    fun providesLogOutUseCase(userAuthenticationRepository: UserAuthenticationRepository): LogOutUseCase {
        return LogOutUseCase(userAuthenticationRepository)
    }


    @Provides
    @Singleton
    fun providesSendPasswrdResetUseCase(userAuthenticationRepository: UserAuthenticationRepository): SendPasswordResetUseCase {
        return SendPasswordResetUseCase(userAuthenticationRepository)
    }


    @Provides
    @Singleton
    fun providesSignUpUseCase(userAuthenticationRepository: UserAuthenticationRepository): SignUpUseCase {
        return SignUpUseCase(userAuthenticationRepository)
    }


    @Provides
    @Singleton
    fun providesGetCurrentUserUseCase(userAuthenticationRepository: UserAuthenticationRepository): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(userAuthenticationRepository)
    }


}