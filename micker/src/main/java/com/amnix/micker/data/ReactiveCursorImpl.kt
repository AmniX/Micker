package com.amnix.micker.data

import android.content.Context
import android.provider.MediaStore
import com.amnix.micker.domain.entity.Media
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val VOLUME_EXTERNAL = "external"
private const val MIME_TYPE_IMAGE = "image/"

internal class ReactiveCursorImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ReactiveCursor {

    override fun findImages(): Flow<Media.Image> = flow {
        val cursor = context.contentResolver.query(
            MediaStore.Files.getContentUri(VOLUME_EXTERNAL),
            arrayOf(
                MediaStore.Files.FileColumns.DATA
            ),
            MediaStore.Files.FileColumns.MIME_TYPE + " LIKE '$MIME_TYPE_IMAGE%'",
            null,
            MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
        )
        if (cursor != null) {
            val data = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
            cursor.moveToFirst()
            while (cursor.moveToNext()) {
                emit(Media.Image(cursor.getString(data)))
            }
        }
        cursor?.close()
    }
}