package com.vijaydhoni.shoppingapp.data.util

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.presentation.activities.ShoppingActivity


fun Fragment.hideBottomNavView() {

    val activity = activity as? ShoppingActivity
    activity?.findViewById<BottomNavigationView>(R.id.bnv_shopping)?.let { bnv ->
        bnv.visibility = View.GONE
    }

}


fun Fragment.showBottomNavView() {

    val activity = activity as? ShoppingActivity
    activity?.findViewById<BottomNavigationView>(R.id.bnv_shopping)?.let { bnv ->
        bnv.visibility = View.VISIBLE
    }

}