package com.unit_one.e_commerceapp.data.repository

import com.unit_one.e_commerceapp.data.model.User
import com.unit_one.e_commerceapp.data.remote.UserFirebase

class UserRepository {
    private val userFirebase = UserFirebase()

    fun addUser(user: User) = userFirebase.addUser(user)

    fun logInUser(email: String, password: String) = userFirebase.logInUser(email,password)

    fun getUserFromFirebase() = userFirebase.getUserFromFirebase()

    fun addUserToFirebase(user: User) = userFirebase.addUserToFirebase(user)
}