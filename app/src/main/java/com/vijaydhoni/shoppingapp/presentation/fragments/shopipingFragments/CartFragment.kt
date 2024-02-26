package com.vijaydhoni.shoppingapp.presentation.fragments.shopipingFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.data.model.UserCartProductsList
import com.vijaydhoni.shoppingapp.data.util.QuantityChange
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.data.util.VerticalItemdecoration
import com.vijaydhoni.shoppingapp.data.util.showBottomNavView
import com.vijaydhoni.shoppingapp.databinding.FragmentCartBinding
import com.vijaydhoni.shoppingapp.presentation.adapters.CartProductRvAdapter
import com.vijaydhoni.shoppingapp.presentation.viewmodels.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val cartViewModel by activityViewModels<CartViewModel>()
    private lateinit var cartProductRvAdapter: CartProductRvAdapter
    private var cartProducts: UserCartProductsList? = null
    var totalPrice = 0f
//    @Inject
//    lateinit var cartViewModelFactory: CartViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showBottomNavView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onHomeClick()
        cartViewModel.getCartProducts()
        setCartProductRVAdapter()


        cartProductRvAdapter.onClickRemove = {
            cartViewModel.increaseOrdecrease(it, QuantityChange.DECREASE)
        }

        cartProductRvAdapter.onClickAdd = {
            cartViewModel.increaseOrdecrease(it, QuantityChange.INCREASE)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cartViewModel.productsPrice.collectLatest { price ->
                    price?.let {
                        totalPrice = it
                        binding.totalPriceCart.text = "Rs $totalPrice"
                    }
                }
            }
        }



        cartProductObserve()
        upDateProduct()
        checkOutBttnClick()
        alertBoxForDeletingProduct()
    }

    private fun cartProductObserve() {
        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cartViewModel.cartProduct.collectLatest { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            resource.data?.let { cartproduct ->
                                binding.progressBar.visibility = View.INVISIBLE
                                if (cartproduct.isEmpty()) {
                                    Toast.makeText(
                                        requireContext(),
                                        "No Product in Cart",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    binding.apply {
                                        emptyCartanimationView.visibility = View.VISIBLE

                                        mycartRv.visibility = View.GONE
                                        totalPriceLayout.visibility = View.GONE
                                        checkOutBttn.visibility = View.GONE


                                    }

                                } else {
                                    cartProductRvAdapter.differ.submitList(cartproduct)
                                    cartProducts = UserCartProductsList(cartproduct)
                                }
                            }
                        }

                        is Resource.Error -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            resource.message?.let {
                                Toast.makeText(
                                    requireContext(),
                                    it,
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        }

                    }

                }
            }


        }
    }

    private fun upDateProduct() {
        cartViewModel.updateProduct.observe(viewLifecycleOwner) {

            when (it) {

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                }

                else -> {
                }

            }

        }
    }

    private fun checkOutBttnClick() {
        binding.checkOutBttn.setOnClickListener {
            val action = CartFragmentDirections.actionCartFragmentToBillingFragment(
                totalPrice,
                true,
                cartProducts
            )
            findNavController().navigate(action)
        }
    }

    private fun alertBoxForDeletingProduct() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                cartViewModel.alertBox.collectLatest {
                    val alertDialog = AlertDialog.Builder(requireContext()).apply {
                        setTitle("Delete item from cart")
                        setMessage("Do you want to delete this item from your cart?")
                        setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                        }
                        setPositiveButton("Yes") { dialog, _ ->
                            cartViewModel.deleteProduct(it)
                            dialog.dismiss()
                        }
                    }
                    alertDialog.create()
                    alertDialog.show()
                }
            }

        }
    }

    private fun setCartProductRVAdapter() {
        cartProductRvAdapter = CartProductRvAdapter()
        binding.mycartRv.apply {
            adapter = cartProductRvAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(VerticalItemdecoration())
        }

    }

    private fun onHomeClick() {
        val btm = activity?.findViewById<BottomNavigationView>(R.id.bnv_shopping)
        btm?.menu?.getItem(0)?.setOnMenuItemClickListener {
            activity?.onBackPressed()
            true
        }
    }


    override fun onResume() {
        super.onResume()
        showBottomNavView()
    }

}