package com.unit_one.e_commerceapp.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.unit_one.e_commerceapp.data.model.ProductResponse
import com.unit_one.e_commerceapp.data.model.State
import com.unit_one.e_commerceapp.data.model.User
import com.unit_one.e_commerceapp.data.repository.ChatRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ChatViewModel : ViewModel() {
    private val chatRepository = ChatRepository()

    val items = chatRepository.getItems().asLiveData()
    val messageText = MutableLiveData<String>()
    val attachments = mutableSetOf<String>()

    val sendMessageState = MutableLiveData<State<Boolean>>()


    fun getProductCommentsChat(productId: String) {
        chatRepository.getProductCommentsChat(productId)
    }

    fun checkAndSend() {
        if (!messageText.value.isNullOrEmpty() || attachments.isNotEmpty()) {
            sendMessage(messageText.value)
            messageText.postValue("")
        }
    }

    private fun sendMessage(message: String?) {
        val formattedDate = getFormattedDate()
        val hashMap = hashMapOf(
            "name" to User.instance?.username.toString(),
            "message" to message,
            "date" to formattedDate,
            "attachments" to attachments.toList(),
        )
        viewModelScope.launch {
            chatRepository.sendMessage(hashMap).collect {
                sendMessageState.postValue(it)
            }
        }
    }

    private fun getFormattedDate(): String? {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val date = Date()
        return formatter.format(date)
    }
}