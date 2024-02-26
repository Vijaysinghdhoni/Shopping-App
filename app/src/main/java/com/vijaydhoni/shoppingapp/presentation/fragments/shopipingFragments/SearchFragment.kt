package com.vijaydhoni.shoppingapp.presentation.fragments.shopipingFragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.data.util.*
import com.vijaydhoni.shoppingapp.databinding.FragmentSearchBinding
import com.vijaydhoni.shoppingapp.presentation.adapters.SearchCategoryRvAdapter
import com.vijaydhoni.shoppingapp.presentation.adapters.SearchRvAdapter
import com.vijaydhoni.shoppingapp.presentation.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val categorySearchAdapter by lazy {
        SearchCategoryRvAdapter()
    }

    private val searchRvAdapter by lazy {
        SearchRvAdapter()
    }

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  showKeyboardAutomatically()
        setUpSearchRv()
        setSearchFunctionality()
        setCategoryRv()
        setCategoryItemClick()
        onHomeClick()

        searchRvAdapter.onClick = {
            val bundle = Bundle()
            binding.searchView.clearFocus()
            bundle.putParcelable("product", it)
            findNavController().navigate(
                R.id.action_favouriteFragment_to_productDetailsFragment,
                bundle
            )
        }
    }


    private fun onHomeClick() {
        val btm = activity?.findViewById<BottomNavigationView>(R.id.bnv_shopping)
        btm?.menu?.getItem(0)?.setOnMenuItemClickListener {
            binding.searchView.clearFocus()
            activity?.onBackPressed()
            true
        }
    }

    private fun setCategoryItemClick() {
        categorySearchAdapter.onClick = { category ->
            binding.searchView.clearFocus()
            Log.d("category", "selected cate is $category")
            val bundle = Bundle()
            bundle.putParcelable("category_name", category)
            findNavController().navigate(
                R.id.action_favouriteFragment_to_viewCategoryFragment,
                bundle
            )
        }
    }

    private fun setCategoryRv() {
        val categoryList = Categories.categorys
        categorySearchAdapter.differ.submitList(categoryList)
        binding.categoryRv.apply {
            adapter = categorySearchAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            addItemDecoration(VerticalItemdecoration())
        }
    }

    private fun setSearchFunctionality() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    binding.searchView.clearFocus()
                    if (it.isNotEmpty()) {
                        Log.d("search", "onQuerysubmit")
                        searchViewModel.getSearchedProducts(it)
                        viewSearchedProducts()
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                MainScope().launch {
                    hideAnimation()
                    if (newText.isNullOrEmpty()) {
                        hideAnimation()
                    }
                    delay(3000)
                    newText?.let {
                        if (it.isNotEmpty()) {
                            Log.d("search", "onquerychange")
                            searchViewModel.getSearchedProducts(it)
                            viewSearchedProducts()
                        }
                    }
                }
                return false
            }

        })


        binding.searchView.setOnCloseListener {
            hideAnimation()
            false
        }

    }

    private fun hideAnimation() {
        binding.searchViewRv.visibility = View.VISIBLE
        binding.categoryTxt.visibility = View.VISIBLE
        binding.categoryRv.visibility = View.VISIBLE

        binding.noResFoundAnim.visibility = View.GONE
    }

    private fun showAnimation() {
        binding.searchViewRv.visibility = View.GONE
        binding.categoryTxt.visibility = View.GONE
        binding.categoryRv.visibility = View.GONE
        binding.noResFoundAnim.visibility = View.VISIBLE
    }

    private fun viewSearchedProducts() {

        searchViewModel.searchProduct.observe(viewLifecycleOwner) { event ->

            event.getContentIfNotHandled()?.let { response ->


                when (response) {

                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        if (response.data.isNullOrEmpty()) {
                            binding.searchView.clearFocus()
//                            Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT)
//                                .show()
                            showAnimation()
                        } else {
                            Log.d("search", "data is showing")
                            binding.searchView.clearFocus()
                            hideAnimation()
                            searchRvAdapter.differ.submitList(response.data)
                        }
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT)
                            .show()
                    }


                }


            }
        }

    }

    private fun showKeyboardAutomatically() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

        binding.searchView.requestFocus()
    }

    private fun setUpSearchRv() {
        binding.searchViewRv.apply {
            adapter = searchRvAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(VerticalItemdecoration(amount = 40))
            addItemDecoration(HorizontalItemdecoration())
        }
    }

    override fun onResume() {
        super.onResume()
        showKeyboardAutomatically()
        showBottomNavView()
    }


    override fun onDestroy() {
        super.onDestroy()
        binding.searchView.clearFocus()
    }

}