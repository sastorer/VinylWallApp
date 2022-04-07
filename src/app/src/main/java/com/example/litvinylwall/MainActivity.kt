package com.example.litvinylwall

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {
    @Suppress("DEPRECATION")
    lateinit var img1 : ImageView
    lateinit var img2 : ImageView
    lateinit var img3 : ImageView
    lateinit var img4 : ImageView
    lateinit var img5 : ImageView
    lateinit var img6 : ImageView
    lateinit var img7 : ImageView
    lateinit var img8 : ImageView

    val sounds = arrayOf("spotify:album:6k3vC8nep1BfqAIJ81L6OL", "spotify:album:4oktVvRuO1In9B7Hz0xm0a")
    val albumArt = arrayOf("https://i.imgur.com/V4QpirM.png",
        "https://i.imgur.com/V4QpirM.png",
        "https://i.imgur.com/V4QpirM.png",
        "https://i.imgur.com/V4QpirM.png",
        "https://i.imgur.com/V4QpirM.png",
        "https://i.imgur.com/V4QpirM.png",
        "https://i.imgur.com/V4QpirM.png",
        "https://i.imgur.com/V4QpirM.png"
    )
    val nfcAlbumArt = arrayOf("https://i.scdn.co/image/ab67616d00001e0269592e88bb29d610a35118f8")

    private val clientId = "4adfa19aee5a48e4a7634908838c3292"
    private val redirectUri = "lit-vinyl-wall://callback"
    private var spotifyAppRemote: SpotifyAppRemote? = null

    var albumCount = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        img1 = findViewById<ImageView>(R.id.image_grid1)
        Picasso.with(this).load(albumArt[0]).into(img1)
        img1.tag = "Blank"
        img2 = findViewById<ImageView>(R.id.image_grid2)
        Picasso.with(this).load(albumArt[1]).into(img2)
        img2.tag = "Blank"
        img3 = findViewById<ImageView>(R.id.image_grid3)
        Picasso.with(this).load(albumArt[2]).into(img3)
        img3.tag = "Blank"
        img4 = findViewById<ImageView>(R.id.image_grid4)
        Picasso.with(this).load(albumArt[3]).into(img4)
        img4.tag = "Blank"
        img5 = findViewById<ImageView>(R.id.image_grid5)
        Picasso.with(this).load(albumArt[4]).into(img5)
        img5.tag = "Blank"
        img6 = findViewById<ImageView>(R.id.image_grid6)
        Picasso.with(this).load(albumArt[5]).into(img6)
        img6.tag = "Blank"
        img7 = findViewById<ImageView>(R.id.image_grid7)
        Picasso.with(this).load(albumArt[6]).into(img7)
        img7.tag = "Blank"
        img8 = findViewById<ImageView>(R.id.image_grid8)
        Picasso.with(this).load(albumArt[7]).into(img8)
        img8.tag = "Blank"

        val images = arrayOf(img1, img2, img3, img4, img5, img6, img7, img8)

        val actionButton1 = findViewById<FloatingActionButton>(R.id.floatingActionButton_grid1)
        val actionButton2 = findViewById<FloatingActionButton>(R.id.floatingActionButton_grid2)
        val actionButton3 = findViewById<FloatingActionButton>(R.id.floatingActionButton_grid3)
        val actionButton4 = findViewById<FloatingActionButton>(R.id.floatingActionButton_grid4)
        val actionButton5 = findViewById<FloatingActionButton>(R.id.floatingActionButton_grid5)
        val actionButton6 = findViewById<FloatingActionButton>(R.id.floatingActionButton_grid6)
        val actionButton7 = findViewById<FloatingActionButton>(R.id.floatingActionButton_grid7)
        val actionButton8 = findViewById<FloatingActionButton>(R.id.floatingActionButton_grid8)

        val buttonAddAlbum = findViewById<Button>(R.id.button_addAlbum)
        buttonAddAlbum.setOnClickListener {
            Log.i("MainActivity", "Clicked the Add button")
            albumCount++

            if (albumCount < 8) {
                Picasso.with(this).load(nfcAlbumArt[0]).into(images[albumCount])
                images[albumCount].tag = "NotBlank"
                images[albumCount].contentDescription = sounds[0]
            }
        }

        img1.setOnLongClickListener {
            if (img1.tag == "NotBlank") {
                actionButton1.visibility = View.VISIBLE
            }
            true
        }

        img2.setOnLongClickListener {
            if (img2.tag == "NotBlank") {
                actionButton2.visibility = View.VISIBLE
            }
            true
        }

        img3.setOnLongClickListener {
            if (img3.tag == "NotBlank") {
                actionButton3.visibility = View.VISIBLE
            }
            true
        }

        img4.setOnLongClickListener {
            if (img4.tag == "NotBlank") {
                actionButton4.visibility = View.VISIBLE
            }
            true
        }

        img5.setOnLongClickListener {
            if (img5.tag == "NotBlank") {
                actionButton5.visibility = View.VISIBLE
            }
            true
        }

        img6.setOnLongClickListener {
            if (img6.tag == "NotBlank") {
                actionButton6.visibility = View.VISIBLE
            }
            true
        }

        img7.setOnLongClickListener {
            if (img7.tag == "NotBlank") {
                actionButton7.visibility = View.VISIBLE
            }
            true
        }

        img8.setOnLongClickListener {
            if (img8.tag == "NotBlank") {
                actionButton8.visibility = View.VISIBLE
            }
            true
        }

        actionButton1.setOnClickListener {
            Log.i("MainActivity", "Clicked the remove button")
            img1.setImageDrawable(img2.drawable)
            img1.tag = img2.tag
            img2.setImageDrawable(img3.drawable)
            img2.tag = img3.tag
            img3.setImageDrawable(img4.drawable)
            img3.tag = img4.tag
            img4.setImageDrawable(img5.drawable)
            img4.tag = img5.tag
            img5.setImageDrawable(img6.drawable)
            img5.tag = img6.tag
            img6.setImageDrawable(img7.drawable)
            img6.tag = img7.tag
            img7.setImageDrawable(img8.drawable)
            img7.tag = img8.tag
            img8.setImageDrawable(resources.getDrawable(R.drawable.blankalbum))
            img8.tag = "Blank"

            actionButton1.visibility = View.INVISIBLE
            albumCount--
        }

        actionButton2.setOnClickListener {
            img2.setImageDrawable(img3.drawable)
            img2.tag = img3.tag
            img3.setImageDrawable(img4.drawable)
            img3.tag = img4.tag
            img4.setImageDrawable(img5.drawable)
            img4.tag = img5.tag
            img5.setImageDrawable(img6.drawable)
            img5.tag = img6.tag
            img6.setImageDrawable(img7.drawable)
            img6.tag = img7.tag
            img7.setImageDrawable(img8.drawable)
            img7.tag = img8.tag
            img8.setImageDrawable(resources.getDrawable(R.drawable.blankalbum))
            img8.tag = "Blank"

            actionButton2.visibility = View.INVISIBLE
            albumCount--
        }

        actionButton3.setOnClickListener {
            img3.setImageDrawable(img4.drawable)
            img3.tag = img4.tag
            img4.setImageDrawable(img5.drawable)
            img4.tag = img5.tag
            img5.setImageDrawable(img6.drawable)
            img5.tag = img6.tag
            img6.setImageDrawable(img7.drawable)
            img6.tag = img7.tag
            img7.setImageDrawable(img8.drawable)
            img7.tag = img8.tag
            img8.setImageDrawable(resources.getDrawable(R.drawable.blankalbum))
            img8.tag = "Blank"

            actionButton3.visibility = View.INVISIBLE
            albumCount--
        }

        actionButton4.setOnClickListener {
            img4.setImageDrawable(img5.drawable)
            img4.tag = img5.tag
            img5.setImageDrawable(img6.drawable)
            img5.tag = img6.tag
            img6.setImageDrawable(img7.drawable)
            img6.tag = img7.tag
            img7.setImageDrawable(img8.drawable)
            img7.tag = img8.tag
            img8.setImageDrawable(resources.getDrawable(R.drawable.blankalbum))
            img8.tag = "Blank"

            actionButton4.visibility = View.INVISIBLE
            albumCount--
        }

        actionButton5.setOnClickListener {
            img5.setImageDrawable(img6.drawable)
            img5.tag = img6.tag
            img6.setImageDrawable(img7.drawable)
            img6.tag = img7.tag
            img7.setImageDrawable(img8.drawable)
            img7.tag = img8.tag
            img8.setImageDrawable(resources.getDrawable(R.drawable.blankalbum))
            img8.tag = "Blank"

            actionButton5.visibility = View.INVISIBLE
            albumCount--
        }

        actionButton6.setOnClickListener {
            img6.setImageDrawable(img7.drawable)
            img6.tag = img7.tag
            img7.setImageDrawable(img8.drawable)
            img7.tag = img8.tag
            img8.setImageDrawable(resources.getDrawable(R.drawable.blankalbum))
            img8.tag = "Blank"

            actionButton6.visibility = View.INVISIBLE
            albumCount--
        }

        actionButton7.setOnClickListener {
            img7.setImageDrawable(img8.drawable)
            img7.tag = img8.tag
            img8.setImageDrawable(resources.getDrawable(R.drawable.blankalbum))
            img8.tag = "Blank"

            actionButton7.visibility = View.INVISIBLE
            albumCount--
        }

        actionButton8.setOnClickListener {
            img8.setImageDrawable(resources.getDrawable(R.drawable.blankalbum))
            img8.tag = "Blank"

            actionButton8.visibility = View.INVISIBLE
            albumCount--
        }
    }

    override fun onStart() {
        super.onStart()
        // Set the connection parameters
        val connectionParams = ConnectionParams.Builder(clientId)
            .setRedirectUri(redirectUri)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(this, connectionParams, object : Connector.ConnectionListener {
            override fun onConnected(appRemote: SpotifyAppRemote) {
                spotifyAppRemote = appRemote
                Log.d("MainActivity", "Connected! Yay!")
                // Now you can start interacting with App Remote
                connected()
            }

            override fun onFailure(throwable: Throwable) {
                Log.e("MainActivity", throwable.message, throwable)
                // Something went wrong when attempting to connect! Handle errors here
            }
        })
    }

    private fun connected() {
        // Then we will write some more code here.
        Log.i("MainActivity", "In the connected method")
        spotifyAppRemote?.let { spotify ->
            img1.setOnClickListener {
                if (img1.tag == "NotBlank") {
                    spotify.playerApi.play(sounds[0])
                }
            }
            img2.setOnClickListener {
                if (img2.tag == "NotBlank") {
                    spotify.playerApi.play(sounds[1])
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        spotifyAppRemote?.let {
            SpotifyAppRemote.disconnect(it)
        }
    }

}