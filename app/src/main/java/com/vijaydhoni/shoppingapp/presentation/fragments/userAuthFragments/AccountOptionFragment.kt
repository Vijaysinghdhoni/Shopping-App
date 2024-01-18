package com.vijaydhoni.shoppingapp.presentation.fragments.userAuthFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vijaydhoni.shoppingapp.R
import com.vijaydhoni.shoppingapp.data.util.setStatusBarColour
import com.vijaydhoni.shoppingapp.databinding.FragmentAccountOptionBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AccountOptionFragment : Fragment() {
    private lateinit var binding: FragmentAccountOptionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAccountOptionBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarColour(activity as? AppCompatActivity, R.color.g_backgroundforLogin)
        binding.loginInBttn.setOnClickListener {

            binding.loginInBttn.startAnimation()

            MainScope().launch {
                delay(800)
                binding.loginInBttn.revertAnimation()
                delay(500)
                findNavController().navigate(R.id.action_accountOptionFragment_to_loginFragment)
            }

        }


        binding.registerNow.setOnClickListener {

            binding.registerNow.startAnimation()

            MainScope().launch {
                delay(800)
                binding.registerNow.revertAnimation()
                delay(500)
                findNavController().navigate(R.id.action_accountOptionFragment_to_signUpFragment)
            }

        }

    }

}