package com.vijaydhoni.shoppingapp.presentation.di

import android.app.Application
import android.content.SharedPreferences
import com.google.firebase.firestore.FirebaseFirestore
import com.vijaydhoni.shoppingapp.domain.usecases.userAuthUsecases.GetCurrentUserUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.userAuthUsecases.LoginUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.userAuthUsecases.SendPasswordResetUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.userAuthUsecases.SignUpUseCase
import com.vijaydhoni.shoppingapp.presentation.viewmodels.UserAuthenticationViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserAuthFactoryModule {

    @Singleton
    @Provides
    fun providesUserAuthViewModelfactory(
        loginUseCase: LoginUseCase,
        app: Application,
        signUpUseCase: SignUpUseCase,
        sendPasswordResetUseCase: SendPasswordResetUseCase,
        getCurrentUserUseCase: GetCurrentUserUseCase,
        sharedPreferences: SharedPreferences,
        db: FirebaseFirestore
    ): UserAuthenticationViewModelFactory {

        return UserAuthenticationViewModelFactory(
            loginUseCase,
            app,
            signUpUseCase,
            sendPasswordResetUseCase,
            getCurrentUserUseCase,
            sharedPreferences,
            db
        )
    }

}