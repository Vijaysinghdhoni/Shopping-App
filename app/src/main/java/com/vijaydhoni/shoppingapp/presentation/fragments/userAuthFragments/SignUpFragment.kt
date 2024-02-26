package com.vijaydhoni.shoppingapp.presentation.fragments.userAuthFragments

import android.os.Bundle
import android.util.Patterns
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
import com.vijaydhoni.shoppingapp.data.model.User
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.data.util.setStatusBarColour
import com.vijaydhoni.shoppingapp.databinding.FragmentSignUpBinding
import com.vijaydhoni.shoppingapp.presentation.activities.UserAuthActivity
import com.vijaydhoni.shoppingapp.presentation.viewmodels.UserAuthenticationViewmodel

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val userAuthenticationViewmodel: UserAuthenticationViewmodel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarColour(activity as? AppCompatActivity, R.color.g_yellow)
        binding.alrdyAccLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        userAuthenticationViewmodel.signUp.observe(viewLifecycleOwner) { event ->

            event.getContentIfNotHandled()?.let { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.signupUser.startAnimation()
                    }
                    is Resource.Success -> {
                        binding.signupUser.revertAnimation()
                        Toast.makeText(
                            requireContext(),
                            "Account Created Sucessfuly! Please Login",
                            Toast.LENGTH_LONG
                        )
                            .show()

                        findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                    }
                    is Resource.Error -> {
                        binding.signupUser.revertAnimation()
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

        binding.signupUser.setOnClickListener {

            val firstname = binding.userFirstNameEt.text.toString().trim()
            val lastname = binding.userLastNameEt.text.toString().trim()
            val email = binding.userEmailEt.text.toString().trim()
            val passwrd = binding.userPaswrdEt.text.toString()
            val cnfrmpasswrd = binding.userCnfrmpaswrdEt.text.toString()

            if (validateAllFields(firstname, lastname, email, passwrd, cnfrmpasswrd)) {
                userAuthenticationViewmodel.signUp(
                    User(firstname, lastname, email),
                    passwrd
                )
            }
        }

        binding.alrdyAccLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        binding.backBttn.setOnClickListener {
            findNavController().navigateUp()
        }


    }


    private fun validateAllFields(
        firstname: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        return (validateEmail(email) && validatePassword(password) && validateName(
            firstname,
            lastName
        ) && paswrdEqualCnfrmpswrd(password, confirmPassword))

    }


    private fun validateEmail(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.edittexemailtLayout1.apply {
                requestFocus()
                error = "Email Cannot be empty"
            }
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edittexemailtLayout1.apply {
                requestFocus()
                error = "Email format is invalid"
            }
            false
        } else {
            binding.edittexemailtLayout1.apply {
                error = null
            }
            true
        }
    }

    private fun validatePassword(password: String): Boolean {
        return if (password.isEmpty()) {
            binding.edittestpasswordlayout2.apply {
                requestFocus()
                error = "Password Cannot be empty"
            }
            false
        } else if (password.length < 6) {
            binding.edittestpasswordlayout2.apply {
                requestFocus()
                error = "Password should contain 6 characters"
            }
            false
        } else {
            binding.edittestpasswordlayout2.apply {
                error = null
            }
            true
        }
    }

    private fun validateName(firstname: String, lastName: String): Boolean {
        return if (firstname.isEmpty()) {
            binding.firstnameLayout.apply {
                requestFocus()
                error = "Field cannot be empty"
            }
            false
        } else if (lastName.isEmpty()) {
            binding.lastnameLayout.apply {
                requestFocus()
                error = "Field cannot be empty"
            }
            false
        } else {
            binding.firstnameLayout.apply {
                error = null
            }
            binding.lastnameLayout.apply {
                error = null
            }
            true
        }
    }

    private fun paswrdEqualCnfrmpswrd(password: String, confirmPassword: String): Boolean {
        return if (confirmPassword != password) {
            binding.edittestcnfrmpasswrdlayout3.apply {
                requestFocus()
                error = "Do not match with password"
            }
            false
        } else {
            binding.edittestcnfrmpasswrdlayout3.apply {
                error = null
            }
            true
        }
    }

}

