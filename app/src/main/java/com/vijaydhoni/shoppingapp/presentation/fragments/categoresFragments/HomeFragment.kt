package com.vijaydhoni.shoppingapp.presentation.fragments.categoresFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.data.model.Category
import com.vijaydhoni.shoppingapp.data.util.HorizontalItemdecoration
import com.vijaydhoni.shoppingapp.data.util.VerticalItemdecoration
import com.vijaydhoni.shoppingapp.data.util.showBottomNavView
import com.vijaydhoni.shoppingapp.databinding.FragmentHomeBinding
import com.vijaydhoni.shoppingapp.presentation.adapters.BestDealsAdapter
import com.vijaydhoni.shoppingapp.presentation.adapters.BestProductsAdapter
import com.vijaydhoni.shoppingapp.presentation.adapters.SpecialProductAdapter
import com.vijaydhoni.shoppingapp.presentation.viewmodels.MainCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val bestDealsAdapter: BestDealsAdapter by lazy {
        BestDealsAdapter()
    }
    private val bestProductsAdapter: BestProductsAdapter by lazy {
        BestProductsAdapter()
    }
    private val mainCategoryViewModel: MainCategoryViewModel by viewModels()
    private val specialProductAdapter: SpecialProductAdapter by lazy {
        SpecialProductAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        showBottomNavView()
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBestProduct()
        setBestDealsRv()
        setSpecialProductRecylerView()
        showProgressBar()
        collectBestProducts()
        collectBestDeals()
        collectSpecilProducts()
        setUpvewAllProducts()
        specialProductAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product", it) }
            findNavController().navigate(
                R.id.action_shoppingHomePageFragment_to_productDetailsFragment,
                b
            )
        }

        bestDealsAdapter.onclick = {
            val b = Bundle().apply { putParcelable("product", it) }
            findNavController().navigate(
                R.id.action_shoppingHomePageFragment_to_productDetailsFragment,
                b
            )
        }

        bestProductsAdapter.onclick = {
            val b = Bundle().apply { putParcelable("product", it) }
            findNavController().navigate(
                R.id.action_shoppingHomePageFragment_to_productDetailsFragment,
                b
            )
        }


    }

    private fun setUpvewAllProducts() {
        binding.specialProductViewAll.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("category_name", Category("Special Deals"))
            findNavController().navigate(
                R.id.action_shoppingHomePageFragment_to_viewCategoryFragment,
                bundle
            )
        }

        binding.bestDealViewAll.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("category_name", Category("Mens Combo"))
            findNavController().navigate(
                R.id.action_shoppingHomePageFragment_to_viewCategoryFragment,
                bundle
            )
        }

    }

    private fun collectSpecilProducts() {
        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainCategoryViewModel.specialProductPaging.collectLatest { pagingData ->
                    binding.progressBar1.visibility = View.GONE
                    specialProductAdapter.submitData(pagingData)
                    return@collectLatest
                }
            }

        }
    }

    private fun collectBestDeals() {
        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainCategoryViewModel.bestDealsPaging.collectLatest { pagingData ->
                    binding.progressBar2.visibility = View.GONE
                    bestDealsAdapter.submitData(pagingData)
                    return@collectLatest
                }
            }
        }
    }

    private fun collectBestProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainCategoryViewModel.bestProductsPaging.collectLatest { pagingData ->
                    bestProductsAdapter.submitData(pagingData)
                    return@collectLatest
                }
            }
        }
    }

    private fun setBestProduct() {
        binding.bestProductsrecylerview.apply {
            adapter = bestProductsAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            addItemDecoration(VerticalItemdecoration())
            addItemDecoration(HorizontalItemdecoration(amount = 35))
        }
    }

    private fun setBestDealsRv() {
        binding.bestDealsrecylerview.apply {
            adapter = bestDealsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        }
    }


    private fun showProgressBar() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                bestDealsAdapter.loadStateFlow.collectLatest {
                    binding.progressBar2.isVisible = it.append is LoadState.Loading
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                specialProductAdapter.loadStateFlow.collectLatest {
                    binding.progressBar1.isVisible = it.append is LoadState.Loading
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                bestProductsAdapter.loadStateFlow.collectLatest {
                    binding.progressBar3.isVisible = it.append is LoadState.Loading
                }
            }
        }
    }

    private fun setSpecialProductRecylerView() {
        binding.specialpackProductrecylerview.apply {
            adapter = specialProductAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
}



