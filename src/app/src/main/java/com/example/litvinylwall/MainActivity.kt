package com.example.litvinylwall

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.litvinylwall.data.Info
import com.example.litvinylwall.data.InfoDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {
    @Suppress("DEPRECATION")

    val database: InfoDatabase by lazy { InfoDatabase.getInstance(this) }

    private lateinit var img1 : ImageView
    private lateinit var img2 : ImageView
    private lateinit var img3 : ImageView
    private lateinit var img4 : ImageView
    private lateinit var img5 : ImageView
    private lateinit var img6 : ImageView
    private lateinit var img7 : ImageView
    private lateinit var img8 : ImageView
    private lateinit var img9 : ImageView

    private var adapter: NfcAdapter? = null
    private var nfcSound = ""
    private var nfcAlbum = ""
    private val blankAlbum = "https://i.imgur.com/V4QpirM.png"
    private val albumArt = arrayOf("", "", "", "", "", "", "", "", "")

    private val clientId = "4adfa19aee5a48e4a7634908838c3292"
    private val redirectUri = "lit-vinyl-wall://callback"
    private var spotifyAppRemote: SpotifyAppRemote? = null

    private var albumCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNFCAdapter()

        if (database.infoDatabaseDao.count() != 0) {
            albumCount = database.infoDatabaseDao.count()
            Log.i("MainActivity", albumCount.toString())
        }

        img1 = findViewById(R.id.image_grid1)
        val img1info =  database.infoDatabaseDao.get(0)
        if (img1info != null && img1info.imgTag != "Blank") {
            Picasso.with(this).load(img1info.imgURL).into(img1)
            img1.contentDescription = img1info.soundURI
            img1.tag = "NotBlank"
            albumArt[0] = img1info.imgURL
        } else {
            Picasso.with(this).load(blankAlbum).into(img1)
            img1.tag = "Blank"
            img1.contentDescription = ""
        }

        img2 = findViewById(R.id.image_grid2)
        val img2info =  database.infoDatabaseDao.get(1)
        if (img2info != null && img2info.imgTag != "Blank") {
            Picasso.with(this).load(img2info.imgURL).into(img2)
            img2.contentDescription = img2info.soundURI
            img2.tag = "NotBlank"
            albumArt[1] = img2info.imgURL
        } else {
            Picasso.with(this).load(blankAlbum).into(img2)
            img2.tag = "Blank"
            img2.contentDescription = ""
        }

        img3 = findViewById(R.id.image_grid3)
        val img3info =  database.infoDatabaseDao.get(2)
        if (img3info != null && img3info.imgTag != "Blank") {
            Picasso.with(this).load(img3info.imgURL).into(img3)
            img3.contentDescription = img3info.soundURI
            img3.tag = "NotBlank"
            albumArt[2] = img3info.imgURL
        } else {
            Picasso.with(this).load(blankAlbum).into(img3)
            img3.tag = "Blank"
            img3.contentDescription = ""
        }

        img4 = findViewById(R.id.image_grid4)
        val img4info =  database.infoDatabaseDao.get(3)
        if (img4info != null && img4info.imgTag != "Blank") {
            Picasso.with(this).load(img4info.imgURL).into(img4)
            img4.contentDescription = img4info.soundURI
            img4.tag = "NotBlank"
            albumArt[3] = img4info.imgURL
        } else {
            Picasso.with(this).load(blankAlbum).into(img4)
            img4.tag = "Blank"
            img4.contentDescription = ""
        }

        img5 = findViewById(R.id.image_grid5)
        val img5info =  database.infoDatabaseDao.get(4)
        if (img5info != null && img5info.imgTag != "Blank") {
            Picasso.with(this).load(img5info.imgURL).into(img5)
            img5.contentDescription = img5info.soundURI
            img5.tag = "NotBlank"
            albumArt[4] = img5info.imgURL
        } else {
            Picasso.with(this).load(blankAlbum).into(img5)
            img5.tag = "Blank"
            img5.contentDescription = ""
        }

        img6 = findViewById(R.id.image_grid6)
        val img6info =  database.infoDatabaseDao.get(5)
        if (img6info != null && img6info.imgTag != "Blank") {
            Picasso.with(this).load(img6info.imgURL).into(img6)
            img6.contentDescription = img6info.soundURI
            img6.tag = "NotBlank"
            albumArt[5] = img6info.imgURL
        } else {
            Picasso.with(this).load(blankAlbum).into(img6)
            img6.tag = "Blank"
            img6.contentDescription = ""
        }

        img7 = findViewById(R.id.image_grid7)
        val img7info =  database.infoDatabaseDao.get(6)
        if (img7info != null && img7info.imgTag != "Blank") {
            Picasso.with(this).load(img7info.imgURL).into(img7)
            img7.contentDescription = img7info.soundURI
            img7.tag = "NotBlank"
            albumArt[6] = img7info.imgURL
        } else {
            Picasso.with(this).load(blankAlbum).into(img7)
            img7.tag = "Blank"
            img7.contentDescription = ""
        }

        img8 = findViewById(R.id.image_grid8)
        val img8info =  database.infoDatabaseDao.get(7)
        if (img8info != null && img8info.imgTag != "Blank") {
            Picasso.with(this).load(img8info.imgURL).into(img8)
            img8.contentDescription = img8info.soundURI
            img8.tag = "NotBlank"
            albumArt[7] = img8info.imgURL
        } else {
            Picasso.with(this).load(blankAlbum).into(img8)
            img8.tag = "Blank"
            img8.contentDescription = ""
        }

        img9 = findViewById(R.id.image_grid9)
        val img9info =  database.infoDatabaseDao.get(8)
        if (img9info != null && img9info.imgTag != "Blank") {
            Picasso.with(this).load(img9info.imgURL).into(img9)
            img9.contentDescription = img9info.soundURI
            img9.tag = "NotBlank"
            albumArt[8] = img9info.imgURL
        } else {
            Picasso.with(this).load(blankAlbum).into(img9)
            img9.tag = "Blank"
            img9.contentDescription = ""
        }

        val images = arrayOf(img1, img2, img3, img4, img5, img6, img7, img8, img9)

        val actionButton1 = findViewById<FloatingActionButton>(R.id.floatingActionButton_grid1)
        val actionButton2 = findViewById<FloatingActionButton>(R.id.floatingActionButton_grid2)
        val actionButton3 = findViewById<FloatingActionButton>(R.id.floatingActionButton_grid3)
        val actionButton4 = findViewById<FloatingActionButton>(R.id.floatingActionButton_grid4)
        val actionButton5 = findViewById<FloatingActionButton>(R.id.floatingActionButton_grid5)
        val actionButton6 = findViewById<FloatingActionButton>(R.id.floatingActionButton_grid6)
        val actionButton7 = findViewById<FloatingActionButton>(R.id.floatingActionButton_grid7)
        val actionButton8 = findViewById<FloatingActionButton>(R.id.floatingActionButton_grid8)
        val actionButton9 = findViewById<FloatingActionButton>(R.id.floatingActionButton_grid9)

        val buttonAddAlbum = findViewById<Button>(R.id.button_addAlbum)
        val buttonDone = findViewById<Button>(R.id.button_done)
        val textViewScan = findViewById<TextView>(R.id.textView_scanTag)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraintLayout_addAlbum)
        val closeButton = findViewById<Button>(R.id.button_close)
        buttonAddAlbum.setOnClickListener {
            if (albumCount < 9) {
                constraintLayout.visibility = VISIBLE
                textViewScan.visibility = VISIBLE
                buttonDone.visibility = VISIBLE
                buttonDone.isClickable = true
                closeButton.isClickable = true
                closeButton.visibility = VISIBLE
            }
        }

        buttonDone.setOnClickListener {
            if (nfcSound != "" && nfcAlbum != "") {
                Picasso.with(this).load(nfcAlbum).into(images[albumCount])
                images[albumCount].tag = "NotBlank"
                images[albumCount].contentDescription = nfcSound
                albumArt[albumCount] = nfcAlbum

                nfcSound = ""
                nfcAlbum = ""

                albumCount++

                constraintLayout.visibility = INVISIBLE
                textViewScan.visibility = INVISIBLE
                buttonDone.visibility = INVISIBLE
                buttonDone.isClickable = false
                closeButton.isClickable = false
                closeButton.visibility = INVISIBLE

                if (albumCount == 9) {
                    buttonAddAlbum.visibility = INVISIBLE
                    buttonAddAlbum.isClickable = false
                }

            }
        }

        img1.setOnLongClickListener {
            if (img1.tag == "NotBlank") {
                actionButton1.visibility = VISIBLE
            }
            true
        }

        img2.setOnLongClickListener {
            if (img2.tag == "NotBlank") {
                actionButton2.visibility = VISIBLE
            }
            true
        }

        img3.setOnLongClickListener {
            if (img3.tag == "NotBlank") {
                actionButton3.visibility = VISIBLE
            }
            true
        }

        img4.setOnLongClickListener {
            if (img4.tag == "NotBlank") {
                actionButton4.visibility = VISIBLE
            }
            true
        }

        img5.setOnLongClickListener {
            if (img5.tag == "NotBlank") {
                actionButton5.visibility = VISIBLE
            }
            true
        }

        img6.setOnLongClickListener {
            if (img6.tag == "NotBlank") {
                actionButton6.visibility = VISIBLE
            }
            true
        }

        img7.setOnLongClickListener {
            if (img7.tag == "NotBlank") {
                actionButton7.visibility = VISIBLE
            }
            true
        }

        img8.setOnLongClickListener {
            if (img8.tag == "NotBlank") {
                actionButton8.visibility = VISIBLE
            }
            true
        }

        img9.setOnLongClickListener {
            if (img9.tag == "NotBlank") {
                actionButton9.visibility = VISIBLE
            }
            true
        }

        actionButton1.setOnClickListener {
            img1.setImageDrawable(img2.drawable)
            img1.tag = img2.tag
            img1.contentDescription = img2.contentDescription
            albumArt[0] = albumArt[1]
            img2.setImageDrawable(img3.drawable)
            img2.tag = img3.tag
            img2.contentDescription = img3.contentDescription
            albumArt[1] = albumArt[2]
            img3.setImageDrawable(img4.drawable)
            img3.tag = img4.tag
            img3.contentDescription = img4.contentDescription
            albumArt[2] = albumArt[3]
            img4.setImageDrawable(img5.drawable)
            img4.tag = img5.tag
            img4.contentDescription = img5.contentDescription
            albumArt[3] = albumArt[4]
            img5.setImageDrawable(img6.drawable)
            img5.tag = img6.tag
            img5.contentDescription = img6.contentDescription
            albumArt[4] = albumArt[5]
            img6.setImageDrawable(img7.drawable)
            img6.tag = img7.tag
            img6.contentDescription = img7.contentDescription
            albumArt[5] = albumArt[6]
            img7.setImageDrawable(img8.drawable)
            img7.tag = img8.tag
            img7.contentDescription = img8.contentDescription
            albumArt[6] = albumArt[7]
            img8.setImageDrawable(img9.drawable)
            img8.tag = img9.tag
            img8.contentDescription = img9.contentDescription
            albumArt[7] = albumArt[8]
            Picasso.with(this).load(blankAlbum).into(img9)
            img9.tag = "Blank"
            albumArt[8] = blankAlbum

            actionButton1.visibility = INVISIBLE
            albumCount--


        }

        actionButton2.setOnClickListener {
            img2.setImageDrawable(img3.drawable)
            img2.tag = img3.tag
            img2.contentDescription = img3.contentDescription
            albumArt[1] = albumArt[2]
            img3.setImageDrawable(img4.drawable)
            img3.tag = img4.tag
            img3.contentDescription = img4.contentDescription
            albumArt[2] = albumArt[3]
            img4.setImageDrawable(img5.drawable)
            img4.tag = img5.tag
            img4.contentDescription = img5.contentDescription
            albumArt[3] = albumArt[4]
            img5.setImageDrawable(img6.drawable)
            img5.tag = img6.tag
            img5.contentDescription = img6.contentDescription
            albumArt[4] = albumArt[5]
            img6.setImageDrawable(img7.drawable)
            img6.tag = img7.tag
            img6.contentDescription = img7.contentDescription
            albumArt[5] = albumArt[6]
            img7.setImageDrawable(img8.drawable)
            img7.tag = img8.tag
            img7.contentDescription = img8.contentDescription
            albumArt[6] = albumArt[7]
            img8.setImageDrawable(img9.drawable)
            img8.tag = img9.tag
            img8.contentDescription = img9.contentDescription
            albumArt[7] = albumArt[8]
            Picasso.with(this).load(blankAlbum).into(img9)
            img9.tag = "Blank"
            albumArt[8] = blankAlbum

            actionButton2.visibility = INVISIBLE
            albumCount--
        }

        actionButton3.setOnClickListener {
            img3.setImageDrawable(img4.drawable)
            img3.tag = img4.tag
            img3.contentDescription = img4.contentDescription
            albumArt[2] = albumArt[3]
            img4.setImageDrawable(img5.drawable)
            img4.tag = img5.tag
            img4.contentDescription = img5.contentDescription
            albumArt[3] = albumArt[4]
            img5.setImageDrawable(img6.drawable)
            img5.tag = img6.tag
            img5.contentDescription = img6.contentDescription
            albumArt[4] = albumArt[5]
            img6.setImageDrawable(img7.drawable)
            img6.tag = img7.tag
            img6.contentDescription = img7.contentDescription
            albumArt[5] = albumArt[6]
            img7.setImageDrawable(img8.drawable)
            img7.tag = img8.tag
            img7.contentDescription = img8.contentDescription
            albumArt[6] = albumArt[7]
            img8.setImageDrawable(img9.drawable)
            img8.tag = img9.tag
            img8.contentDescription = img9.contentDescription
            albumArt[7] = albumArt[8]
            Picasso.with(this).load(blankAlbum).into(img9)
            img9.tag = "Blank"
            albumArt[8] = blankAlbum

            actionButton3.visibility = INVISIBLE
            albumCount--
        }

        actionButton4.setOnClickListener {
            img4.setImageDrawable(img5.drawable)
            img4.tag = img5.tag
            img4.contentDescription = img5.contentDescription
            albumArt[3] = albumArt[4]
            img5.setImageDrawable(img6.drawable)
            img5.tag = img6.tag
            img5.contentDescription = img6.contentDescription
            albumArt[4] = albumArt[5]
            img6.setImageDrawable(img7.drawable)
            img6.tag = img7.tag
            img6.contentDescription = img7.contentDescription
            albumArt[5] = albumArt[6]
            img7.setImageDrawable(img8.drawable)
            img7.tag = img8.tag
            img7.contentDescription = img8.contentDescription
            albumArt[6] = albumArt[7]
            img8.setImageDrawable(img9.drawable)
            img8.tag = img9.tag
            img8.contentDescription = img9.contentDescription
            albumArt[7] = albumArt[8]
            Picasso.with(this).load(blankAlbum).into(img9)
            img9.tag = "Blank"
            albumArt[8] = blankAlbum

            actionButton4.visibility = INVISIBLE
            albumCount--
        }

        actionButton5.setOnClickListener {
            img5.setImageDrawable(img6.drawable)
            img5.tag = img6.tag
            img5.contentDescription = img6.contentDescription
            albumArt[4] = albumArt[5]
            img6.setImageDrawable(img7.drawable)
            img6.tag = img7.tag
            img6.contentDescription = img7.contentDescription
            albumArt[5] = albumArt[6]
            img7.setImageDrawable(img8.drawable)
            img7.tag = img8.tag
            img7.contentDescription = img8.contentDescription
            albumArt[6] = albumArt[7]
            img8.setImageDrawable(img9.drawable)
            img8.tag = img9.tag
            img8.contentDescription = img9.contentDescription
            albumArt[7] = albumArt[8]
            Picasso.with(this).load(blankAlbum).into(img9)
            img9.tag = "Blank"
            albumArt[8] = blankAlbum

            actionButton5.visibility = INVISIBLE
            albumCount--
        }

        actionButton6.setOnClickListener {
            img6.setImageDrawable(img7.drawable)
            img6.tag = img7.tag
            img6.contentDescription = img7.contentDescription
            albumArt[5] = albumArt[6]
            img7.setImageDrawable(img8.drawable)
            img7.tag = img8.tag
            img7.contentDescription = img8.contentDescription
            albumArt[6] = albumArt[7]
            img8.setImageDrawable(img9.drawable)
            img8.tag = img9.tag
            img8.contentDescription = img9.contentDescription
            albumArt[7] = albumArt[8]
            Picasso.with(this).load(blankAlbum).into(img9)
            img9.tag = "Blank"
            albumArt[8] = blankAlbum

            actionButton6.visibility = INVISIBLE
            albumCount--
        }

        actionButton7.setOnClickListener {
            img7.setImageDrawable(img8.drawable)
            img7.tag = img8.tag
            img7.contentDescription = img8.contentDescription
            albumArt[6] = albumArt[7]
            img8.setImageDrawable(img9.drawable)
            img8.tag = img9.tag
            img8.contentDescription = img9.contentDescription
            albumArt[7] = albumArt[8]
            Picasso.with(this).load(blankAlbum).into(img9)
            img9.tag = "Blank"
            albumArt[8] = blankAlbum

            actionButton7.visibility = INVISIBLE
            albumCount--
        }

        actionButton8.setOnClickListener {
            img8.setImageDrawable(img9.drawable)
            img8.tag = img9.tag
            img8.contentDescription = img9.contentDescription
            albumArt[7] = albumArt[8]
            Picasso.with(this).load(blankAlbum).into(img9)
            img9.tag = "Blank"
            albumArt[8] = blankAlbum

            actionButton8.visibility = INVISIBLE
            albumCount--
        }

        actionButton9.setOnClickListener {
            Picasso.with(this).load(blankAlbum).into(img9)
            img9.tag = "Blank"
            albumArt[8] = blankAlbum

            actionButton9.visibility = INVISIBLE

            buttonAddAlbum.visibility = VISIBLE
            buttonAddAlbum.isClickable = true
            albumCount--
        }

        closeButton.setOnClickListener {
            constraintLayout.visibility = INVISIBLE
            textViewScan.visibility = INVISIBLE
            buttonDone.visibility = INVISIBLE
            buttonDone.isClickable = false
            closeButton.visibility = INVISIBLE
            closeButton.isClickable = false

        }
    }

    private fun initNFCAdapter() {
        val nfcManager = getSystemService(Context.NFC_SERVICE) as NfcManager
        adapter = nfcManager.defaultAdapter
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onResume() {
        super.onResume()
        enableNfcForegroundDispatch()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun enableNfcForegroundDispatch() {
        try {
            val intent = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            val nfcPendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_MUTABLE)
            adapter?.enableForegroundDispatch(this, nfcPendingIntent, null, null)
        } catch (ex: IllegalStateException) {
            Log.e("MainActivity", "Error enabling NFC foreground dispatch", ex)
        }
    }

    override fun onPause() {
        disableNfcForegroundDispatch()
        super.onPause()
    }

    private fun disableNfcForegroundDispatch() {
        try {
            adapter?.disableForegroundDispatch(this)
        } catch (ex: IllegalStateException) {
            Log.e("MainActivity", "Error disabling NFC foreground dispatch", ex)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val rawMsg = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            if (rawMsg != null) {
                getData(rawMsg)
            }
            Toast.makeText(this@MainActivity, "Tag Scanned", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getData(rawMsg: Array<Parcelable>) {
        val msg = arrayOfNulls<NdefMessage>(rawMsg.size)
        for (i in rawMsg.indices) {
            msg[i] = rawMsg[i] as NdefMessage
        }

        val records = msg[0]!!.records

        var recordData = ""

        for (record in records) {
            val recordURI = record.toUri().toString()
            if (recordURI.contains("https")) {
                nfcAlbum = recordURI
            } else if (recordURI.contains("spotify:")) {
                nfcSound = recordURI
            }
            recordData += record.toUri()
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
        spotifyAppRemote?.let { spotify ->
            img1.setOnClickListener {
                if (img1.tag == "NotBlank") {
                    spotify.playerApi.play(img1.contentDescription.toString())
                }
            }
            img2.setOnClickListener {
                if (img2.tag == "NotBlank") {
                    spotify.playerApi.play(img2.contentDescription.toString())
                }
            }
            img3.setOnClickListener {
                if (img3.tag == "NotBlank") {
                    spotify.playerApi.play(img3.contentDescription.toString())
                }
            }
            img4.setOnClickListener {
                if (img4.tag == "NotBlank") {
                    spotify.playerApi.play(img4.contentDescription.toString())
                }
            }
            img5.setOnClickListener {
                if (img5.tag == "NotBlank") {
                    spotify.playerApi.play(img5.contentDescription.toString())
                }
            }
            img6.setOnClickListener {
                if (img6.tag == "NotBlank") {
                    spotify.playerApi.play(img6.contentDescription.toString())
                }
            }
            img7.setOnClickListener {
                if (img7.tag == "NotBlank") {
                    spotify.playerApi.play(img7.contentDescription.toString())
                }
            }
            img8.setOnClickListener {
                if (img8.tag == "NotBlank") {
                    spotify.playerApi.play(img8.contentDescription.toString())
                }
            }
            img9.setOnClickListener {
                if (img9.tag == "NotBlank") {
                    spotify.playerApi.play(img9.contentDescription.toString())
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        val savedImage1 = Info(0, albumArt[0], img1.contentDescription.toString(), img1.tag.toString())
        val savedImage2 = Info(1, albumArt[1], img2.contentDescription.toString(), img2.tag.toString())
        val savedImage3 = Info(2, albumArt[2], img3.contentDescription.toString(), img3.tag.toString())
        val savedImage4 = Info(3, albumArt[3], img4.contentDescription.toString(), img4.tag.toString())
        val savedImage5 = Info(4, albumArt[4], img5.contentDescription.toString(), img5.tag.toString())
        val savedImage6 = Info(5, albumArt[5], img6.contentDescription.toString(), img6.tag.toString())
        val savedImage7 = Info(6, albumArt[6], img7.contentDescription.toString(), img7.tag.toString())
        val savedImage8 = Info(7, albumArt[7], img8.contentDescription.toString(), img8.tag.toString())
        val savedImage9 = Info(8, albumArt[8], img9.contentDescription.toString(), img9.tag.toString())
        database.infoDatabaseDao.insert(savedImage1)
        database.infoDatabaseDao.insert(savedImage2)
        database.infoDatabaseDao.insert(savedImage3)
        database.infoDatabaseDao.insert(savedImage4)
        database.infoDatabaseDao.insert(savedImage5)
        database.infoDatabaseDao.insert(savedImage6)
        database.infoDatabaseDao.insert(savedImage7)
        database.infoDatabaseDao.insert(savedImage8)
        database.infoDatabaseDao.insert(savedImage9)
    }

    override fun onDestroy() {
        super.onDestroy()
        spotifyAppRemote?.let {
            SpotifyAppRemote.disconnect(it)
        }
    }
}