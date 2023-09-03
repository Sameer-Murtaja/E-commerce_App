package com.unit_one.e_commerceapp.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.unit_one.e_commerceapp.data.repository.ChatRepository
import java.text.SimpleDateFormat
import java.util.*

class ChatViewModel: ViewModel() {
    private val chatRepository = ChatRepository()

    val items = chatRepository.getItems().asLiveData()
    val messageText = MutableLiveData<String>()
    val attachments = mutableSetOf<String>()


    fun getProductCommentsChat(productId: String){
        chatRepository.getProductCommentsChat(productId)
    }

    fun checkAndSend(){
        if (!messageText.value.isNullOrEmpty() || attachments.isNotEmpty()) {
            sendMessage(messageText.value)
            messageText.postValue("")
        }
    }

    private fun sendMessage(message: String?) {
        val formattedDate = getFormattedDate()
        val hashMap = hashMapOf(
            "name" to "Admin",
            "message" to message,
            "date" to formattedDate,
            "attachments" to attachments.toList(),
        )
        chatRepository.sendMessage(hashMap)
    }

    private fun getFormattedDate(): String? {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val date = Date()
        return formatter.format(date)
    }
}