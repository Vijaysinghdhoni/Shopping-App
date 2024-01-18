package com.vijaydhoni.shoppingapp.presentation.di

import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ShoppingUseCasesModule {


    @Provides
    @Singleton
    fun providesGetSportsUseCase(shoppingRepository: ShoppingRepository): GetSportsProductsUseCase {
        return GetSportsProductsUseCase(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesGetmalesUseCase(shoppingRepository: ShoppingRepository): GetMensProductsUseCase {
        return GetMensProductsUseCase(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesGetFootwearUseCase(shoppingRepository: ShoppingRepository): GetFootWearProductsUseCase {
        return GetFootWearProductsUseCase(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesGetAccessoriesUseCase(shoppingRepository: ShoppingRepository): GetAccessoriesProductsUseCase {
        return GetAccessoriesProductsUseCase(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesGetCurrentProductFromUserCartUseCase(shoppingRepository: ShoppingRepository): GetCurrentProductFromUserCart {
        return GetCurrentProductFromUserCart(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesIncreaseCurrentProductIncartUseCase(shoppingRepository: ShoppingRepository): IncreaseQuantityOfProductInCartUseCase {
        return IncreaseQuantityOfProductInCartUseCase(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesAddNewProductToUseCart(shoppingRepository: ShoppingRepository): AddNewProductToCartUseCase {
        return AddNewProductToCartUseCase(shoppingRepository)
    }


    @Provides
    @Singleton
    fun providesdecreaseProductInCartUseCase(shoppingRepository: ShoppingRepository): DecreaseProductQuantityInCartUseCase {
        return DecreaseProductQuantityInCartUseCase(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesDeleteproductInCartUseCase(shoppingRepository: ShoppingRepository): DeleteProductInCartUseCase {
        return DeleteProductInCartUseCase(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesaddAddressUseCase(shoppingRepository: ShoppingRepository): AddUserAddressUseCase {
        return AddUserAddressUseCase(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesgetUsersAddressesUseCase(shoppingRepository: ShoppingRepository): GetUserAddressesUseCase {
        return GetUserAddressesUseCase(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesGetCurrentAddressFromUserAddressesUseCase(shoppingRepository: ShoppingRepository): GetCurrentAddressFromUserAddressesUseCase {
        return GetCurrentAddressFromUserAddressesUseCase(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesUpdateUsersAddressUseCase(shoppingRepository: ShoppingRepository): UpdateUserAddressUseCase {
        return UpdateUserAddressUseCase(shoppingRepository)
    }


    @Provides
    @Singleton
    fun providesdeleteUsersAddressUseCase(shoppingRepository: ShoppingRepository): DeleteUserAddressUSeCase {
        return DeleteUserAddressUSeCase(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesPlaceOrderUseCase(shoppingRepository: ShoppingRepository): PlaceOrderUseCase {
        return PlaceOrderUseCase(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesGetUserUseCase(shoppingRepository: ShoppingRepository): GetUserUseCase {
        return GetUserUseCase(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesSaveUserInfoUseCase(shoppingRepository: ShoppingRepository): SaveUserInfoUSeCase {
        return SaveUserInfoUSeCase(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesSaveUserProfileImgUseCase(shoppingRepository: ShoppingRepository): SaveUserProfileImgUseCase {
        return SaveUserProfileImgUseCase(shoppingRepository)
    }


    @Provides
    @Singleton
    fun providesAllOrdersUseCase(shoppingRepository: ShoppingRepository): GetAllOrdersUseCase {
        return GetAllOrdersUseCase(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesLogoutUseCase(shoppingRepository: ShoppingRepository): LogoutUseCase {
        return LogoutUseCase(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesBestProductsPagingSourceUseCase(shoppingRepository: ShoppingRepository): BestProductsPagingSourceUSeCase {
        return BestProductsPagingSourceUSeCase(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesBestDealsPagingSourceUseCase(shoppingRepository: ShoppingRepository): GetBestDealsPagingUseCase {
        return GetBestDealsPagingUseCase(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesSpecialProductsPagingSourceUseCase(shoppingRepository: ShoppingRepository): GetSpecialProductPagingUseCase {
        return GetSpecialProductPagingUseCase(shoppingRepository)
    }


    @Provides
    @Singleton
    fun providesRecentOrderUseCase(shoppingRepository: ShoppingRepository): GetRecentOrderUseCase {
        return GetRecentOrderUseCase(shoppingRepository)
    }

    @Provides
    @Singleton
    fun providesGetSearchedUsecase(shoppingRepository: ShoppingRepository): GetSearchedProductUseCase {
        return GetSearchedProductUseCase(shoppingRepository)
    }


}