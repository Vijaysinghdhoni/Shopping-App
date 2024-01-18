package com.vijaydhoni.shoppingapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.AddUserAddressUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.DeleteUserAddressUSeCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetCurrentAddressFromUserAddressesUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.UpdateUserAddressUseCase

class AddressViewModelFactory(
    private val addUserAddressUseCase: AddUserAddressUseCase,
    private val getCurrentAddressFromUserAddressesUseCase: GetCurrentAddressFromUserAddressesUseCase,
    private val updateUserAddressUseCase: UpdateUserAddressUseCase,
    private val deleteUserAddressUSeCase: DeleteUserAddressUSeCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddressViewModel(
            addUserAddressUseCase,
            getCurrentAddressFromUserAddressesUseCase,
            updateUserAddressUseCase,
            deleteUserAddressUSeCase
        ) as T
    }

}