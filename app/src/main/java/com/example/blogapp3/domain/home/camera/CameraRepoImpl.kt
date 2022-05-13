package com.example.blogapp3.domain.home.camera

import android.graphics.Bitmap
import com.example.blogapp3.data.model.remote.home.camera.CameraDataSource

class CameraRepoImpl(private val dataSource: CameraDataSource): CameraRepo {
    override suspend fun uploadPhoto(imageBitmap: Bitmap, description: String) {
        dataSource.uploadPhoto(imageBitmap, description)
    }
}

