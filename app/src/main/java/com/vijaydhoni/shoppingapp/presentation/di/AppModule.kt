package com.vijaydhoni.shoppingapp.presentation.di

import android.app.Application
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.vijaydhoni.shoppingapp.data.repository.ShoppingRepositoryImpl
import com.vijaydhoni.shoppingapp.data.repository.UserAuthenticationRepositoryImpl
import com.vijaydhoni.shoppingapp.data.util.Constants
import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository
import com.vijaydhoni.shoppingapp.domain.repositorys.UserAuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun providesFirebaseFireStore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun providesFireBaseStorage(): StorageReference {
        return FirebaseStorage.getInstance().reference
    }

    @Provides
    @Singleton
    fun providesUserAuthRepository(firebaseAuth: FirebaseAuth): UserAuthenticationRepository {
        return UserAuthenticationRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun providesShoppingRepositroy(
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth,
        storage: StorageReference
    ): ShoppingRepository {
        return ShoppingRepositoryImpl(firebaseFirestore, firebaseAuth, storage)
    }

    @Provides
    @Singleton
    fun providesIntroductionSharedPrefernce(
        app: Application
    ) = app.getSharedPreferences(Constants.INTRODUCTION_SP, Context.MODE_PRIVATE)


}