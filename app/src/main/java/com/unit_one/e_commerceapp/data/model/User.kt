package com.unit_one.e_commerceapp.data.model



data class User(
    var id: String?,
    val username: String?,
    val email: String?,
    val address: String?,
    val phone: String?,
    var image: String?,
    val password: String?,
){
    companion object{
        var instance: User? = null
    }

    fun asMap(): Map<String, Any>{
        return mapOf(
            "username" to username.toString(),
            "email" to email.toString(),
            "address" to address.toString(),
            "phone" to phone.toString(),
            "image" to image.toString(),
            "password" to password.toString(),
        )
    }
}