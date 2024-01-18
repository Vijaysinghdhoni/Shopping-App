package com.vijaydhoni.shoppingapp.presentation.viewmodels

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vijaydhoni.shoppingapp.data.model.User
import com.vijaydhoni.shoppingapp.data.util.Event
import com.vijaydhoni.shoppingapp.data.util.Resource
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetUserUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.SaveUserInfoUSeCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.SaveUserProfileImgUseCase
import com.vijaydhoni.shoppingapp.presentation.ShoppingApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class UserAccountViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserInfoUSeCase: SaveUserInfoUSeCase,
    private val saveUserProfileImgUseCase: SaveUserProfileImgUseCase,
    val app: Application
) : AndroidViewModel(app) {

    private val _user: MutableLiveData<Event<Resource<User?>>> = MutableLiveData()
    val user: LiveData<Event<Resource<User?>>> = _user


    fun getUser() {
        viewModelScope.launch {
            _user.postValue(Event(Resource.Loading()))
            val user = getUserUseCase.execute()
            _user.postValue(Event(user))
        }
    }

    private val _updateUser: MutableLiveData<Event<Resource<User>>> = MutableLiveData()
    val updateUser: LiveData<Event<Resource<User>>> = _updateUser

    fun updateUserInfo(user: User, imageUri: Uri?) {

        if (inputValid(user)) {
            viewModelScope.launch {
                _updateUser.postValue(Event(Resource.Loading()))

                if (imageUri == null) {
                    saveUserInfo(user, true)
                } else {
                    saveUserInfoWithNewInfo(user, imageUri)
                }

            }

        } else {
            _updateUser.postValue(Event(Resource.Error("Check Your Inputs")))
        }

    }

    private fun saveUserInfoWithNewInfo(user: User, imageUri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val imageBitmap = MediaStore.Images.Media.getBitmap(
                getApplication<ShoppingApplication>().contentResolver,
                imageUri
            )
            val byteArrayOutputStream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 96, byteArrayOutputStream)
            val imageByteArray = byteArrayOutputStream.toByteArray()

            when (val imgUrl = saveUserProfileImgUseCase.execute(imageByteArray)) {

                is Resource.Success -> {
                    saveUserInfo(user.copy(imagePath = imgUrl.data!!), false)
                }

                is Resource.Error -> {
                    _updateUser.postValue(Event(Resource.Error(imgUrl.message!!)))
                }
                else -> {
                }
            }


        }
    }

    private suspend fun saveUserInfo(user: User, shouldRetrivOldImage: Boolean) {
        val savedUser = saveUserInfoUSeCase.execute(user, shouldRetrivOldImage)
        _updateUser.postValue(Event(savedUser))
    }

    private fun inputValid(user: User): Boolean {
        if (user.email.trim().isEmpty()) {
            return false
        }
        if (user.firstName.trim().isEmpty()) {
            return false
        }
        if (user.lastName.trim().isEmpty()) {
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
            return false
        }

        return true
    }

}