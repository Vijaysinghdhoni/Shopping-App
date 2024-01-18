package com.vijaydhoni.shoppingapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vijaydhoni.shoppingapp.data.model.UserCartProduct
import com.vijaydhoni.shoppingapp.data.util.QuantityChange
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.DecreaseProductQuantityInCartUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.DeleteProductInCartUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetCurrentProductFromUserCart
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.IncreaseQuantityOfProductInCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val decreaseProductQuantityInCartUseCase: DecreaseProductQuantityInCartUseCase,
    private val increaseQuantityOfProductInCartUseCase: IncreaseQuantityOfProductInCartUseCase,
    private val deleteProductInCartUseCase: DeleteProductInCartUseCase,
    private val getCurrentProductFromUserCart: GetCurrentProductFromUserCart,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _cartProduct = MutableStateFlow<Resource<List<UserCartProduct>>>(Resource.Loading())
    val cartProduct: StateFlow<Resource<List<UserCartProduct>>> get() = _cartProduct


    fun getCartProducts() {

        firestore.collection("user").document(auth.uid!!).collection("cart")
            .addSnapshotListener { value, error ->
                if (error != null || value == null) {
                    viewModelScope.launch { _cartProduct.emit(Resource.Error(error?.message.toString())) }
                } else {
                    val cartProducts = value.toObjects(UserCartProduct::class.java)
                    viewModelScope.launch { _cartProduct.emit(Resource.Success(cartProducts)) }
                }
            }
    }

    val productsPrice = cartProduct.map {
        when (it) {
            is Resource.Success -> {
                getTotalPrices(it.data!!)
            }
            else -> null
        }
    }


    private fun getTotalPrices(data: List<UserCartProduct>): Float {

        return data.sumByDouble { cartProduct ->
            var price =
                findPriceAfterOffer(cartProduct.product.price, cartProduct.product.offerPercentage)
            (price * cartProduct.quantity).toDouble()
        }.toFloat()

    }

    private fun findPriceAfterOffer(price: Float, offerPercentage: Float?): Float {

        if (offerPercentage == null) {
            return price
        }
        val discountAmount = price * (offerPercentage.div(100))
        return price - discountAmount
    }


    private val _alertbox = MutableSharedFlow<UserCartProduct>()
    val alertBox: SharedFlow<UserCartProduct> = _alertbox

    private val _updateProduct = MutableLiveData<Resource<UserCartProduct>>()
    val updateProduct: LiveData<Resource<UserCartProduct>> = _updateProduct

    fun increaseOrdecrease(userCartProduct: UserCartProduct, quantityChange: QuantityChange) {

        viewModelScope.launch {
            _updateProduct.postValue(Resource.Loading())
            val currentProductSnapShot = getCurrentProductFromUserCart.execute(userCartProduct)
            val documentid = currentProductSnapShot.data?.documents?.first()?.id

            when (quantityChange) {
                QuantityChange.INCREASE -> {
                    _updateProduct.postValue(Resource.Success(userCartProduct))
                    increaseProduct(documentid, userCartProduct)
                }
                QuantityChange.DECREASE -> {
                    _updateProduct.postValue(Resource.Success(userCartProduct))
                    if (userCartProduct.quantity == 1) {
                        _alertbox.emit(userCartProduct)
                    } else {
                        decreaseQuantity(documentid, userCartProduct)
                    }
                }

            }

        }
    }

    fun deleteProduct(userCartProduct: UserCartProduct) {
        viewModelScope.launch {
            val currentProductSnapShot = getCurrentProductFromUserCart.execute(userCartProduct)
            val documentid = currentProductSnapShot.data?.documents?.first()?.id
            deleteProductInCartUseCase.execute(documentid!!)
        }

    }

    private suspend fun decreaseQuantity(documentid: String?, userCartProduct: UserCartProduct) {
        decreaseProductQuantityInCartUseCase.execute(documentid!!, userCartProduct)
    }

    private suspend fun increaseProduct(documentid: String?, userCartProduct: UserCartProduct) {
        increaseQuantityOfProductInCartUseCase.execute(documentid!!, userCartProduct)
    }


}


