package com.vijaydhoni.shoppingapp.presentation.fragments.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vijaydhoni.shoppingapp.data.model.Address
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.data.util.hideBottomNavView
import com.vijaydhoni.shoppingapp.databinding.FragmentAddressBinding
import com.vijaydhoni.shoppingapp.presentation.viewmodels.AddressViewModel
import com.vijaydhoni.shoppingapp.presentation.viewmodels.AddressViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddressFragment : Fragment() {
    private lateinit var binding: FragmentAddressBinding
    private lateinit var addressViewModel: AddressViewModel

    @Inject
    lateinit var addressViewModelFactory: AddressViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideBottomNavView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addressViewModel = ViewModelProvider(
            requireActivity(),
            addressViewModelFactory
        )[AddressViewModel::class.java]

        val args by navArgs<AddressFragmentArgs>()

        val address = args.selectedAddress

        if (address == null) {
            saveAddress()
            binding.deleteBttn.visibility = View.INVISIBLE
            observeAddres()
        } else {
            setInfoInUi(address)
            binding.deleteBttn.visibility = View.VISIBLE
            binding.saveBttn.text = "Update"
            updateAddress(address)
            deleteUserAddress(address)
            observeDeleteUserAddress()
            observeupdatedAddress()

        }

        binding.cancelImg.setOnClickListener {
            findNavController().navigateUp()
        }


    }

    private fun observeDeleteUserAddress() {
        addressViewModel.deleteAddres.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                when (it) {
                    is Resource.Loading -> {
                        binding.deleteBttn.startAnimation()
                    }

                    is Resource.Success -> {
                        binding.deleteBttn.revertAnimation()
                        Toast.makeText(
                            requireContext(),
                            "Address is Deleted Succesfuly",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigateUp()
                    }

                    is Resource.Error -> {
                        binding.deleteBttn.revertAnimation()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    private fun deleteUserAddress(address: Address) {
        binding.deleteBttn.setOnClickListener {
            addressViewModel.deleteUserAddress(address)
        }

    }


    private fun observeupdatedAddress() {
        addressViewModel.updateAddress.observe(viewLifecycleOwner) { event ->

            event.getContentIfNotHandled()?.let {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressIndicator.visibility = View.VISIBLE
                        binding.saveBttn.startAnimation()
                    }
                    is Resource.Success -> {
                        binding.progressIndicator.visibility = View.INVISIBLE
                        binding.saveBttn.revertAnimation()
                    }
                    is Resource.Error -> {
                        binding.progressIndicator.visibility = View.INVISIBLE
                        binding.saveBttn.revertAnimation()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }


        }
    }

    private fun updateAddress(oldAdress: Address) {

        binding.apply {
            saveBttn.setOnClickListener {
                val addressLocation = adrsLocation.text.toString()
                val fullName = fullName.text.toString()
                val fullAdrs = fullAdrs.text.toString()
                val phone = phoneNum.text.toString()
                val city = city.text.toString()
                val state = state.text.toString()
                val newAddress = Address(addressLocation, fullName, fullAdrs, phone, city, state)
                addressViewModel.updateUserAddress(newAddress, oldAdress)

            }
        }

    }

    private fun setInfoInUi(address: Address) {
        binding.adrsLocation.setText(address.addressLocation)
        binding.fullAdrs.setText(address.fullAddress)
        binding.city.setText(address.city)
        binding.state.setText(address.state)
        binding.fullName.setText(address.fullName)
        binding.phoneNum.setText(address.phone)
    }

    private fun observeAddres() {
        addressViewModel.addNewAddress.observe(viewLifecycleOwner) {

            when (it) {

                is Resource.Loading -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                    binding.saveBttn.startAnimation()
                }
                is Resource.Success -> {
                    binding.progressIndicator.visibility = View.INVISIBLE
                    binding.saveBttn.revertAnimation()
                }

                is Resource.Error -> {
                    binding.progressIndicator.visibility = View.INVISIBLE
                    binding.saveBttn.revertAnimation()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }

            }

        }
    }

    private fun saveAddress() {
        binding.apply {
            saveBttn.setOnClickListener {
                val addressLocation = adrsLocation.text.toString()
                val fullName = fullName.text.toString()
                val fullAdrs = fullAdrs.text.toString()
                val phone = phoneNum.text.toString()
                val city = city.text.toString()
                val state = state.text.toString()
                val addresss = Address(addressLocation, fullName, fullAdrs, phone, city, state)
                addressViewModel.addAddress(addresss)
            }
        }
    }


}