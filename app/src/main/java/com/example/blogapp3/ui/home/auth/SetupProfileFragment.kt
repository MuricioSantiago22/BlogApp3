package com.example.blogapp3.ui.home.auth

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.blogapp3.R
import com.example.blogapp3.data.model.remote.home.auth.AuthDataSource
import com.example.blogapp3.databinding.FragmentSetupProfileBinding
import com.example.blogapp3.domain.home.auth.AuthRepoImpl
import com.example.blogapp3.presentation.auth.AuthViewModel
import com.example.blogapp3.presentation.auth.AuthViewModelFactory
import com.example.blogapp3.core.Result

class SetupProfileFragment : Fragment(R.layout.fragment_setup_profile) {

    private lateinit var binding: FragmentSetupProfileBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    private val REQUEST_IMAGE_CAPTURE = 1

    private var bitmap: Bitmap? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSetupProfileBinding.bind(view)
        binding.profileImage.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    requireContext(),
                    "No se encontro app para abrir la camara",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnCreateProfile.setOnClickListener {
            val userName = binding.etxtUsername.text.toString().trim()
            val alertDialog =
                AlertDialog.Builder(requireContext()).setTitle("Uploading photo ...").create()
            bitmap?.let {
                if (userName.isNotEmpty()) {
                    viewModel.updateUserProfile(imageBitmap = it, userName = userName)
                        .observe(viewLifecycleOwner, { result ->
                            when (result) {
                                is Result.Loading -> {
                                    alertDialog.show()
                                }
                                is Result.Success -> {
                                    alertDialog.dismiss()
                                    findNavController().navigate(R.id.action_setupProfileFragment_to_homeScreenFragment)
                                }
                                is Result.Failure -> {
                                    alertDialog.dismiss()
                                }
                            }
                        })
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.profileImage.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        }
    }
}
