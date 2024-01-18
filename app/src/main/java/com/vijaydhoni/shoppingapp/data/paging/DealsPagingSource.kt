package com.vijaydhoni.shoppingapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.vijaydhoni.shoppingapp.data.model.Product
import com.vijaydhoni.shoppingapp.data.util.Constants.PAGE_SIZE
import com.vijaydhoni.shoppingapp.data.util.Constants.PRODUCT_CATEGORY
import com.vijaydhoni.shoppingapp.data.util.Constants.PRODUCT_COLLECTION
import kotlinx.coroutines.tasks.await

class DealsPagingSource(private val firestore: FirebaseFirestore, private val category: String) :
    PagingSource<QuerySnapshot, Product>() {
    override fun getRefreshKey(state: PagingState<QuerySnapshot, Product>): QuerySnapshot? {
        return null
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Product> {
        return try {
            val currentPage = params.key ?: firestore.collection(PRODUCT_COLLECTION).whereEqualTo(
                PRODUCT_CATEGORY, category
            ).limit(PAGE_SIZE.toLong()).get().await()

            val lastVisibleProduct = currentPage.documents[currentPage.size() - 1]

            val nextpage = firestore.collection(PRODUCT_COLLECTION).whereEqualTo(
                PRODUCT_CATEGORY, category
            ).limit(PAGE_SIZE.toLong()).startAfter(lastVisibleProduct).get().await()

            LoadResult.Page(
                data = currentPage.toObjects(Product::class.java),
                prevKey = null,
                nextKey = nextpage
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        } catch (ex: FirebaseFirestoreException) {
            LoadResult.Error(ex)
        }
    }


}