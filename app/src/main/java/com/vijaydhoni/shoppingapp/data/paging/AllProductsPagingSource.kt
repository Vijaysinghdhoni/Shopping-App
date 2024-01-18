package com.vijaydhoni.shoppingapp.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.vijaydhoni.shoppingapp.data.model.Product
import kotlinx.coroutines.tasks.await

class AllProductsPagingSource(private val fireStore: FirebaseFirestore) :
    PagingSource<QuerySnapshot, Product>() {



    override fun getRefreshKey(state: PagingState<QuerySnapshot, Product>): QuerySnapshot? {
        return null
    }


    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Product> {
        return try {
            val currentPage = params.key ?: fireStore.collection("Products")
                .limit(6).get().await()

            Log.d(
                "Paging3", "Loading page: ${params.key}, pageSize: ${params.loadSize}"
            )

            val lastVisibleProduct = currentPage.documents[currentPage.size() - 1]

            val nextPage = fireStore.collection("Products")
                .limit(6).startAfter(lastVisibleProduct).get().await()

            LoadResult.Page(
                data = currentPage.toObjects(Product::class.java),
                prevKey = null,
                nextKey = nextPage
            )

        } catch (ex: Exception) {
            LoadResult.Error(ex)
        } catch (ex: FirebaseFirestoreException) {
            LoadResult.Error(ex)
        }
    }


}