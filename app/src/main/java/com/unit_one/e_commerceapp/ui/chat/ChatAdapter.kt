package com.unit_one.e_commerceapp.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.data.model.Message
import com.unit_one.e_commerceapp.data.model.User
import com.unit_one.e_commerceapp.databinding.ItemChatLeftBinding
import com.unit_one.e_commerceapp.databinding.ItemChatRightBinding
import com.unit_one.e_commerceapp.ui.base.BaseAdapter

class ChatAdapter(items: List<Message>?) :
    BaseAdapter<Message>(R.layout.item_chat_left, items, null) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        return if (viewType == RIGHT) {
            ItemViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_chat_right,
                    parent,
                    false
                )
            )
        } else {
            ItemViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_chat_left,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        items?.get(position)?.attachments?.let { uris ->
            when (holder.binding) {
                is ItemChatLeftBinding -> holder.binding.rvAttachments.adapter =
                    AttachmentAdapter(uris)
                is ItemChatRightBinding -> holder.binding.rvAttachments.adapter =
                    AttachmentAdapter(uris)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (items?.get(position)?.name == User.instance?.username.toString()) {
            RIGHT
        } else {
            LEFT
        }
    }

    companion object {
        const val RIGHT = 0
        const val LEFT = 1
    }
}