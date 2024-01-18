package com.vijaydhoni.shoppingapp.presentation.fragments.categoresFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.data.model.Category
import com.vijaydhoni.shoppingapp.data.util.HorizontalItemdecoration
import com.vijaydhoni.shoppingapp.data.util.VerticalItemdecoration
import com.vijaydhoni.shoppingapp.data.util.showBottomNavView
import com.vijaydhoni.shoppingapp.databinding.FragmentMalesBinding
import com.vijaydhoni.shoppingapp.presentation.adapters.BestProductsAdapter
import com.vijaydhoni.shoppingapp.presentation.viewmodels.CategoryViewModel
import com.vijaydhoni.shoppingapp.presentation.viewmodels.CategoryViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MalesFragment : Fragment() {
    private lateinit var binding: FragmentMalesBinding
    private val categoryAdpter: BestProductsAdapter by lazy {
        BestProductsAdapter()
    }

    @Inject
    lateinit var categoryViewModelFactory: CategoryViewModelFactory

    private val categoryViewmodel: CategoryViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            categoryViewModelFactory
        )[CategoryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        showBottomNavView()
        binding = FragmentMalesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecylerView()
        showLoading()
        setUpvewAllProducts()
        categoryAdpter.onclick = {

            val b = Bundle().apply {
                putParcelable("product", it)
            }
            findNavController().navigate(
                R.id.action_shoppingHomePageFragment_to_productDetailsFragment,
                b
            )

        }

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                categoryViewmodel.mensProduct.collectLatest {
                    categoryAdpter.submitData(it)
                }
            }

        }
    }


    private fun showLoading() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                categoryAdpter.loadStateFlow.collectLatest {
                    binding.malesProgressBar.isVisible = it.append is LoadState.Loading

                }
            }
        }
    }

    private fun setUpvewAllProducts() {
        binding.productViewAll.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("category_name", Category("Mens Clothes"))
            findNavController().navigate(
                R.id.action_shoppingHomePageFragment_to_viewCategoryFragment,
                bundle
            )
        }
    }


    private fun setRecylerView() {
        binding.malesRv.apply {
            adapter = categoryAdpter
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            addItemDecoration(VerticalItemdecoration())
            addItemDecoration(HorizontalItemdecoration())
        }
    }

}