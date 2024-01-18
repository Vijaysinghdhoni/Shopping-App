package com.vijaydhoni.shoppingapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vijaydhoni.shoppingapp.data.model.Address
import com.vijaydhoni.shoppingapp.data.util.Event
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.AddUserAddressUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.DeleteUserAddressUSeCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetCurrentAddressFromUserAddressesUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.UpdateUserAddressUseCase
import kotlinx.coroutines.launch

class AddressViewModel(
    private val addUserAddressUseCase: AddUserAddressUseCase,
    private val getCurrentAddressFromUserAddressesUseCase: GetCurrentAddressFromUserAddressesUseCase,
    private val updateUserAddressUseCase: UpdateUserAddressUseCase,
    private val deleteUserAddressUSeCase: DeleteUserAddressUSeCase
) : ViewModel() {

    private val _addNewAddress: MutableLiveData<Resource<Address>> = MutableLiveData()
    val addNewAddress: LiveData<Resource<Address>> = _addNewAddress

    fun addAddress(address: Address) {

        viewModelScope.launch {
            if (validateUser(address)) {
                _addNewAddress.postValue(Resource.Loading())
                val addreses = addUserAddressUseCase.execute(address)
                _addNewAddress.postValue(addreses)
            } else {
                _addNewAddress.postValue(Resource.Error("All Fields Are Required"))
            }
        }


    }

    private val _updateAddress: MutableLiveData<Event<Resource<Address>>> = MutableLiveData()
    val updateAddress: LiveData<Event<Resource<Address>>> = _updateAddress


    fun updateUserAddress(newAddress: Address, oldAddress: Address) {

        viewModelScope.launch {
            _updateAddress.postValue(Event(Resource.Loading()))
            if (validateUser(newAddress)) {
                val currentAddressSnapShot =
                    getCurrentAddressFromUserAddressesUseCase.execute(oldAddress)
                val documentId = currentAddressSnapShot.data?.documents?.first()?.id
                val updatedAddress = updateUserAddressUseCase.execute(newAddress, documentId!!)
                _updateAddress.postValue(Event(updatedAddress))
            } else {
                _updateAddress.postValue(Event(Resource.Error("All Fields Are Required")))
            }

        }


    }

    private val _deleteAddress: MutableLiveData<Event<Resource<Unit>>> = MutableLiveData()
    val deleteAddres: LiveData<Event<Resource<Unit>>> = _deleteAddress

    fun deleteUserAddress(address: Address) {
        viewModelScope.launch {
            _deleteAddress.postValue(Event(Resource.Loading()))
            if (validateUser(address)) {
                val currentAddressSnapShot =
                    getCurrentAddressFromUserAddressesUseCase.execute(address)
                val documentId = currentAddressSnapShot.data?.documents?.first()?.id
                val deleteAddressresult = deleteUserAddressUSeCase.execute(documentId!!)
                _deleteAddress.postValue(Event(deleteAddressresult))
            } else {
                _deleteAddress.postValue(Event(Resource.Error("Nothing to Delete")))
            }

        }
    }


    private fun validateUser(address: Address): Boolean {

        if (address.addressLocation.trim().isEmpty()) {
            return false
        }
        if (address.state.trim().isEmpty()) {
            return false
        }
        if (address.fullName.trim().isEmpty()) {
            return false
        }
        if (address.fullAddress.trim().isEmpty()) {
            return false
        }
        if (address.phone.trim().isEmpty()) {
            return false
        }

        if (address.city.trim().isEmpty()) {
            return false
        }

        return true
    }


}