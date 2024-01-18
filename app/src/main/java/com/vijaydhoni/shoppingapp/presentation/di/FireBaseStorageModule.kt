package com.vijaydhoni.shoppingapp.presentation.di

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FireBaseStorageModule {

    @Provides
    @Singleton
    fun providesFireBaseStorage(): StorageReference {
        return FirebaseStorage.getInstance().reference
    }

}