package com.vijaydhoni.shoppingapp.presentation.fragments.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.data.model.Address
import com.vijaydhoni.shoppingapp.data.model.Order
import com.vijaydhoni.shoppingapp.data.model.UserCartProductsList
import com.vijaydhoni.shoppingapp.data.util.HorizontalItemdecoration
import com.vijaydhoni.shoppingapp.data.util.OrderStatus
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.data.util.hideBottomNavView
import com.vijaydhoni.shoppingapp.databinding.FragmentBillingBinding
import com.vijaydhoni.shoppingapp.presentation.adapters.BillingAddressPickRvAdapter
import com.vijaydhoni.shoppingapp.presentation.adapters.BillingProductsRvAdapter
import com.vijaydhoni.shoppingapp.presentation.viewmodels.BillingViewModelFactory
import com.vijaydhoni.shoppingapp.presentation.viewmodels.BillingViewmodel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BillingFragment : Fragment() {
    private lateinit var binding: FragmentBillingBinding
    private lateinit var billingViewmodel: BillingViewmodel
    private lateinit var billingAddressPickRvAdapter: BillingAddressPickRvAdapter
    private lateinit var billingProductsRvAdapter: BillingProductsRvAdapter
    private var selectedAddress: Address? = null
    private var totalPrice: Float? = null
    private lateinit var userCartProducts: UserCartProductsList

    @Inject
    lateinit var billingViewModelFactory: BillingViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideBottomNavView()
        billingViewmodel = ViewModelProvider(
            requireActivity(), billingViewModelFactory
        )[BillingViewmodel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBillingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAddressRv()
        setupProductsRv()
        val args by navArgs<BillingFragmentArgs>()

        totalPrice = args.totalPrice
        userCartProducts = args.cartProductsList!!


        if (!args.payment) {
         //   Toast.makeText(requireContext(), "from user Profile", Toast.LENGTH_SHORT).show()
            binding.totalPriceLayout.visibility = View.INVISIBLE
            binding.placeOrderBttn.visibility = View.INVISIBLE
            binding.viewBtmOfRvProducts.visibility = View.INVISIBLE
        }
        if (totalPrice != 0f) {
            billingProductsRvAdapter.differ.submitList(userCartProducts.products)
            binding.totalPrice.text = "Rs $totalPrice"
        }

        billingAddressPickRvAdapter.onclick = {
            selectedAddress = it
        }
        binding.cancelImg.setOnClickListener {
            findNavController().navigateUp()
        }
        observeAddres()
        addAddress()
        placeOrder()
        observePlacedOrder()
    }

    private fun observePlacedOrder() {
        billingViewmodel.placeOrder.observe(viewLifecycleOwner) { event ->

            event.getContentIfNotHandled()?.let {
                when (it) {
                    is Resource.Loading -> {
                        binding.placeOrderBttn.startAnimation()
                    }

                    is Resource.Success -> {
                        binding.placeOrderBttn.revertAnimation()
                        val order = it.data
                        orderCnfrm(order)
                        Log.d("MyTag", "Ordered")
                    }

                    is Resource.Error -> {
                        binding.placeOrderBttn.revertAnimation()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

    private fun orderCnfrm(order: Order?) {

        val bundle = Bundle()
        bundle.putParcelable("order", order)
        findNavController().navigate(R.id.action_billingFragment_to_orderConfirmedFragment, bundle)

    }


    private fun placeOrder() {
        binding.placeOrderBttn.setOnClickListener {

            if (selectedAddress != null && totalPrice != null) {
                val order = Order(
                    OrderStatus.Ordered.status, totalPrice.toString(), userCartProducts.products,
                    selectedAddress!!
                )

                billingViewmodel.placeUserOrder(order)
            } else {
                Log.d("MyTag", "select an address ")
            }
        }
    }

    private fun addAddress() {
        binding.addAddress.setOnClickListener {
            Log.d("MyTag", "Selected Address is $selectedAddress")
            if (selectedAddress != null) {
                val bundle = Bundle()
                bundle.putParcelable("selectedAddress", selectedAddress)
                findNavController().navigate(R.id.action_billingFragment_to_addressFragment, bundle)
            } else {
                findNavController().navigate(R.id.action_billingFragment_to_addressFragment)
            }
        }

    }

    private fun observeAddres() {
        billingViewmodel.address.observe(viewLifecycleOwner) {

            when (it) {

                is Resource.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progress.visibility = View.GONE
                    billingAddressPickRvAdapter.differ.submitList(it.data)
                }

                is Resource.Error -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

                else -> {

                }

            }

        }
    }

    private fun setupProductsRv() {
        billingProductsRvAdapter = BillingProductsRvAdapter()
        binding.productsRV.apply {
            adapter = billingProductsRvAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(HorizontalItemdecoration(26))
        }
    }

    private fun setupAddressRv() {
        billingAddressPickRvAdapter = BillingAddressPickRvAdapter()
        binding.addresRV.apply {
            adapter = billingAddressPickRvAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(HorizontalItemdecoration(23))
        }
    }

    override fun onResume() {
        super.onResume()
        billingViewmodel.getUserAddress()
        selectedAddress = null
        Log.d("MyTag", "Selected Address is $selectedAddress")
        Log.d("MyTag", "Selected Address is onresume")
    }

}