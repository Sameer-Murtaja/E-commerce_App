package com.unit_one.e_commerceapp.data.model

import android.net.Uri

data class Message(val name: String, val message:String, val date: String, val attachments: List<Uri>? = null)
