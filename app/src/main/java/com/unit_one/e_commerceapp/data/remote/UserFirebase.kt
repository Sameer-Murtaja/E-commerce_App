package com.unit_one.e_commerceapp.data.remote

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.unit_one.e_commerceapp.data.model.State
import com.unit_one.e_commerceapp.data.model.User
import com.unit_one.e_commerceapp.util.Constants
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserFirebase {
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    private val signUpState = MutableStateFlow<State<Boolean>>(State.Loading)
    private val signInState = MutableStateFlow<State<Boolean>>(State.Loading)

    fun addUser(user: User):
            MutableStateFlow<State<Boolean>> {
        GlobalScope.launch {
            signUpState.emit(State.Loading)
        }
        authSignUp(user)
        return signUpState
    }

    private fun authSignUp(user: User) {
        auth.createUserWithEmailAndPassword(user.email.toString(), user.password.toString())
            .addOnSuccessListener {
                addUserToFirebase(user)

            }.addOnFailureListener { e ->
                GlobalScope.launch {
                    signUpState.emit(State.Error(e.message.toString()))
                }
            }
    }

    fun addUserToFirebase(user: User): MutableStateFlow<State<Boolean>> {

        val id = auth.currentUser!!.uid
        user.id = id
        User.instance = user

        db.collection(Constants.COLLECTION_USERS).document(id)
            .set(user)
            .addOnSuccessListener {
                Log.e("tag", "Added Successfully $id")
                GlobalScope.launch {
                    signUpState.emit(State.Success(true))
                }
            }
            .addOnFailureListener {
                Log.e("tag", it.message.toString())
                GlobalScope.launch {
                    signUpState.emit(State.Error(it.message.toString()))
                }
            }
        return signUpState
    }


    fun logInUser(email: String, password: String):
            MutableStateFlow<State<Boolean>> {
        GlobalScope.launch {
            signInState.emit(State.Loading)
        }
        authLogin(email, password)
        return signInState
    }

    private fun authLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                getUserFromFirebase()

            }.addOnFailureListener { e ->
                GlobalScope.launch {
                    signInState.emit(State.Error(e.message.toString()))
                }
            }
    }

    fun getUserFromFirebase() {
        val id = auth.currentUser?.uid
        db.collection(Constants.COLLECTION_USERS)
            .whereEqualTo("id", id)
            .limit(1)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val user = User(
                        document.id,
                        document.getString("username"),
                        document.getString("email"),
                        document.getString("address"),
                        document.getString("phone"),
                        document.getString("image"),
                        document.getString("password"),
                    )
                    User.instance = user
                    Log.e("tag", "found user Successfully $id")

                    GlobalScope.launch {
                        signInState.emit(State.Success(true))
                    }
                }
            }.addOnFailureListener { error ->
                Log.e("tag", error.message.toString())
                GlobalScope.launch {
                    signInState.emit(State.Error(error.message.toString()))
                }
            }
    }


}