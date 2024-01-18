package com.vijaydhoni.shoppingapp.presentation.di

import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.AddUserAddressUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.DeleteUserAddressUSeCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetCurrentAddressFromUserAddressesUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.UpdateUserAddressUseCase
import com.vijaydhoni.shoppingapp.presentation.viewmodels.AddressViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AddressViewModelFactoryModule {

    @Provides
    @Singleton
    fun providesAddressViewModelFactory(
        addUserAddressUseCase: AddUserAddressUseCase,
        getCurrentAddressFromUserAddressesUseCase: GetCurrentAddressFromUserAddressesUseCase,
        updateUserAddressUseCase: UpdateUserAddressUseCase,
        deleteUserAddressUSeCase: DeleteUserAddressUSeCase
    ) =
        AddressViewModelFactory(
            addUserAddressUseCase,
            getCurrentAddressFromUserAddressesUseCase,
            updateUserAddressUseCase,
            deleteUserAddressUSeCase
        )

}