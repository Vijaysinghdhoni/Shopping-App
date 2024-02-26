package com.vijaydhoni.shoppingapp.presentation.fragments.setting

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.data.model.User
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.data.util.hideBottomNavView
import com.vijaydhoni.shoppingapp.databinding.FragmentUserAccountBinding
import com.vijaydhoni.shoppingapp.presentation.viewmodels.UserAccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserAccountFragment : Fragment() {

    private lateinit var binding: FragmentUserAccountBinding
    private  val viewModel: UserAccountViewModel by viewModels()
    private var imageUri: Uri? = null
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideBottomNavView()
        viewModel.getUser()
        pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->

                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")
                    imageUri = uri
                    Glide.with(binding.userImg)
                        .load(uri)
                        .into(binding.userImg)
                } else {
                    Toast.makeText(requireContext(), "No Image Selected", Toast.LENGTH_SHORT).show()
                }
            }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUserInfo()
        observeUserInfoUpdate()
        binding.updateImg.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.cancelImg.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.saveBttn.setOnClickListener {
            val firstName = binding.firstNameEdit.text.trim().toString()
            val lastName = binding.lastNameEdit.text.trim().toString()
            val email = binding.emailEdit.text.trim().toString()
            val user = User(firstName, lastName, email)
            viewModel.updateUserInfo(user, imageUri)
        }
    }

    private fun observeUserInfoUpdate() {
        viewModel.updateUser.observe(viewLifecycleOwner) { event ->

            event.getContentIfNotHandled()?.let {
                when (it) {

                    is Resource.Loading -> {
                        binding.saveBttn.startAnimation()
                    }

                    is Resource.Success -> {
                        binding.saveBttn.revertAnimation()
                        Toast.makeText(
                            requireContext(),
                            "Information updated Succesfuly",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    is Resource.Error -> {
                        binding.saveBttn.revertAnimation()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                }
            }


        }
    }


    @SuppressLint("SetTextI18n")
    private fun observeUserInfo() {
        viewModel.user.observe(viewLifecycleOwner) { event ->

            event.getContentIfNotHandled()?.let {
                when (it) {

                    is Resource.Success -> {
                        binding.userName.text = "${it.data?.firstName} ${it.data?.lastName}"
                        binding.emailEdit.setText(it.data?.email)
                        binding.firstNameEdit.setText(it.data?.firstName)
                        binding.lastNameEdit.setText(it.data?.lastName)

                        Glide.with(binding.userImg)
                            .load(it.data?.imagePath)
                            .placeholder(R.drawable.shopping_placeholder)
                            .error(ColorDrawable(Color.BLACK))
                            .into(binding.userImg)

                    }

                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }


        }
    }


}