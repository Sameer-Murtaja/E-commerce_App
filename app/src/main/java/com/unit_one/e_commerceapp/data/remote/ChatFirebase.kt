package com.unit_one.e_commerceapp.data.remote

import android.net.Uri
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.unit_one.e_commerceapp.data.model.Message
import com.unit_one.e_commerceapp.data.model.State
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ChatFirebase {

    private val db = Firebase.database
    private var ref = db.getReference("chat")
    private var count = 0
    private val items = MutableStateFlow<State<List<Message>?>>(State.Loading)
    private val attachments = mutableSetOf<Uri>()


    fun getProductCommentsChat(productId: String) {
        ref = db.getReference("chat").child(productId)
        setOnDataChangeListener()
    }

    private val messagesListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            updateData(dataSnapshot)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.e("ChatRepository", "loadPost:onCancelled ${databaseError.toException()}")
        }
    }

    private fun updateData(dataSnapshot: DataSnapshot) {
        try {
            val itemsHashMap = dataSnapshot.getValue<List<HashMap<String, Any>>?>()
            val itemsMessage = itemsHashMap?.map {
                Message(
                    it["name"].toString(),
                    it["message"].toString(),
                    it["date"].toString(),
                    (it["attachments"] as List<String>?)?.map { string -> Uri.parse(string) }
                )
            }
            GlobalScope.launch {
                items.emit(State.Success(itemsMessage))
            }
            count = dataSnapshot.childrenCount.toInt()
        } catch (e: Exception) {
            Log.e("TAG", "updateData: ${e.message}", )
        }
    }


    init {
        setOnDataChangeListener()
    }

    private fun setOnDataChangeListener() {
        ref.addValueEventListener(messagesListener).runCatching {}
    }


    fun getItems(): Flow<State<List<Message>?>> {
        return items
    }

    fun sendMessageAndAttachments(hashMap: HashMap<String, Any?>) {
        val attachments = hashMap["attachments"] as List<String>?
        val attachmentsUri = attachments?.map { Uri.parse(it) }
        uploadAttachments(attachmentsUri, hashMap)
    }


    private fun uploadAttachments(attachmentsUris: List<Uri>?, hashMap: HashMap<String, Any?>) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val fileRef = storageRef.child("chat").child("images")

        if (attachmentsUris.isNullOrEmpty()) {
            sendMessage(hashMap)
            return
        }

        for (attachmentUri in attachmentsUris) {
            val childRef = fileRef.child(
                System.currentTimeMillis().toString() + ".png"
            )
            val uploadTask = childRef.putFile(attachmentUri)
            uploadTask.addOnSuccessListener { _ ->
                childRef.downloadUrl.addOnSuccessListener {
                    attachments.add(it)

                    if (attachmentsUris.size == attachments.size) {
                        hashMap["attachments"] = attachments.toList().map { uri -> uri.toString() }
                        sendMessage(hashMap)
                    }
                }
            }.addOnFailureListener {
                Log.e("ChatRepository", "uploadAttachments failed: ${it.message}")
            }
        }
    }

    private fun sendMessage(hashMap: HashMap<String, Any?>) {
        Log.e("Chat", "sendMessage: $hashMap")
        ref.child(count.toString()).setValue(hashMap)
    }


}