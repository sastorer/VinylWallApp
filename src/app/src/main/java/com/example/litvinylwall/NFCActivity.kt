package com.example.litvinylwall

import android.app.Activity
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.litvinylwall.databinding.ActivityNfcactivityBinding

@Suppress("DEPRECATION")
class NFCActivity : AppCompatActivity() {
    // NFC adapter for checking NFC state in the device
    private var nfcAdapter : NfcAdapter? = null

    var soundURI = ""
    var imgURL = ""

    // Pending intent for NFC intent foreground dispatch.
    // Used to read all NDEF tags while the app is running in the foreground.
    private var nfcPendingIntent: PendingIntent? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfcactivity)

        // Check if NFC is supported and enabled
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        Log.i("NFC supported", (nfcAdapter != null).toString())
        Log.i("NFC enabled", (nfcAdapter?.isEnabled).toString())


        // Read all tags when app is running and in the foreground
        // Create a generic PendingIntent that will be deliver to this activity. The NFC stack
        // will fill in the intent with the details of the discovered tag before delivering to
        // this activity.
        nfcPendingIntent = PendingIntent.getActivity(this, 0,
            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), FLAG_MUTABLE)

        if (intent != null) {
            // Check if the app was started via an NDEF intent
            Log.i("Found intent in onCreate", intent.action.toString())
            processIntent(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Get all NDEF discovered intents
        // Makes sure the app gets all discovered NDEF messages as long as it's in the foreground.
        nfcAdapter?.enableForegroundDispatch(this, nfcPendingIntent, null, null);
    }

    override fun onPause() {
        super.onPause()
        // Disable foreground dispatch, as this activity is no longer in the foreground
        nfcAdapter?.disableForegroundDispatch(this);
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i("Found intent in onNewIntent", intent?.action.toString())
        // If we got an intent while the app is running, also check if it's a new NDEF message
        // that was discovered
        if (intent != null) {
            processIntent(intent)

            Log.i("Intent", imgURL)
            Log.i("Intent", soundURI)
            intent.putExtra("imgURL", imgURL)
            intent.putExtra("soundURI", soundURI)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    /**
     * Check if the Intent has the action "ACTION_NDEF_DISCOVERED". If yes, handle it
     * accordingly and parse the NDEF messages.
     * @param checkIntent the intent to parse and handle if it's the right type
     */
    private fun processIntent(checkIntent: Intent) {
        // Check if intent has the action of a discovered NFC tag
        // with NDEF formatted contents
        if (checkIntent.action == NfcAdapter.ACTION_NDEF_DISCOVERED) {
            Log.i("New NDEF intent", checkIntent.toString())

            // Retrieve the raw NDEF message from the tag
            val rawMessages = checkIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            Log.i("Raw messages", rawMessages?.size.toString())

            // Complete variant: parse NDEF messages
            if (rawMessages != null) {
                val messages = arrayOfNulls<NdefMessage?>(rawMessages.size)// Array<NdefMessage>(rawMessages.size, {})
                for (i in rawMessages.indices) {
                    messages[i] = rawMessages[i] as NdefMessage;
                }
                // Process the messages array.
                processNdefMessages(messages)
            }
        }
    }

    /**
     * Parse the NDEF message contents and print these to the on-screen log.
     */
    private fun processNdefMessages(ndefMessages: Array<NdefMessage?>) {
        // Go through all NDEF messages found on the NFC tag
        for (curMsg in ndefMessages) {
            if (curMsg != null) {
                // Print generic information about the NDEF message
                Log.i("Message", curMsg.toString())
                // The NDEF message usually contains 1+ records - print the number of recoreds
                Log.i("Records", curMsg.records.size.toString())

                // Loop through all the records contained in the message
                for (curRecord in curMsg.records) {
                    if (curRecord.toUri() != null && curRecord.toUri().toString()
                            .contains("https")
                    ) {
                        // URI NDEF Tag
                        val tv1 = findViewById<TextView>(R.id.textView_imgURL)
                        tv1.text = curRecord.toUri().toString()
                        imgURL = curRecord.toUri().toString()
                    } else if (curRecord.toUri() != null) {
                        val tv2 = findViewById<TextView>(R.id.textView_soundURI)
                        tv2.text = curRecord.toUri().toString()
                        soundURI = curRecord.toUri().toString()
                    } else {
                            // Other NDEF Tags - simply print the payload
                            Log.i("- Contents", curRecord.payload.contentToString())
                    }
                }
            }
        }
    }
}