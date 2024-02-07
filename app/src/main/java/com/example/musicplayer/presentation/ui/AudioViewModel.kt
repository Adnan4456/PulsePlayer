package com.example.musicplayer.presentation.ui

import android.support.v4.media.MediaBrowserCompat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.model.Audio
import com.example.musicplayer.data.repository.AudioRepository
import com.example.musicplayer.data.service.MediaPlayerService
import com.example.musicplayer.data.service.exoplayer.MediaPlayerServiceConnection
import com.example.musicplayer.data.service.exoplayer.currentPosition
import com.example.musicplayer.data.service.exoplayer.isPlaying
import com.example.musicplayer.data.utils.constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val repository: AudioRepository,
    serviceConnection: MediaPlayerServiceConnection
):ViewModel() {

    var audioList = mutableStateListOf<Audio>()
    val currentPlayingAudio  = serviceConnection.currentPlayingAudio

    private val isConnected = serviceConnection._isConnected

    lateinit var rootMediaId : String

    var currentPlayBackPosition by mutableStateOf(0L)

    private var updatePosition = true

    private val playBackState = serviceConnection.playBackState

    val isAudioPlaying:Boolean
    get() = playBackState.value?.isPlaying == true

    private val subscriptionCallBack = object: MediaBrowserCompat.SubscriptionCallback(){
        override fun onChildrenLoaded(
            parentId: String,
            children: MutableList<MediaBrowserCompat.MediaItem>
        ) {
            super.onChildrenLoaded(parentId, children)
        }
    }

    private val serviceConnection = serviceConnection.also {

        updatePlayBack()
    }
    val currentDuration:Long
    get() = MediaPlayerService.currentDuration

    var currentAudioProgress = mutableStateOf(0f)


    init {
        viewModelScope.launch {
            audioList +=getAndFormatAudioData()

            isConnected.collect{
                if (it){
                    rootMediaId = serviceConnection.rootMediaId
                    serviceConnection.playBackState.value?.apply {
                        currentPlayBackPosition  = position
                    }
                    serviceConnection.subscribe(rootMediaId , subscriptionCallBack)
                }
            }
        }
    }

    private suspend fun getAndFormatAudioData():List<Audio>{
        return repository.getAudioData().map {
            val displayName = it.displayName.substringBefore(".")
            val artisit = if(it.artist.contains("<unknow>"))
                "Unknow artist" else it.artist
            it.copy(
                displayName = displayName,
                artist = artisit
            )
        }
    }

    fun playAudio(currentAudio:Audio){
        serviceConnection.playAudio(audios = audioList)

        if (currentAudio.id == currentPlayingAudio.value?.id){

            if (isAudioPlaying){
                serviceConnection.transportControll.pause()
            }else
            {
                serviceConnection.transportControll.play()
            }
        }
        else
        {
            serviceConnection.transportControll.playFromMediaId(
                currentAudio.id.toString(),
                null
            )
        }
    }

    private fun updatePlayBack(){
        viewModelScope.launch {

            val position = playBackState.value?.currentPosition ?: 0
            if (currentPlayBackPosition != position){
                currentPlayBackPosition = position
            }
            if (currentDuration > 0 ){
                currentAudioProgress.value = (
                        currentPlayBackPosition.toFloat()
                                /currentDuration.toFloat() * 100f
                        )
            }

            delay(constants.PLAYBACK_UPDATE_INTERVAL)

            if (updatePosition){

                updatePlayBack()

            }
        }
    }



    fun stopPlayBack(){
        serviceConnection.transportControll.stop()
    }

    fun fastForward(){
        serviceConnection.fastForward()
    }

    fun rewind(){
        serviceConnection.rewind()
    }

    fun skipToNext(){
        serviceConnection.skipToNext()
    }

    fun seekTo(value:Float){
        serviceConnection.transportControll.seekTo(
            (currentDuration * value / 100f).toLong()
        )
    }

    override fun onCleared() {
        super.onCleared()
        serviceConnection.unsubscribe(constants.MEDIA_ROOT_ID ,
            object :MediaBrowserCompat.SubscriptionCallback(){})
        updatePosition = false

    }
}