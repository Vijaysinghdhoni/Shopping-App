package com.vijaydhoni.shoppingapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vijaydhoni.shoppingapp.data.model.Address
import com.vijaydhoni.shoppingapp.data.model.Order
import com.vijaydhoni.shoppingapp.data.util.Event
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetUserAddressesUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.PlaceOrderUseCase
import kotlinx.coroutines.launch

class BillingViewmodel(
    private val getUserAddressesUseCase: GetUserAddressesUseCase,
    private val placeOrderUseCase: PlaceOrderUseCase
) : ViewModel() {

    private val _address: MutableLiveData<Resource<List<Address>>> = MutableLiveData()
    val address: LiveData<Resource<List<Address>>> = _address

    init {
        getUserAddress()
    }

    fun getUserAddress() {

        viewModelScope.launch {
            _address.postValue(Resource.Loading())
            val userAddress = getUserAddressesUseCase.execute()
            _address.postValue(userAddress)
        }
    }

    private val _placeOrder: MutableLiveData<Event<Resource<Order>>> = MutableLiveData()
    val placeOrder: LiveData<Event<Resource<Order>>> = _placeOrder

    fun placeUserOrder(order: Order) {

        viewModelScope.launch {
            _placeOrder.postValue(Event(Resource.Loading()))
            val orderPlaced = placeOrderUseCase.execute(order)
            _placeOrder.postValue(Event(orderPlaced))
        }

    }


}