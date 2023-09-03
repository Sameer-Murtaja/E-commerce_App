package com.unit_one.e_commerceapp.data.repository

import com.unit_one.e_commerceapp.data.remote.ChatFirebase

class ChatRepository {

    private val chatFirebaseRT = ChatFirebase()

    fun getItems() = chatFirebaseRT.getItems()

    fun sendMessage(hashMap: HashMap<String, Any?>) = chatFirebaseRT.sendMessage(hashMap)

    fun getProductCommentsChat(productId: String){
        chatFirebaseRT.getProductCommentsChat(productId)
    }


}