package com.vijaydhoni.shoppingapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vijaydhoni.shoppingapp.data.model.UserCartProduct
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.AddNewProductToCartUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetCurrentProductFromUserCart
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.IncreaseQuantityOfProductInCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getCurrentProductFromUserCart: GetCurrentProductFromUserCart,
    private val addNewProductToCartUseCase: AddNewProductToCartUseCase,
    private val increaseProductQuantityUseCase: IncreaseQuantityOfProductInCartUseCase
) : ViewModel() {

    private val _addtocart: MutableLiveData<Resource<UserCartProduct>> = MutableLiveData()
    val addtocart: LiveData<Resource<UserCartProduct>> get() = _addtocart

    fun addToCart(userCartProduct: UserCartProduct) {

        viewModelScope.launch(Dispatchers.IO) {
            _addtocart.postValue(Resource.Loading())

            val snapshot = getCurrentProductFromUserCart.execute(cartProduct = userCartProduct)

            if (snapshot is Resource.Success) {
                snapshot.data?.documents.let {
                    if (it.isNullOrEmpty()) {
                        Log.d("MyTag", "product is null")
                        addNewProdcttoCart(userCartProduct)
                    } else {
                        val cartproduct = it.first().toObject(UserCartProduct::class.java)
                        if (cartproduct?.product == userCartProduct.product && cartproduct.selectedSize == userCartProduct.selectedSize && cartproduct.selectedColor == userCartProduct.selectedColor) {
                            val documentId = it.first().id
                            Log.d("MyTag", "product is increasing")
                            increaseQuantityofProductInCart(userCartProduct, documentId)
                        } else {
                            Log.d("MyTag", "product is null and not increasing")
                            addNewProdcttoCart(userCartProduct)
                        }
                    }
                }
            } else {
                _addtocart.postValue(snapshot.message?.let { Resource.Error(it) })
            }


        }

    }

    private suspend fun increaseQuantityofProductInCart(
        userCartProduct: UserCartProduct,
        documentId: String
    ) {
        val increasedProduct = increaseProductQuantityUseCase.execute(documentId, userCartProduct)
        _addtocart.postValue(increasedProduct)
    }

    private suspend fun addNewProdcttoCart(userCartProduct: UserCartProduct) {
        val cartProduct = addNewProductToCartUseCase.execute(cartProduct = userCartProduct)
        _addtocart.postValue(cartProduct)
    }


}