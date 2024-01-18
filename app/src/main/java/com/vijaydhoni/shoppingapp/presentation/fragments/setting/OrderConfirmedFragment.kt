package com.vijaydhoni.shoppingapp.presentation.fragments.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.data.util.hideBottomNavView
import com.vijaydhoni.shoppingapp.databinding.FragmentOrderConfirmedBinding


class OrderConfirmedFragment : Fragment() {
    private lateinit var binding: FragmentOrderConfirmedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideBottomNavView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderConfirmedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args by navArgs<OrderConfirmedFragmentArgs>()

        val order = args.order
        order?.let {

            binding.orderIdTxt.text =
                "We have Recived your order and we will contact you through phone call in next 24H Your Order number is #${order.orderId}"

        }

        binding.cancelImg.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.cartBttn.setOnClickListener {
            findNavController().navigate(R.id.action_orderConfirmedFragment_to_cartFragment)
        }

    }

}