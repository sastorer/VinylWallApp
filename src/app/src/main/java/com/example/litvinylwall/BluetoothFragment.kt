package com.example.litvinylwall

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BluetoothFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BluetoothFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bluetooth, container, false)
        val button1 = view.findViewById<Button>(R.id.button_select1)
        val button2 = view.findViewById<Button>(R.id.button_select2)
        val closeButtonFrag = view.findViewById<Button>(R.id.button_closeFrag)

        closeButtonFrag.setOnClickListener {
            val manager = requireActivity().supportFragmentManager
            manager.beginTransaction().remove(this).commit()
        }

        val client = Mqtt5Client.builder()
            .identifier(UUID.randomUUID().toString())
            .serverHost("test.mosquitto.org")
            .buildBlocking()

        client.connect()

        button1.setOnClickListener {
            Log.i("Fragment", "Clicked button 1")
            client.publishWith().topic("which_lights_topic").qos(MqttQos.AT_LEAST_ONCE).payload("1".toByteArray()).send();
        }

        button2.setOnClickListener {
            Log.i("Fragment", "Clicked button 2")
            client.publishWith().topic("which_lights_topic").qos(MqttQos.AT_LEAST_ONCE).payload("2".toByteArray()).send();

        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment BluetoothFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            BluetoothFragment().apply {}
    }
}