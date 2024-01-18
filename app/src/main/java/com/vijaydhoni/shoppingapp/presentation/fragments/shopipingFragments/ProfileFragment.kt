package com.vijaydhoni.shoppingapp.presentation.fragments.shopipingFragments

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.data.model.UserCartProductsList
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.data.util.showBottomNavView
import com.vijaydhoni.shoppingapp.databinding.FragmentProfileBinding
import com.vijaydhoni.shoppingapp.presentation.activities.UserAuthActivity
import com.vijaydhoni.shoppingapp.presentation.viewmodels.ProfileFragmentViewModel
import com.vijaydhoni.shoppingapp.presentation.viewmodels.ProfileFragmentViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileFragmentViewModel: ProfileFragmentViewModel

    @Inject
    lateinit var profileFragmentViewModelFactory: ProfileFragmentViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileFragmentViewModel = ViewModelProvider(
            requireActivity(),
            profileFragmentViewModelFactory
        )[ProfileFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileFragmentViewModel.getUser()
        onHomeClick()
        setUpActions()
        observeUser()
        observeLogout()
        observeTrackOrder()
    }

    private fun observeLogout() {
        profileFragmentViewModel.logout.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                when (it) {

                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        val intent = Intent(requireActivity(), UserAuthActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

    private fun observeUser() {
        profileFragmentViewModel.user.observe(viewLifecycleOwner) { event ->

            event.getContentIfNotHandled()?.let {
                when (it) {
                    is Resource.Success -> {
                        val user = it.data
                        binding.usrName.text = "${user?.firstName} ${user?.lastName}"
                        Glide.with(binding.usrProfileImg)
                            .load(user?.imagePath)
                            .placeholder(ColorDrawable(Color.BLACK))
                            .error(ColorDrawable(Color.BLACK))
                            .into(binding.usrProfileImg)
                    }

                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                    }
                }
            }
        }
    }

    private fun setUpActions() {
        binding.openAllOrdrArrow.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_allOrdersFragment)
        }

        binding.billingAdrsArrow.setOnClickListener {
            val emptyList = UserCartProductsList(emptyList())
            val action = ProfileFragmentDirections.actionProfileFragmentToBillingFragment(
                0f,
                false,
                emptyList
            )
            findNavController().navigate(action)
        }

        binding.openUsrProfileArrow.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_userAccountFragment)
        }

        binding.logoutArrow.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext()).apply {
                setTitle("Log out!")
                setMessage("Do you want to Logout?")
                setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                setPositiveButton("Yes") { dialog, _ ->
                    profileFragmentViewModel.logoutUser()
                    dialog.dismiss()
                }
            }
            alertDialog.create()
            alertDialog.show()

        }

        binding.openTrackOrdrArrow.setOnClickListener {
            profileFragmentViewModel.getRecentsOrder()
        }

        binding.languageArrow.setOnClickListener {
            choseLanguage()
        }

    }

    private fun observeTrackOrder() {
        profileFragmentViewModel.recentOrder.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { orders ->

                when (orders) {

                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val order = orders.data?.get(0)
                        val bundle = Bundle()
                        bundle.putParcelable("userOrder", order)
                        findNavController().navigate(
                            R.id.action_profileFragment_to_orderDetailFragment,
                            bundle
                        )
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), orders.message, Toast.LENGTH_SHORT).show()
                    }

                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                }


            }
        }
    }

    private fun choseLanguage() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder
            .setTitle("Chose Language")
            .setPositiveButton("Select") { dialog, _ ->
                Toast.makeText(requireContext(), "English is Selected", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setSingleChoiceItems(
                arrayOf("English"), 0
            ) { dialog, which ->
                when (which) {
                    0 -> {
                        dialog.dismiss()
                    }
                }
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
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
        profileFragmentViewModel.getUser()
        showBottomNavView()
    }

}