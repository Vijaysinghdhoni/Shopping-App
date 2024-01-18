package com.vijaydhoni.shoppingapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.AddNewProductToCartUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetCurrentProductFromUserCart
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.IncreaseQuantityOfProductInCartUseCase

class ProductDetailsViewModelFactory(
    private val getCurrentProductFromUserCart: GetCurrentProductFromUserCart,
    private val addNewProductToCartUseCase: AddNewProductToCartUseCase,
    private val increaseProductQuantityUseCase: IncreaseQuantityOfProductInCartUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductDetailsViewModel(
            getCurrentProductFromUserCart,
            addNewProductToCartUseCase,
            increaseProductQuantityUseCase
        ) as T
    }
}