package com.vijaydhoni.shoppingapp.presentation.fragments.shopipingFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.databinding.FragmentShoppingHomePageBinding
import com.vijaydhoni.shoppingapp.presentation.adapters.HomeViewPagerAdapter
import com.vijaydhoni.shoppingapp.presentation.fragments.categoresFragments.*

class ShoppingHomePageFragment : Fragment() {
    private lateinit var binding: FragmentShoppingHomePageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.voiceImg.setOnClickListener {
            Snackbar.make(
                binding.root,
                resources.getText(R.string.g_coming_soon),
                Snackbar.LENGTH_SHORT
            ).show()
        }


        val categoryFragments = arrayListOf(
            HomeFragment(),
            SportsFragment(),
            MalesFragment(),
            FootwearFragment(),
            Accessoriesragment()
        )

        binding.viewPagerHome.isUserInputEnabled = false

        val viewPagerAdapter =
            HomeViewPagerAdapter(categoryFragments, childFragmentManager, lifecycle)

        binding.viewPagerHome.adapter = viewPagerAdapter



        TabLayoutMediator(binding.tabLayout, binding.viewPagerHome) { tab, position ->

            when (position) {
                0 -> tab.text = resources.getText(R.string.g_home)
                1 -> tab.text = resources.getText(R.string.g_sports)
                2 -> tab.text = resources.getText(R.string.g_males)
                3 -> tab.text = resources.getText(R.string.g_footwear)
                4 -> tab.text = resources.getText(R.string.g_acces)

            }

        }.attach()


        binding.searchNowTxt.setOnClickListener {
            findNavController().navigate(R.id.action_shoppingHomePageFragment_to_favouriteFragment)
        }

    }


}