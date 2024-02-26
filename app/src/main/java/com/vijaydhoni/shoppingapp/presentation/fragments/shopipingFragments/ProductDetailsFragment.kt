package com.vijaydhoni.shoppingapp.presentation.fragments.shopipingFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.data.model.UserCartProduct
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.data.util.hideBottomNavView
import com.vijaydhoni.shoppingapp.databinding.FragmentProductDetailsBinding
import com.vijaydhoni.shoppingapp.presentation.adapters.ColorRVAdapter
import com.vijaydhoni.shoppingapp.presentation.adapters.ProductDetailViewPagerAdapter
import com.vijaydhoni.shoppingapp.presentation.adapters.SizesRVAdapter
import com.vijaydhoni.shoppingapp.presentation.viewmodels.ProductDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var colorAdapter: ColorRVAdapter
    private lateinit var sizeAdapter: SizesRVAdapter
    private lateinit var imageAdapter: ProductDetailViewPagerAdapter
    private var selectedColor: Int? = null
    private var selectedSizes: String? = null
    private val productDetailsViewModel: ProductDetailsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideBottomNavView()
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: ProductDetailsFragmentArgs by navArgs()
        val product = args.product
        setupViewpagerAdater()
        setupcolorRvAdapter()
        setupsizeRvAdapter()

        binding.cancelImg.setOnClickListener {
            findNavController().navigateUp()
        }

        colorAdapter.onClick = {
            selectedColor = it
        }

        sizeAdapter.onClick = {
            selectedSizes = it
        }

        binding.apply {
            productName.text = product.productName
            productPrice.text = "Rs ${product.price}"
            productDescription.text = product.productDescription

            if (product.colours.isNullOrEmpty()) {
                colorTxt.visibility = View.INVISIBLE
            }
            if (product.sizes.isNullOrEmpty()) {
                sizeTx.visibility = View.INVISIBLE
            }
        }

        imageAdapter.differ.submitList(product.images)

        product.colours?.let {
            colorAdapter.differ.submitList(it)
        }

        product.sizes?.let {
            sizeAdapter.differ.submitList(it)
        }

        binding.addToCartBttn.setOnClickListener {
            binding.addToCartBttn.startAnimation()
        }

        productDetailsViewModel.addtocart.observe(viewLifecycleOwner) { resource ->

            when (resource) {

                is Resource.Loading -> {
                    binding.addToCartBttn.startAnimation()
                }

                is Resource.Success -> {
                    binding.addToCartBttn.revertAnimation {
                        binding.addToCartBttn.text = "Product Added To Cart"
                    }
                    binding.addToCartBttn.setBackgroundColor(resources.getColor(R.color.g_black))

                }

                is Resource.Error -> {
                    binding.addToCartBttn.revertAnimation()
                    resource.message?.let {
                        Toast.makeText(
                            requireContext(),
                            it,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }


        binding.addToCartBttn.setOnClickListener {
            productDetailsViewModel.addToCart(
                UserCartProduct(
                    product,
                    1,
                    selectedColor,
                    selectedSizes
                )
            )
        }

    }

    private fun setupViewpagerAdater() {
        imageAdapter = ProductDetailViewPagerAdapter()
        binding.imageViewpager.adapter = imageAdapter

        TabLayoutMediator(binding.tabLayout, binding.imageViewpager) { _, _ ->

        }.attach()

    }

    private fun setupcolorRvAdapter() {
        colorAdapter = ColorRVAdapter()
        binding.colorRV.apply {
            adapter = colorAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupsizeRvAdapter() {
        sizeAdapter = SizesRVAdapter()
        binding.sizeRV.apply {
            adapter = sizeAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

}