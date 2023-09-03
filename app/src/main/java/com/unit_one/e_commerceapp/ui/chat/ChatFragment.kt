package com.unit_one.e_commerceapp.ui.chat

import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.navArgs
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.databinding.FragmentChatBinding
import com.unit_one.e_commerceapp.ui.base.BaseFragment
import com.unit_one.e_commerceapp.ui.details.ProductDetailsFragmentArgs

class ChatFragment : BaseFragment<FragmentChatBinding, ChatViewModel>(
    R.layout.fragment_chat,
    ChatViewModel::class.java
) {
    private val args: ChatFragmentArgs by navArgs()


    override fun setup() {
        binding.rvChat.adapter = ChatAdapter(emptyList())
        viewModel.getProductCommentsChat(args.produtId)
    }

    override fun addCallbacks() {
        binding.buttonAttachments.setOnClickListener {
            getContents.launch("image/*")
        }
    }

    private val getContents =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri>? ->
            if (uris != null) {
                log( "Uris: $uris")
                viewModel.attachments.addAll(uris.map { it.toString() })
            }
        }

}