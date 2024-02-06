package com.example.musicplayer.data.repository

import com.example.musicplayer.data.model.Audio
import com.example.musicplayer.data.model.ContentResolverHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AudioRepository @Inject
constructor (private val contentResolverHelper: ContentResolverHelper) {

    suspend fun getAudioData():List<Audio> = withContext(Dispatchers.IO){
        contentResolverHelper.getAudioListData()
    }
}