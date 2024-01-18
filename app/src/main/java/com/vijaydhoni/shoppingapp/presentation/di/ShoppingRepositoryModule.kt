package com.vijaydhoni.shoppingapp.presentation.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.vijaydhoni.shoppingapp.data.repository.ShoppingRepositoryImpl
import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ShoppingRepositoryModule {

    @Provides
    @Singleton
    fun providesShoppingRepositroy(
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth,
        storage: StorageReference
    ): ShoppingRepository {
        return ShoppingRepositoryImpl(firebaseFirestore, firebaseAuth, storage)
    }
}