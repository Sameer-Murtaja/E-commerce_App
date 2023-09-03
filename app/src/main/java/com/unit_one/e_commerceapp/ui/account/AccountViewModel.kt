package com.unit_one.e_commerceapp.ui.account

import android.graphics.Bitmap
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.unit_one.e_commerceapp.data.model.State
import com.unit_one.e_commerceapp.data.model.User
import com.unit_one.e_commerceapp.ui.base.BaseViewModel
import com.unit_one.e_commerceapp.util.Constants
import java.io.ByteArrayOutputStream

class AccountViewModel : BaseViewModel() {

    val user = MutableLiveData<State<User?>>(State.Loading)

    val isCropping = MutableLiveData<Boolean>()

    private val _imageAccountClicked = MutableLiveData<Boolean>()
    val imageAccountClicked : LiveData<Boolean> get() = _imageAccountClicked

    private val _buttonSaveClicked = MutableLiveData<Boolean>()
    val buttonSaveClicked : LiveData<Boolean> get() = _buttonSaveClicked

    private val _updatingUserState = MutableLiveData<State<Boolean>>(State.Success(true))
    val updatingUserState : LiveData<State<Boolean>> get() = _updatingUserState


    private val _buttonSignOutClicked = MutableLiveData<Boolean>()
    val buttonSignOutClicked : LiveData<Boolean> get() = _buttonSignOutClicked

    init {
        user.postValue(State.Success(User.instance))
    }

    fun onClickImageAccount() {
        _imageAccountClicked.postValue(true)
        _buttonSaveClicked.postValue(false)
    }

    fun setIsCropping() {
        isCropping.postValue(true)
    }

    fun onClickButtonSave() {
        _imageAccountClicked.postValue(false)
        isCropping.postValue(false)
        _buttonSaveClicked.postValue(true)

    }

    fun onClickButtonCancel() {
        _imageAccountClicked.postValue(false)
        isCropping.postValue(false)
    }


    fun onClickButtonSignOut() {
        Firebase.auth.signOut()
        _buttonSignOutClicked.postValue(true)
    }



    fun uploadImage(image: Bitmap?) {
        _updatingUserState.postValue(State.Loading)

        val baos = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()

        val imageRef = Firebase.storage.reference.child(User.instance?.username.toString())
        val childRef = imageRef.child("profileImage.png")
        var uploadTask = childRef.putBytes(data)
        uploadTask.addOnFailureListener { exception ->
            log(exception.message)
            _updatingUserState.postValue(State.Error(exception.message.toString()))
        }.addOnSuccessListener {
            childRef.downloadUrl.addOnSuccessListener { uri ->
                User.instance?.image = uri.toString()

                User.instance?.let { updateUser(it) }

            }
        }
    }


    private fun updateUser(user: User) {
        Firebase.firestore.collection(Constants.COLLECTION_USERS).document(user.id.toString())
            .update(user.asMap())
            .addOnSuccessListener {
                _updatingUserState.postValue(State.Success(true))
            }
            .addOnFailureListener { exception ->
                _updatingUserState.postValue(State.Error(exception.message.toString()))
                log(exception.message.toString())
            }
    }

}
