package com.vijaydhoni.shoppingapp.presentation.fragments.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.data.util.VerticalItemdecoration
import com.vijaydhoni.shoppingapp.data.util.hideBottomNavView
import com.vijaydhoni.shoppingapp.databinding.FragmentAllOrdersBinding
import com.vijaydhoni.shoppingapp.presentation.adapters.AllOrdersRvAdapter
import com.vijaydhoni.shoppingapp.presentation.viewmodels.AllOrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllOrdersFragment : Fragment() {
    private lateinit var binding: FragmentAllOrdersBinding
    private  val viewModel: AllOrdersViewModel by viewModels()
    private lateinit var allOrderadapter: AllOrdersRvAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideBottomNavView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
        observeAllorders()
        allOrderadapter.itemClick = {
            val bundle = Bundle()
            bundle.putParcelable("userOrder", it)
            findNavController().navigate(
                R.id.action_allOrdersFragment_to_orderDetailFragment,
                bundle
            )
        }

        binding.cancelImg.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun observeAllorders() {

        viewModel.allOrders.observe(viewLifecycleOwner) {

            when (it) {

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    it.data?.let { orders ->
                        if (orders.isEmpty()) {
                            hideViewShowEmpty()
                        }else{
                            allOrderadapter.differ.submitList(orders)
                        }
                    }

                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

            }

        }

    }

    private fun hideViewShowEmpty() {
        binding.allOrdersRv.visibility = View.GONE
        binding.progressBar.visibility = View.GONE

        binding.emptyCartanimationView.visibility = View.VISIBLE
        binding.noOrdrsYt.visibility = View.VISIBLE
    }

    private fun setUpRv() {
        allOrderadapter = AllOrdersRvAdapter()
        binding.allOrdersRv.apply {
            adapter = allOrderadapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(VerticalItemdecoration())
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllOrders()
    }
}