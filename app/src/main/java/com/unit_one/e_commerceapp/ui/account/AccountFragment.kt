package com.unit_one.e_commerceapp.ui.account

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.*
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.util.Pair
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.canhub.cropper.CropImageView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.data.model.User
import com.unit_one.e_commerceapp.databinding.FragmentAccountBinding
import com.unit_one.e_commerceapp.ui.SignActivity
import com.unit_one.e_commerceapp.ui.base.BaseFragment
import com.unit_one.e_commerceapp.ui.base.BaseViewModel
import com.unit_one.e_commerceapp.util.Constants
import java.io.ByteArrayOutputStream


class AccountFragment : BaseFragment<FragmentAccountBinding, AccountViewModel>(
    R.layout.fragment_account,
    AccountViewModel::class.java
) {
    private val REQUEST_IMAGE_CAPTURE = 100

    override fun setup() {
        binding.imageCropView.cropShape = CropImageView.CropShape.OVAL
    }


    override fun addCallbacks() {
        viewModel.imageAccountClicked.observe(this) {
            if (it)
                showDialog()
        }

        viewModel.buttonSaveClicked.observe(this) {
            if (it) {
                val croppedImageBitmap = binding.imageCropView.getCroppedImage()
                binding.imageAccount.setImageBitmap(croppedImageBitmap)
                viewModel.uploadImage(croppedImageBitmap)
            }
        }

        viewModel.buttonSignOutClicked.observe(this) {
            if (it) {
                startActivity(Intent(requireActivity(), SignActivity::class.java))
                requireActivity().finish()
            }
        }

    }


    private fun showDialog() {
        val title = "Take image"
        val message = "Please select the method to pick your image"
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                "open Gallery"
            ) { _, _ ->
                openGallery()
            }
            .setNegativeButton("open Camera") { _, _ ->
                openCamera()
            }
            .show()
    }


    private fun openGallery() {
        getContent.launch("image/*")
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                binding.imageCropView.setImageUriAsync(uri)
                viewModel.setIsCropping()
            }
        }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.imageCropView.setImageBitmap(imageBitmap)
            viewModel.setIsCropping()
        }
    }

}