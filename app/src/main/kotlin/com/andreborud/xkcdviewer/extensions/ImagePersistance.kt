package com.andreborud.xkcdviewer.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import java.io.FileOutputStream
import javax.inject.Inject

class ImagePersistence @Inject constructor(private val context: Context) {

    fun saveComicImage(url: String, comicNumber: Int) {
        Picasso.get()
            .load(url)
            .into(object : com.squareup.picasso.Target {
                override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                    // Save bitmap to file for offline use
                    saveImageToFile(bitmap, comicNumber)
                }

                override fun onBitmapFailed(e: Exception, errorDrawable: Drawable?) {
                    // Handle failure
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
            })
    }

    private fun saveImageToFile(bitmap: Bitmap, comicNumber: Int) {
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = context.openFileOutput("image_$comicNumber", Context.MODE_PRIVATE)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loadComicImage(comicNumber: Int): Bitmap? {
        return try {
            val fileInputStream = context.openFileInput("image_$comicNumber")
            BitmapFactory.decodeStream(fileInputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}