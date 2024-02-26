package com.vijaydhoni.shoppingapp.presentation.fragments.userAuthFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.presentation.activities.ShoppingActivity
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.data.util.setStatusBarColour
import com.vijaydhoni.shoppingapp.databinding.FragmentLoginBinding
import com.vijaydhoni.shoppingapp.presentation.activities.UserAuthActivity
import com.vijaydhoni.shoppingapp.presentation.bottomSheet.setupBottomSheetDialog
import com.vijaydhoni.shoppingapp.presentation.viewmodels.UserAuthenticationViewmodel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val userAuthenticationViewmodel: UserAuthenticationViewmodel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarColour(activity as? AppCompatActivity, R.color.g_yellow)
        userAuthenticationViewmodel.login.observe(viewLifecycleOwner) { event ->

            event.getContentIfNotHandled()?.let { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.loginUser.startAnimation()
                    }
                    is Resource.Success -> {
                        binding.loginUser.revertAnimation()
                        val intent = Intent(requireContext(), ShoppingActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        Toast.makeText(
                            requireContext(),
                            "you are sucessfuly login!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is Resource.Error -> {
                        binding.loginUser.revertAnimation()
                        resource.message?.let {
                            Snackbar.make(
                                requireContext(),
                                binding.root,
                                it,
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }


        }

        userAuthenticationViewmodel.sendPasswrdReset.observe(
            viewLifecycleOwner
        ) { event ->

            event.getContentIfNotHandled()?.let { response ->

                when (response) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        Snackbar.make(
                            requireContext(),
                            binding.root,
                            "Password reset link is send to your email",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    is Resource.Error -> {
                        response.message?.let {
                            Snackbar.make(
                                requireContext(),
                                binding.root,
                                it,
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }

        }

        binding.forgotPasswrd.setOnClickListener {
            setupBottomSheetDialog { email ->
                userAuthenticationViewmodel.sendPsswrdReset(email)
            }
        }

        binding.loginUser.setOnClickListener {
            val email = binding.userEmailEt.text.toString().trim()
            val password = binding.userPaswrdEt.text.toString()
            userAuthenticationViewmodel.login(email, password)
        }

        binding.backBttn.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.notaccsignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

    }


}