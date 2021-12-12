package com.example.youtubebonusapp

import android.app.AlertDialog
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MainActivity : AppCompatActivity() {

    lateinit var videosList: List<Video>
    lateinit var gridView: GridView

    lateinit var playerView: YouTubePlayerView
    lateinit var player: YouTubePlayer

    private var currentVideo = 0
    private var timeStamp = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkInternet()

        videosList = listOf(
            Video("Attack On Titan", "ZFU-fZfZ0DI"),
            Video("Jujutsu Kaisen", "dwv65QSdXSU"),
            Video("Black Clover", "olW99JAV_lA"),
            Video("Naruto", "ejWo1KHRYTI"),
            Video("Hunter X Hunter", "aO-ZaF4FJls"),
            Video("Death Note", "-ZhjiihNfw4"),
        )
        gridView = findViewById(R.id.gridView)
        playerView = findViewById(R.id.player)

        playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                player = youTubePlayer
                player.loadVideo("L8KgdWNSGtc", 0F)
                initRV()
            }
        })

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            playerView.enterFullScreen()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            playerView.exitFullScreen()
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("currentVideo", currentVideo)
        outState.putFloat("timeStamp", timeStamp)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        currentVideo = savedInstanceState.getInt("currentVideo", 0)
        timeStamp = savedInstanceState.getFloat("timeStamp", 0f)
    }


    private fun checkInternet() {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo

        if (activeNetwork?.isConnectedOrConnecting == false) {
            AlertDialog.Builder(this).setTitle("No Internet")
                .setMessage("Please Connect to a Network")
                .setPositiveButton("OK") { _, _ -> checkInternet() }
                .show()
        }
    }

    private fun initRV() {
        gridView.adapter = GridVideosAdapter(videosList, player, this)
    }


}