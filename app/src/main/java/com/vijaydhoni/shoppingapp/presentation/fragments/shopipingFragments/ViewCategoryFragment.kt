package com.vijaydhoni.shoppingapp.presentation.fragments.shopipingFragments

import android.os.Bundle
import android.util.Log
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
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.ViewCategoryFragmentArgs
import com.vijaydhoni.shoppingapp.data.util.HorizontalItemdecoration
import com.vijaydhoni.shoppingapp.data.util.VerticalItemdecoration
import com.vijaydhoni.shoppingapp.data.util.hideBottomNavView
import com.vijaydhoni.shoppingapp.databinding.FragmentViewCategoryBinding
import com.vijaydhoni.shoppingapp.presentation.adapters.BestProductsAdapter
import com.vijaydhoni.shoppingapp.presentation.viewmodels.CategoryViewModel
import com.vijaydhoni.shoppingapp.presentation.viewmodels.CategoryViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ViewCategoryFragment : Fragment() {
    private lateinit var binding: FragmentViewCategoryBinding

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideBottomNavView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args by navArgs<ViewCategoryFragmentArgs>()
        val category = args.categoryName
        Log.d("category", "Category Name: ${category.name}")
        binding.categoryTitileName.text = category.name
        binding.cancelImg.setOnClickListener {
            findNavController().navigateUp()
        }
        submitDataToAdapter(category.name)
        setRecylerView()
        showHideProgressBar()

        categoryAdpter.onclick = {
            val bundle = Bundle()
            bundle.putParcelable("product", it)
            findNavController().navigate(
                R.id.action_viewCategoryFragment_to_productDetailsFragment,
                bundle
            )
        }
    }

    private fun showHideProgressBar() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                categoryAdpter.loadStateFlow.collectLatest {
                    binding.progress.isVisible = it.append is LoadState.Loading

                }
            }
        }

    }

    private fun submitDataToAdapter(categoryName: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                when (categoryName) {
                    "Sports" -> {
                        categoryViewmodel.sportsProduct.collectLatest {
                            categoryAdpter.submitData(it)
                            return@collectLatest
                        }
                    }

                    "Accessories" -> {
                        categoryViewmodel.accessoriesProducts.collectLatest {
                            categoryAdpter.submitData(it)
                            return@collectLatest
                        }
                    }

                    "Mens Clothes" -> {
                        categoryViewmodel.mensProduct.collectLatest {
                            categoryAdpter.submitData(it)
                            return@collectLatest
                        }
                    }

                    "Footwear" -> {
                        categoryViewmodel.footwearProducts.collectLatest {
                            categoryAdpter.submitData(it)
                            return@collectLatest
                        }
                    }

                    "Special Deals" -> {
                        categoryViewmodel.specialProductPaging.collectLatest {
                            categoryAdpter.submitData(it)
                            return@collectLatest
                        }
                    }
                    else -> {
                        categoryViewmodel.bestDealsPaging.collectLatest {
                            categoryAdpter.submitData(it)
                            return@collectLatest
                        }
                        Log.d("category", categoryName)
                    }
                }

            }
        }


    }

    private fun setRecylerView() {
        binding.categoryRvId.apply {
            adapter = categoryAdpter
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            addItemDecoration(HorizontalItemdecoration())
            addItemDecoration(VerticalItemdecoration())
        }
    }


}