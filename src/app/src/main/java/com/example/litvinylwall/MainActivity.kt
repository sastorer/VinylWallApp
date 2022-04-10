package com.example.litvinylwall

import android.app.Activity
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior.getTag
import com.example.litvinylwall.data.Info
import com.example.litvinylwall.data.InfoDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.squareup.picasso.Picasso
import kotlin.random.Random.Default.nextInt


class MainActivity : AppCompatActivity() {
    @Suppress("DEPRECATION")

    val database: InfoDatabase by lazy { InfoDatabase.getInstance(this) }

    lateinit var img1 : ImageView
    lateinit var img2 : ImageView
    lateinit var img3 : ImageView
    lateinit var img4 : ImageView
    lateinit var img5 : ImageView
    lateinit var img6 : ImageView
    lateinit var img7 : ImageView
    lateinit var img8 : ImageView

    private var adapter: NfcAdapter? = null

    val sounds = arrayOf("spotify:album:6k3vC8nep1BfqAIJ81L6OL",
    "spotify:album:4oktVvRuO1In9B7Hz0xm0a",
    "spotify:album:0cnNCK2xpudXjB8pzsrYy9",
    "spotify:album:4QjGIZZqvNrdkgw4LZKLZK",
    "spotify:album:1CJDBCPg27ASz4eWE7oeNz",
    "spotify:album:0Q5XBpCYFgUWiG9DUWyAmJ",
    "spotify:album:3cQO7jp5S9qLBoIVtbkSM1",
    "spotify:album:5OZJflQcQCdZLQjtUudCin"
    )
    var nfcSound = ""
    var nfcAlbum = ""
    val blankAlbum = "https://i.imgur.com/V4QpirM.png"
    val albumArt = arrayOf("", "", "", "", "", "", "", "")
    val nfcAlbumArt = arrayOf("https://i.scdn.co/image/ab67616d00001e0269592e88bb29d610a35118f8",
    "https://i.scdn.co/image/ab67616d00001e0211b3df35e2e46d91f585afd9",
    "https://i.scdn.co/image/ab67616d00001e024849278ce9876ebea7353d66",
    "https://i.scdn.co/image/ab67616d00001e02377453abefeab743c5633fcc",
    "https://i.scdn.co/image/ab67616d00001e027f888564b247d806d838761e",
    "https://i.scdn.co/image/ab67616d00001e0220b467550945fd123e00f0a5",
    "https://i.scdn.co/image/ab67616d00001e02de03bfc2991fd5bcfde65ba3",
    "https://i.scdn.co/image/ab67616d00001e0274fefed78db6d6cf4d963fdc"
    )

    private val clientId = "4adfa19aee5a48e4a7634908838c3292"
    private val redirectUri = "lit-vinyl-wall://callback"
    private var spotifyAppRemote: SpotifyAppRemote? = null

    var albumCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNFCAdapter()

        if (database.infoDatabaseDao.count() != 0) {
            albumCount = database.infoDatabaseDao.count()
            Log.i("MainActivity", albumCount.toString())
        }

        img1 = findViewById<ImageView>(R.id.image_grid1)
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

        img2 = findViewById<ImageView>(R.id.image_grid2)
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

        img3 = findViewById<ImageView>(R.id.image_grid3)
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

        img4 = findViewById<ImageView>(R.id.image_grid4)
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

        img5 = findViewById<ImageView>(R.id.image_grid5)
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

        img6 = findViewById<ImageView>(R.id.image_grid6)
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

        img7 = findViewById<ImageView>(R.id.image_grid7)
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

        img8 = findViewById<ImageView>(R.id.image_grid8)
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
        val buttonDone = findViewById<Button>(R.id.button_done)
        val textViewScan = findViewById<TextView>(R.id.textView_scanTag)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraintLayout_addAlbum)
        buttonAddAlbum.setOnClickListener {
            Log.i("MainActivity", "Clicked the Add button")

            if (albumCount < 8) {
                constraintLayout.visibility = View.VISIBLE
                textViewScan.visibility = View.VISIBLE
                buttonDone.visibility = View.VISIBLE
                buttonDone.isClickable = true
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

                constraintLayout.visibility = View.INVISIBLE
                textViewScan.visibility = View.INVISIBLE
                buttonDone.visibility = View.INVISIBLE
                buttonDone.isClickable = false
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
            img8.setImageDrawable(resources.getDrawable(R.drawable.blankalbum))
            img8.tag = "Blank"
            albumArt[7] = blankAlbum

            actionButton1.visibility = View.INVISIBLE
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
            img8.setImageDrawable(resources.getDrawable(R.drawable.blankalbum))
            img8.tag = "Blank"
            albumArt[7] = blankAlbum

            actionButton2.visibility = View.INVISIBLE
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
            img8.setImageDrawable(resources.getDrawable(R.drawable.blankalbum))
            img8.tag = "Blank"
            albumArt[7] = blankAlbum

            actionButton3.visibility = View.INVISIBLE
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
            img8.setImageDrawable(resources.getDrawable(R.drawable.blankalbum))
            img8.tag = "Blank"
            albumArt[7] = blankAlbum

            actionButton4.visibility = View.INVISIBLE
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
            img8.setImageDrawable(resources.getDrawable(R.drawable.blankalbum))
            img8.tag = "Blank"
            albumArt[7] = blankAlbum

            actionButton5.visibility = View.INVISIBLE
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
            img8.setImageDrawable(resources.getDrawable(R.drawable.blankalbum))
            img8.tag = "Blank"
            albumArt[7] = blankAlbum

            actionButton6.visibility = View.INVISIBLE
            albumCount--
        }

        actionButton7.setOnClickListener {
            img7.setImageDrawable(img8.drawable)
            img7.tag = img8.tag
            img7.contentDescription = img8.contentDescription
            albumArt[6] = albumArt[7]
            img8.setImageDrawable(resources.getDrawable(R.drawable.blankalbum))
            img8.tag = "Blank"
            albumArt[7] = blankAlbum

            actionButton7.visibility = View.INVISIBLE
            albumCount--
        }

        actionButton8.setOnClickListener {
            img8.setImageDrawable(resources.getDrawable(R.drawable.blankalbum))
            img8.tag = "Blank"
            albumArt[7] = blankAlbum

            actionButton8.visibility = View.INVISIBLE
            albumCount--
        }
    }

    private fun initNFCAdapter() {
        val nfcManager = getSystemService(Context.NFC_SERVICE) as NfcManager
        adapter = nfcManager.defaultAdapter
    }

    override fun onResume() {
        super.onResume()
        enableNfcForegroundDispatch()
    }

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
            val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            if (rawMsgs != null) {
                getData(rawMsgs)
            }
            Toast.makeText(this@MainActivity, "Tag Scanned", Toast.LENGTH_SHORT).show()
        }
    }

    fun getData(rawMsgs: Array<Parcelable>) {
        val msgs = arrayOfNulls<NdefMessage>(rawMsgs.size)
        for (i in rawMsgs.indices) {
            msgs[i] = rawMsgs[i] as NdefMessage
        }

        val records = msgs[0]!!.records

        var recordData = ""

        for (record in records) {
            var recordURI = record.toUri()
            if (recordURI.toString().contains("https")) {
                nfcAlbum = recordURI.toString()
            } else if (recordURI.toString().contains("spotify:")) {
                nfcSound = recordURI.toString()
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
        // Then we will write some more code here.
        Log.i("MainActivity", "In the connected method")
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
        database.infoDatabaseDao.insert(savedImage1)
        database.infoDatabaseDao.insert(savedImage2)
        database.infoDatabaseDao.insert(savedImage3)
        database.infoDatabaseDao.insert(savedImage4)
        database.infoDatabaseDao.insert(savedImage5)
        database.infoDatabaseDao.insert(savedImage6)
        database.infoDatabaseDao.insert(savedImage7)
        database.infoDatabaseDao.insert(savedImage8)
    }

    override fun onDestroy() {
        super.onDestroy()
        spotifyAppRemote?.let {
            SpotifyAppRemote.disconnect(it)
        }
    }
}