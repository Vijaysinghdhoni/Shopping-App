package com.vijaydhoni.shoppingapp.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.vijaydhoni.shoppingapp.databinding.ActivityUserAuthBinding
import com.vijaydhoni.shoppingapp.presentation.viewmodels.UserAuthenticationViewModelFactory
import com.vijaydhoni.shoppingapp.presentation.viewmodels.UserAuthenticationViewmodel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserAuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserAuthBinding

    @Inject
    lateinit var userAuthenticationViewModelFactory: UserAuthenticationViewModelFactory
    val userAuthenticationViewmodel: UserAuthenticationViewmodel by lazy {
        ViewModelProvider(
            this,
            userAuthenticationViewModelFactory
        )[UserAuthenticationViewmodel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        if (!userAuthenticationViewmodel.isUserLoading.value) {
            val intent = Intent(this, ShoppingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding = ActivityUserAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}