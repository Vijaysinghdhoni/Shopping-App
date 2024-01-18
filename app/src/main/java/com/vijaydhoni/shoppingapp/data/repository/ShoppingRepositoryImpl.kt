package com.vijaydhoni.shoppingapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.*
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.StorageReference
import com.vijaydhoni.shoppingapp.data.model.*
import com.vijaydhoni.shoppingapp.data.paging.AllProductsPagingSource
import com.vijaydhoni.shoppingapp.data.paging.DealsPagingSource
import com.vijaydhoni.shoppingapp.data.util.Constants.ACCESSORIES_CATEGORY
import com.vijaydhoni.shoppingapp.data.util.Constants.BEST_DEALS
import com.vijaydhoni.shoppingapp.data.util.Constants.FOOTWEAR_CATEGORY
import com.vijaydhoni.shoppingapp.data.util.Constants.MALES_CATEGORY
import com.vijaydhoni.shoppingapp.data.util.Constants.PAGE_SIZE
import com.vijaydhoni.shoppingapp.data.util.Constants.PRODUCT_COLLECTION
import com.vijaydhoni.shoppingapp.data.util.Constants.SPECIAL_CATEGORY
import com.vijaydhoni.shoppingapp.data.util.Constants.SPORTS_CATEGORY
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.*

class ShoppingRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val storage: StorageReference
) : ShoppingRepository {


    override fun getBestProductsPaging(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = 6, enablePlaceholders = false),
            pagingSourceFactory = { AllProductsPagingSource(firestore) }
        ).flow
    }

    override fun getBestDealsPaging(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { DealsPagingSource(firestore, BEST_DEALS) }
        ).flow
    }

    override fun getSpecialProductsPaging(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { DealsPagingSource(firestore, SPECIAL_CATEGORY) }
        ).flow
    }


    override fun getSportsProductsPaging(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { DealsPagingSource(firestore, SPORTS_CATEGORY) }
        ).flow
    }

    override fun getAccessoriesProductsPaging(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { DealsPagingSource(firestore, ACCESSORIES_CATEGORY) }
        ).flow
    }

    override fun getFootWearProductsPaging(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { DealsPagingSource(firestore, FOOTWEAR_CATEGORY) }
        ).flow
    }

    override fun getMensProductsPaging(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { DealsPagingSource(firestore, MALES_CATEGORY) }
        ).flow
    }


    override suspend fun getCurrentProductsFromUserCart(cartProduct: UserCartProduct): Resource<QuerySnapshot> {

        return try {
            val snapshot =
                firestore.collection("user").document(firebaseAuth.uid!!).collection("cart")
                    .whereEqualTo("product.id", cartProduct.product.id).get().await()
            Resource.Success(snapshot)
        } catch (ex: Exception) {
            Resource.Error(ex.message!!)
        }
    }

    override suspend fun addUserAddress(address: Address): Resource<Address> {
        return try {
            firestore.collection("user").document(firebaseAuth.uid!!).collection("address")
                .document().set(address).await()
            Resource.Success(address)
        } catch (ex: Exception) {
            Resource.Error(ex.message!!)
        }

    }


    override suspend fun getUserAddresses(): Resource<List<Address>> {
        return try {
            val snapshot =
                firestore.collection("user").document(firebaseAuth.uid!!).collection("address")
                    .get().await()

            val userAddressList = snapshot.toObjects(Address::class.java)
            Resource.Success(userAddressList)
        } catch (ex: Exception) {
            Resource.Error(ex.message!!)
        }
    }

    override suspend fun updateUserAddress(
        newAddress: Address,
        addressId: String
    ): Resource<Address> {
        return try {
            firestore.collection("user").document(firebaseAuth.uid!!)
                .collection("address").document(addressId)
                .set(
                    newAddress,
                    SetOptions.merge()
                ) // Use SetOptions.merge() to update only the provided fields
                .await()
            Resource.Success(newAddress)
        } catch (ex: Exception) {
            Resource.Error(ex.message!!)
        }
    }

    override suspend fun getCurrentAddresFromUserAddress(address: Address): Resource<QuerySnapshot> {
        return try {
            val snapshot =
                firestore.collection("user").document(firebaseAuth.uid!!).collection("address")
                    .whereEqualTo("phone", address.phone).get().await()
            Resource.Success(snapshot)
        } catch (ex: Exception) {
            Resource.Error(ex.message!!)
        }
    }

    override suspend fun deleteUSerAddress(documentID: String): Resource<Unit> {
        return try {

            firestore.collection("user").document(firebaseAuth.uid!!)
                .collection("address").document(documentID)
                .delete()
                .await()
            Resource.Success(Unit)
        } catch (ex: Exception) {
            Resource.Error(ex.message!!)
        }
    }


    override suspend fun addNewProductToCart(cartProduct: UserCartProduct): Resource<UserCartProduct> {

        return try {
            firestore.collection("user").document(firebaseAuth.uid!!).collection("cart")
                .document()
                .set(cartProduct).await()
            Resource.Success(cartProduct)
        } catch (ex: Exception) {
            Resource.Error(ex.message!!)
        }

    }

    override suspend fun increaseProductQuantityInCart(
        documentId: String,
        userCartProduct: UserCartProduct
    ): Resource<UserCartProduct> {
        return try {
            firestore.runTransaction { transaction ->
                val documentRef =
                    firestore.collection("user").document(firebaseAuth.uid!!).collection("cart")
                        .document(documentId)
                val document = transaction.get(documentRef)
                val productObject = document.toObject(UserCartProduct::class.java)
                productObject?.let { cartProduct ->
                    val newQuantity = cartProduct.quantity + 1
                    val newProduct = cartProduct.copy(quantity = newQuantity)
                    transaction.set(documentRef, newProduct)
                }
            }.await()
            Resource.Success(userCartProduct)
        } catch (ex: Exception) {
            Resource.Error(ex.message!!)
        }
    }


    override suspend fun decreaseProductQuantityInCart(
        documentId: String,
        userCartProduct: UserCartProduct
    ): Resource<UserCartProduct> {

        return try {
            firestore.runTransaction { transaction ->
                val documentRef =
                    firestore.collection("user").document(firebaseAuth.uid!!).collection("cart")
                        .document(documentId)
                val document = transaction.get(documentRef)
                val productObject = document.toObject(UserCartProduct::class.java)
                productObject?.let { cartProduct ->
                    val newQuantity = cartProduct.quantity - 1
                    val newProduct = cartProduct.copy(quantity = newQuantity)
                    transaction.set(documentRef, newProduct)
                }
            }.await()
            Resource.Success(userCartProduct)
        } catch (ex: Exception) {
            Resource.Error(ex.message!!)
        }
    }

    override suspend fun placeOrder(order: Order): Resource<Order> {
        return try {
            firestore.runBatch { _ ->

                firestore.collection("user").document(firebaseAuth.uid!!).collection("orders")
                    .document().set(order)

                firestore.collection("orders").document().set(order)

                firestore.collection("user").document(firebaseAuth.uid!!).collection("cart")
                    .get().addOnSuccessListener {
                        it.forEach { doc ->
                            doc.reference.delete()
                        }
                    }
            }
            Resource.Success(order)
        } catch (ex: Exception) {
            Resource.Error(ex.message!!)
        }
    }


    override suspend fun deleteProductInCart(documentId: String): Resource<Unit> {

        return try {
            firestore.collection("user").document(firebaseAuth.uid!!).collection("cart")
                .document(documentId).delete().await()
            Resource.Success(Unit)
        } catch (ex: Exception) {
            Resource.Error(ex.message!!)
        }

    }

    override suspend fun getUser(): Resource<User?> {
        return try {
            val snapShot = firestore.collection("user").document(firebaseAuth.uid!!).get().await()
            val user = snapShot.toObject(User::class.java)
            Resource.Success(user)
        } catch (ex: FirebaseFirestoreException) {
            Resource.Error(ex.message!!)
        }
    }

    override suspend fun saveUserInfo(
        user: User,
        shouldRetrievedOldImage: Boolean
    ): Resource<User> {
        return try {
            firestore.runTransaction { transaction ->
                val documentRef = firestore.collection("user").document(firebaseAuth.uid!!)

                if (shouldRetrievedOldImage) {
                    val currentUser = transaction.get(documentRef).toObject(User::class.java)
                    val newUser = user.copy(imagePath = currentUser?.imagePath ?: "")
                    transaction.set(documentRef, newUser)
                } else {
                    transaction.set(documentRef, user)
                }
            }.await()
            Resource.Success(user)
        } catch (ex: FirebaseFirestoreException) {
            Resource.Error(ex.message!!)
        }
    }

    override suspend fun saveUserProfileImg(imageByteArray: ByteArray): Resource<String> {
        return try {
            val imageDirectory =
                storage.child("profileImages/${firebaseAuth.uid}/${UUID.randomUUID()}")
            val result = imageDirectory.putBytes(imageByteArray).await()
            val imageUrl = result.storage.downloadUrl.await().toString()
            Resource.Success(imageUrl)
        } catch (ex: StorageException) {
            Resource.Error(ex.message!!)
        }
    }

    override suspend fun getAllOrders(): Resource<List<Order>> {
        return try {

            val snapShot =
                firestore.collection("user").document(firebaseAuth.uid!!).collection("orders").get()
                    .await()
            val orders = snapShot.toObjects(Order::class.java)
            Resource.Success(orders)
        } catch (ex: FirebaseFirestoreException) {
            Resource.Error(ex.message!!)
        }
    }

    override fun logout(): Resource<Unit> {
        return try {
            firebaseAuth.signOut()
            Resource.Success(Unit)
        } catch (ex: FirebaseAuthException) {
            Resource.Error(ex.message!!)
        }
    }

    override suspend fun getRecentOrder(): Resource<List<Order>> {
        return try {
            val snapShot =
                firestore.collection("user").document(firebaseAuth.uid!!).collection("orders")
                    .orderBy("date", Query.Direction.DESCENDING).limit(1).get().await()
            val order = snapShot.toObjects(Order::class.java)
            Resource.Success(order)
        } catch (ex: Exception) {
            Resource.Error(ex.message!!)
        }
    }

    override suspend fun getSearchedProduct(productName: String): Resource<List<Product>> {
        return try {
            val snapshot = firestore.collection(PRODUCT_COLLECTION)
                .whereGreaterThanOrEqualTo("productName", productName.trim())
                .whereLessThanOrEqualTo("productName", productName.trim() + "\uf8ff")
                .limit(6)
                .get().await()
            val searchProducts = snapshot.toObjects(Product::class.java)
            Resource.Success(searchProducts)
        } catch (ex: Exception) {
            Resource.Error(ex.message!!)
        } catch (ex: FirebaseFirestoreException) {
            Resource.Error(ex.message!!)
        }
    }

}

