package com.unit_one.e_commerceapp.ui.chat

import android.net.Uri
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.ui.base.BaseAdapter

class AttachmentAdapter(items: List<Uri>) : BaseAdapter<Uri>(R.layout.item_attachment_image, items, null)