package com.example.litvinylwall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView

var albumCount = -1

class MainActivity : AppCompatActivity() {
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun addButtonClick (view : View) {
        Log.i("MainActivity", "Clicked the Add button")
        albumCount++

        val img1 = findViewById<ImageView>(R.id.image_grid1)
        val img2 = findViewById<ImageView>(R.id.image_grid2)
        val img3 = findViewById<ImageView>(R.id.image_grid3)
        val img4 = findViewById<ImageView>(R.id.image_grid4)
        val img5 = findViewById<ImageView>(R.id.image_grid5)
        val img6 = findViewById<ImageView>(R.id.image_grid6)
        val img7 = findViewById<ImageView>(R.id.image_grid7)
        val img8 = findViewById<ImageView>(R.id.image_grid8)
        val images = arrayOf(img1, img2, img3, img4, img5, img6, img7, img8)

        if (albumCount < 8) {
            images[albumCount].setImageResource(R.drawable.anawesomewave)
        }

    }
}