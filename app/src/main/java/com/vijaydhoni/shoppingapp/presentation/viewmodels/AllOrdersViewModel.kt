package com.vijaydhoni.shoppingapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vijaydhoni.shoppingapp.data.model.Order
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetAllOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllOrdersViewModel @Inject constructor(
    private val getAllOrdersUseCase: GetAllOrdersUseCase
) : ViewModel() {

    private val _allOrders: MutableLiveData<Resource<List<Order>>> = MutableLiveData()
    val allOrders: LiveData<Resource<List<Order>>> = _allOrders

    init {
        getAllOrders()
    }

    fun getAllOrders() {
        viewModelScope.launch {
            _allOrders.postValue(Resource.Loading())
            val allorders = getAllOrdersUseCase.execute()
            _allOrders.postValue(allorders)
        }

    }

}