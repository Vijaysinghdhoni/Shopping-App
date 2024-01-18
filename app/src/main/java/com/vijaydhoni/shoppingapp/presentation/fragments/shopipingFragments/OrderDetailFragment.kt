package com.vijaydhoni.shoppingapp.presentation.fragments.shopipingFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.vijaydhoni.shoppingapp.data.model.Order
import com.vijaydhoni.shoppingapp.data.util.OrderStatus
import com.vijaydhoni.shoppingapp.data.util.VerticalItemdecoration
import com.vijaydhoni.shoppingapp.data.util.getOrderStatus
import com.vijaydhoni.shoppingapp.data.util.hideBottomNavView
import com.vijaydhoni.shoppingapp.databinding.FragmentOrderDetailBinding
import com.vijaydhoni.shoppingapp.presentation.adapters.BillingProductsRvAdapter

class OrderDetailFragment : Fragment() {
    private lateinit var binding: FragmentOrderDetailBinding
    private lateinit var productsRvAdapter: BillingProductsRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideBottomNavView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
        val args by navArgs<OrderDetailFragmentArgs>()
        val order = args.userOrder

        binding.cancelImg.setOnClickListener {
            findNavController().navigateUp()
        }

        if (order != null) {
            setUpUi(order)
        }

    }

    private fun setUpUi(order: Order) {

        binding.apply {
            toolbarTitle.text = "Order #${order.orderId}"
            usrAddName.text = order.address.fullName
            usrAddrs.text =
                "${order.address.fullAddress},${order.address.city},${order.address.state}"
            usrPhnNo.text = order.address.phone

            productsRvAdapter.differ.submitList(order.cartProducts)

            stepView.setSteps(
                mutableListOf(
                    OrderStatus.Ordered.status,
                    OrderStatus.Confirmed.status,
                    OrderStatus.Shipped.status,
                    OrderStatus.Delivered.status
                )
            )


            val currentOrderState = when (getOrderStatus(order.orderStatus)) {
                is OrderStatus.Ordered -> 0
                is OrderStatus.Confirmed -> 1
                is OrderStatus.Shipped -> 2
                is OrderStatus.Delivered -> 3
                else -> 0
            }

            stepView.go(currentOrderState, false)
            if (currentOrderState == 3) {
                stepView.done(true)
            }

        }

    }

    private fun setUpRv() {
        productsRvAdapter = BillingProductsRvAdapter()
        binding.productsRv.apply {
            adapter = productsRvAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(VerticalItemdecoration())
        }
    }


}