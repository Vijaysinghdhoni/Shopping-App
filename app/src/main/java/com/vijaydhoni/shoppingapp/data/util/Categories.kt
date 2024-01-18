package com.vijaydhoni.shoppingapp.data.util

import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.data.model.Category

object Categories {

    val categorys = listOf<Category>(
        Category("Sports", R.drawable.cricket_bat),
        Category("Accessories", R.drawable.accessios),
        Category("Mens Clothes", R.drawable.mens_clothes_collection),
        Category("Footwear", R.drawable.footwear),
        Category("Mens Combo", R.drawable.mens_combo),
        Category("Special Deals", R.drawable.special_products)
    )

}