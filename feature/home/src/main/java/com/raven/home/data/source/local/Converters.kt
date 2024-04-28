package com.raven.home.data.source.local

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Base64.DEFAULT
import androidx.room.TypeConverter
import java.nio.ByteBuffer
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteBuffer = ByteBuffer.allocate(bitmap.height * bitmap.rowBytes)
        bitmap.copyPixelsToBuffer(byteBuffer)
        val byteArray = byteBuffer.array()
        return Base64.encodeToString(byteArray, DEFAULT)
    }

    @TypeConverter
    fun base64ToBitmap(base64String: String): Bitmap {
        val byteArray = Base64.decode(base64String, DEFAULT)
        //byteArray to Bitmap
        return BitmapFactory.decodeByteArray(
            byteArray,
            0, byteArray.size
        )
    }
}