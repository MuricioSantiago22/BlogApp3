package com.example.blogapp3.domain.home.camera

import android.graphics.Bitmap

interface CameraRepo {
    suspend fun uploadPhoto(imageBitmap: Bitmap, description: String) {

    }
}