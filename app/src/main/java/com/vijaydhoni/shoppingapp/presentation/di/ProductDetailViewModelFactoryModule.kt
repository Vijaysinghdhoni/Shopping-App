package com.vijaydhoni.shoppingapp.presentation.di

import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.AddNewProductToCartUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetCurrentProductFromUserCart
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.IncreaseQuantityOfProductInCartUseCase
import com.vijaydhoni.shoppingapp.presentation.viewmodels.ProductDetailsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProductDetailViewModelFactoryModule {

    @Provides
    @Singleton
    fun providesProductDetailViewModelFactory(
        getCurrentProductFromUserCart: GetCurrentProductFromUserCart,
        addNewProductToCartUseCase: AddNewProductToCartUseCase,
        increaseQuantityOfProductInCartUseCase: IncreaseQuantityOfProductInCartUseCase
    ): ProductDetailsViewModelFactory {
        return ProductDetailsViewModelFactory(
            getCurrentProductFromUserCart,
            addNewProductToCartUseCase,
            increaseQuantityOfProductInCartUseCase
        )
    }

}