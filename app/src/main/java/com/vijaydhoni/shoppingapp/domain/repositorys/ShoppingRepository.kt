package com.vijaydhoni.shoppingapp.domain.repositorys

import androidx.paging.PagingData
import com.google.firebase.firestore.QuerySnapshot
import com.vijaydhoni.shoppingapp.data.model.*
import com.vijaydhoni.shoppingapp.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {


    fun getBestProductsPaging(): Flow<PagingData<Product>>

    fun getBestDealsPaging(): Flow<PagingData<Product>>

    fun getSpecialProductsPaging(): Flow<PagingData<Product>>

    fun getSportsProductsPaging(): Flow<PagingData<Product>>

    fun getMensProductsPaging(): Flow<PagingData<Product>>

    fun getFootWearProductsPaging(): Flow<PagingData<Product>>

    fun getAccessoriesProductsPaging(): Flow<PagingData<Product>>


    suspend fun getCurrentProductsFromUserCart(cartProduct: UserCartProduct): Resource<QuerySnapshot>

    suspend fun addNewProductToCart(cartProduct: UserCartProduct): Resource<UserCartProduct>

    suspend fun increaseProductQuantityInCart(
        documentId: String,
        userCartProduct: UserCartProduct
    ): Resource<UserCartProduct>

    fun logout(): Resource<Unit>


    suspend fun decreaseProductQuantityInCart(
        documentId: String,
        userCartProduct: UserCartProduct
    ): Resource<UserCartProduct>

    suspend fun deleteProductInCart(documentId: String): Resource<Unit>


    suspend fun addUserAddress(address: Address): Resource<Address>

    suspend fun getUserAddresses(): Resource<List<Address>>

    suspend fun updateUserAddress(newAddress: Address, addressId: String): Resource<Address>

    suspend fun getCurrentAddresFromUserAddress(address: Address): Resource<QuerySnapshot>

    suspend fun deleteUSerAddress(documentID: String): Resource<Unit>

    suspend fun placeOrder(order: Order): Resource<Order>

    suspend fun getUser(): Resource<User?>

    suspend fun saveUserInfo(user: User, shouldRetrievedOldImage: Boolean): Resource<User>
    suspend fun saveUserProfileImg(imageByteArray: ByteArray): Resource<String>

    suspend fun getAllOrders(): Resource<List<Order>>

    suspend fun getRecentOrder(): Resource<List<Order>>

    suspend fun getSearchedProduct(productName: String): Resource<List<Product>>

}