package com.vijaydhoni.shoppingapp.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.vijaydhoni.shoppingapp.data.model.User
import com.vijaydhoni.shoppingapp.data.util.Constants.INTRODUCTION_KEY
import com.vijaydhoni.shoppingapp.data.util.Constants.USER_COLLECTION
import com.vijaydhoni.shoppingapp.data.util.Event
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.domain.usecases.userAuthUsecases.GetCurrentUserUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.userAuthUsecases.LoginUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.userAuthUsecases.SendPasswordResetUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.userAuthUsecases.SignUpUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserAuthenticationViewmodel(
    private val loginUseCase: LoginUseCase,
    private val app: Application,
    private val signUpUseCase: SignUpUseCase,
    private val sendPasswordResetUseCase: SendPasswordResetUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val sharedPreferences: SharedPreferences,
    private val db: FirebaseFirestore
) : ViewModel() {


    //User Login
    private val _login: MutableLiveData<Event<Resource<FirebaseUser?>>> = MutableLiveData()
    val login: LiveData<Event<Resource<FirebaseUser?>>>
        get() = _login


    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _login.postValue(Event(Resource.Loading()))

            if (isInternetAvailable(app)) {
                val user = loginUseCase.execute(email, password)
                _login.postValue(Event(user))
            } else {
                _login.postValue(Event(Resource.Error("Internet is not available")))
            }

        }


    }

    //Register User

    private val _signUp: MutableLiveData<Event<Resource<FirebaseUser?>>> = MutableLiveData()
    val signUp: LiveData<Event<Resource<FirebaseUser?>>>
        get() = _signUp


    fun signUp(user: User, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _signUp.postValue(Event(Resource.Loading()))

            if (isInternetAvailable(app)) {
                val firebaseUser = signUpUseCase.execute(user, password)
                _signUp.postValue(Event(firebaseUser))
                saveUserInfo(firebaseUser.data?.uid, user)
            } else {
                _signUp.postValue(Event(Resource.Error("Internet is not available")))
            }

        }
    }

    private fun saveUserInfo(uid: String?, user: User) {

        db.collection(USER_COLLECTION)
            .document(uid!!)
            .set(user).addOnSuccessListener {
                Log.d("user", "user is saved ")
            }.addOnFailureListener {
                Log.d("user", it.message.toString())
            }

    }


    //Passwrd Reset

    private val _sendPasswrdReset: MutableLiveData<Event<Resource<String>>> = MutableLiveData()
    val sendPasswrdReset: LiveData<Event<Resource<String>>>
        get() = _sendPasswrdReset

    fun sendPsswrdReset(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _sendPasswrdReset.postValue(Event(Resource.Loading()))

            if (isInternetAvailable(app)) {
                val response = sendPasswordResetUseCase.execute(email)
                _sendPasswrdReset.postValue(Event(response))
            } else {
                _sendPasswrdReset.postValue(Event(Resource.Error("Internet is not available")))
            }

        }
    }


    companion object {
        const val Shopping_Activity = "shoppingactivity"
        const val Option_Activity = "accountOptionActivity"
    }

    //get current user

    private val _currentUser: MutableLiveData<Resource<String>> = MutableLiveData()
    val currentUser: LiveData<Resource<String>> get() = _currentUser

    private val _isUserLoading = MutableStateFlow(true)
    val isUserLoading = _isUserLoading.asStateFlow()


    fun getCurrentUser() {
        _currentUser.postValue(Resource.Loading())
        val isButtonClicked = sharedPreferences.getBoolean(INTRODUCTION_KEY, false)
        if (isInternetAvailable(app)) {
            val currentuser = getCurrentUserUseCase.execute()
            if (currentuser.data != null) {
                _isUserLoading.value = false
            } else if (isButtonClicked) {
                _currentUser.postValue(Resource.Success(Option_Activity))
            }
        } else {
            _currentUser.postValue(Resource.Error("Internet is not available"))
        }
    }


    init {
        getCurrentUser()
    }


    fun startGetStartButtonClicked() {
        sharedPreferences.edit().putBoolean(INTRODUCTION_KEY, true).apply()
    }


    @Suppress("DEPRECATION")
    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }


}