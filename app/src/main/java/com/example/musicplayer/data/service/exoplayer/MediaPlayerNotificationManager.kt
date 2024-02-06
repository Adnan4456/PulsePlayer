package com.example.musicplayer.data.service.exoplayer

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import com.example.musicplayer.R
import com.example.musicplayer.data.utils.constants
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager


internal class MediaPlayerNotificationManager (
    context: Context,
    sessionToken: MediaSessionCompat.Token,
    notificationListener: PlayerNotificationManager.NotificationListener
){
    private val notificationManager:PlayerNotificationManager

    init {
        val mediaController = MediaControllerCompat(context , sessionToken)

        val builder = PlayerNotificationManager.Builder(
            context ,
            constants.PLAYBACK_NOTIFICATION_ID,
            constants.PLAYBACK_NOTIFICATION_CHANNEL_ID
        )
        with(builder){
            setMediaDescriptionAdapter(DescriptionAdapter(mediaController))
            setNotificationListener(notificationListener)
            setChannelDescriptionResourceId(R.string.notification_channel)
            setChannelDescriptionResourceId(R.string.notification_channel_description)
        }

        notificationManager = builder.build()

        with(notificationManager){
            setMediaSessionToken(sessionToken)
            setSmallIcon(R.drawable.music)
            setUseRewindAction(false)
            setUseFastForwardAction(false)
        }
    }

    fun showNotification(player:Player){
        notificationManager.setPlayer(player)
    }
    fun hideNotification(){
        notificationManager.setPlayer(null)
    }


    inner class DescriptionAdapter(private val controller: MediaControllerCompat):
            PlayerNotificationManager.MediaDescriptionAdapter{

        override fun getCurrentContentTitle(player: Player): CharSequence {
           return controller.metadata.description.title.toString()
        }

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            return controller.sessionActivity
        }

        override fun getCurrentContentText(player: Player): CharSequence? {
            return controller.metadata.description.subtitle
        }

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            return null
        }

    }
}