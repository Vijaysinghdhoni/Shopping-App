package com.vijaydhoni.shoppingapp.presentation.fragments.userAuthFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.data.util.setStatusBarColour
import com.vijaydhoni.shoppingapp.databinding.FragmentIntroBinding
import com.vijaydhoni.shoppingapp.presentation.viewmodels.UserAuthenticationViewmodel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class IntroFragment : Fragment() {
    private lateinit var binding: FragmentIntroBinding
    private val userAuthenticationViewmodel: UserAuthenticationViewmodel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarColour(activity as? AppCompatActivity, R.color.g_backgroundforLogin)
        userAuthenticationViewmodel.getCurrentUser()

        userAuthenticationViewmodel.currentUser.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    if (resource.data == UserAuthenticationViewmodel.Option_Activity) {
                        findNavController().navigate(R.id.action_introFragment_to_accountOptionFragment)
                    }
                }
                is Resource.Error -> {
                    resource.message?.let {
                        Snackbar.make(requireContext(), binding.root, it, Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
                else -> Unit
            }
        }

        binding.getStartedBttn.setOnClickListener {
            userAuthenticationViewmodel.startGetStartButtonClicked()
            binding.getStartedBttn.startAnimation()
            MainScope().launch {
                delay(1000)
                binding.getStartedBttn.revertAnimation {
                    binding.getStartedBttn.text = "Welcome"
                }
                delay(700)
                findNavController().navigate(R.id.action_introFragment_to_accountOptionFragment)
            }

        }


    }
}