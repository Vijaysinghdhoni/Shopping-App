package com.vijaydhoni.shoppingapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vijaydhoni.shoppingapp.data.model.Product
import com.vijaydhoni.shoppingapp.data.util.Event
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.BestProductsPagingSourceUSeCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetSearchedProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor (
    private val getSearchedProductUsecase: GetSearchedProductUseCase
) :
    ViewModel() {

    private val _searchProduct: MutableLiveData<Event<Resource<List<Product>>>> = MutableLiveData()
    val searchProduct: LiveData<Event<Resource<List<Product>>>> = _searchProduct


    fun getSearchedProducts(query: String) {
        viewModelScope.launch {
            _searchProduct.postValue(Event(Resource.Loading()))
            val products = getSearchedProductUsecase.execute(query)
            _searchProduct.postValue(Event(products))
        }
    }


}