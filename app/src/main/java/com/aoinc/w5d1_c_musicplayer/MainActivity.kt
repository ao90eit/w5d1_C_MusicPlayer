package com.aoinc.w5d1_c_musicplayer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var statusTextView: TextView
    private lateinit var playPauseButton: ImageButton
    private lateinit var stopButton: ImageButton

    private lateinit var normalServiceIntent: Intent

    private lateinit var boundServiceIntent: Intent
//    private lateinit var boundService: BoundService
//    optionally:
    private var boundService: BoundService? = null

    val serviceConnection: ServiceConnection = object: ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // called when service successfully bound
            Log.d("TAG_X", "on service connected in the service connection object")
            boundService = (service as BoundService.BoundServiceBinder).getServiceInstance()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            // called when service unexpectedly unbound (on an error)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        normalServiceIntent = Intent(this, NormalService::class.java)
//        startService(normalServiceIntent)

        Log.d("TAG_X", "binding the service")
        boundServiceIntent = Intent(this, BoundService::class.java)
        bindService(boundServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE)

        statusTextView = findViewById(R.id.status_textView)
        playPauseButton = findViewById(R.id.play_pause_button)
        stopButton = findViewById(R.id.stop_button)

        playPauseButton.setOnClickListener {
            val button = it as ImageButton

            if (button.tag == "play") {
                button.setImageResource(R.drawable.ic_baseline_pause_24)
                boundService?.playSong()
                button.tag = "pause"
                statusTextView.text = "playing"
            } else {
                button.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                boundService?.pauseSong()
                button.tag = "play"
                statusTextView.text = "paused"
            }
        }

        stopButton.setOnClickListener(View.OnClickListener {
            boundService?.stopSong()
            playPauseButton.tag = "play"
            playPauseButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            statusTextView.text = "stopped"
        })

    }

    override fun onStop() {
        super.onStop()

        // normal services don't self-destruct
        stopService(normalServiceIntent)

//        if (this::boundService.isInitialized)
//            unbindService(serviceConnection)

//        optionally:
        boundService?.let {
            unbindService(serviceConnection)
            boundService = null
        }
    }
}