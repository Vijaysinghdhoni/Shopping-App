package com.vijaydhoni.shoppingapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vijaydhoni.shoppingapp.data.model.Order
import com.vijaydhoni.shoppingapp.data.model.User
import com.vijaydhoni.shoppingapp.data.util.Event
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetRecentOrderUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetUserUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.LogoutUseCase
import kotlinx.coroutines.launch

class ProfileFragmentViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getRecentOrderUseCase: GetRecentOrderUseCase
) : ViewModel() {

    private val _user: MutableLiveData<Event<Resource<User?>>> = MutableLiveData()
    val user: LiveData<Event<Resource<User?>>> = _user

    fun getUser() {
        viewModelScope.launch {
            _user.postValue(Event(Resource.Loading()))
            val user = getUserUseCase.execute()
            _user.postValue(Event(user))
        }
    }

    private val _logout: MutableLiveData<Event<Resource<Unit>>> = MutableLiveData()
    val logout: LiveData<Event<Resource<Unit>>> = _logout

    fun logoutUser() {
        _logout.postValue(Event(Resource.Loading()))
        val snapShot = logoutUseCase.execute()
        _logout.postValue(Event(snapShot))
    }

    private val _recentOrder: MutableLiveData<Event<Resource<List<Order>>>> = MutableLiveData()
    val recentOrder: LiveData<Event<Resource<List<Order>>>> = _recentOrder

    fun getRecentsOrder() {
        viewModelScope.launch {
            _recentOrder.postValue(Event(Resource.Loading()))
            val order = getRecentOrderUseCase.execute()
            _recentOrder.postValue(Event(order))
        }
    }


}