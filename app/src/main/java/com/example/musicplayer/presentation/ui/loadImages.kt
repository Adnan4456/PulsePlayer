package com.example.musicplayer.presentation.ui

//import coil.compose.rememberImagePainter
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R

@Composable
fun AlbumArtImage(context: Context, audioId: Long) {
    val albumArtUri = getAlbumArtUri(context, audioId)

    Box(
        modifier = Modifier
            .size(200.dp)
            .clip(shape = CircleShape)
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (albumArtUri != null) {
//            Image(
//                painter = rememberImagePainter(
//                    data = albumArtUri,
//                    builder = {
//                        crossfade(true)
//                        placeholder(R.drawable.ic_placeholder) // Placeholder image resource
//                    }
//                ),
//                contentDescription = null,
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Crop
//            )
        } else {
            // Placeholder for no album art
            Icon(
                painter = painterResource(id = R.drawable.ic_placeholder),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

private fun getAlbumArtUri(context: Context, audioId: Long): Uri? {
    val albumId = getAlbumId(context, audioId)
    return if (albumId != null) {
        ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, albumId)
    } else {
        Log.d("Error", "")
        null
    }
}

private fun getAlbumId(context: Context, audioId: Long): Long? {
    val projection = arrayOf(MediaStore.Audio.AudioColumns.ALBUM_ID)
    val selection = "${MediaStore.Audio.AudioColumns._ID} = ?"
    val selectionArgs = arrayOf(audioId.toString())

    context.contentResolver.query(
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        projection,
        selection,
        selectionArgs,
        null
    )?.use { cursor ->
        if (cursor.moveToFirst()) {

            val id =
                cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM_ID))
            Log.d("TAG:", "{$id}")
            return id
        }
    }

    return null
}

@Preview(showBackground = true)
@Composable
fun AlbumArtImagePreview() {
    // Replace with your actual context and audioId
    AlbumArtImage(LocalContext.current, 123)
}