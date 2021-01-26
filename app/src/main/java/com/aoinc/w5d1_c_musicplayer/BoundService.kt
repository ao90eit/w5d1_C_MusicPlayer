package com.aoinc.w5d1_c_musicplayer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder

class BoundService : Service() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()

        mediaPlayer = MediaPlayer.create(this, R.raw.farts1)
    }

    // NOTE: onStartCommand not called by a bound service

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
    }

    fun playSong() {
        if (!mediaPlayer.isPlaying)
            mediaPlayer.start()
    }

    fun pauseSong() {
        if (mediaPlayer.isPlaying)
            mediaPlayer.pause()
    }

    fun stopSong() {
        mediaPlayer.stop()
        mediaPlayer.prepare()
    }

    override fun onBind(intent: Intent?): IBinder {
        return BoundServiceBinder()
    }

    inner class BoundServiceBinder: Binder() {
        fun getServiceInstance(): BoundService {
            return this@BoundService
        }
    }
}