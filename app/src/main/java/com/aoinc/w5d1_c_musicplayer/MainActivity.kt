package com.aoinc.w5d1_c_musicplayer

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var statusTextView: TextView
    private lateinit var playPauseButton: ImageButton
    private lateinit var stopButton: ImageButton

    private lateinit var normalServiceIntent: Intent

    private lateinit var boundServiceIntent: Intent
    val serviceConnection: ServiceConnection = object: ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // called when service successfully bound
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            // called when service unexpectedly unbound
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.status_textView)
        playPauseButton = findViewById(R.id.play_pause_button)
        stopButton = findViewById(R.id.stop_button)

        playPauseButton.setOnClickListener {
            val button = it as ImageButton

            if (button.tag == "play") {
                button.setImageResource(R.drawable.ic_baseline_pause_24)
                button.tag = "pause"
            } else {
                button.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                button.tag = "play"
            }
        }

        stopButton.setOnClickListener(View.OnClickListener {
            // TODO: stop button event
        })

        normalServiceIntent = Intent(this, NormalService::class.java)
        startService(normalServiceIntent)

        boundServiceIntent = Intent(this, BoundService::class.java)
        
    }

    override fun onStop() {
        super.onStop()

        // normal services don't self-destruct
        stopService(normalServiceIntent)
    }
}