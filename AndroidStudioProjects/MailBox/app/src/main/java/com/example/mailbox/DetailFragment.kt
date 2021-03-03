package com.example.mailbox

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class DetailFragment : Fragment() {

    private lateinit var userText:TextView
    private lateinit var mailText:TextView
    private lateinit var button: Button
    private lateinit var distanceText:TextView
    private lateinit var mailBox2: MailBox.MailBoxStatus

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_detail, container, false)

        distanceText = rootView.findViewById(R.id.detail_frag_distance)
        userText = rootView.findViewById(R.id.detail_frag_user)
        mailText = rootView.findViewById(R.id.detail_frag_mailbox)
        button = rootView.findViewById(R.id.unlock_bt)


        button.setOnClickListener(){

            if (button.isEnabled){

                Toast.makeText(requireActivity(), "MailBox Status Changed", Toast.LENGTH_SHORT).show()
                changeButtonString(mailBox2)
                //TODO-----Open MAilBox BLE
            }else{
                Toast.makeText(requireActivity(), "Too Far From MailBox", Toast.LENGTH_SHORT).show()
                changeButtonString(mailBox2)
                //TODO----Close MAilBox BLE
            }
        }

        return rootView
    }

    //Setting user info
    @SuppressLint("SetTextI18n")
    fun setUserData(user: User, mailBox: MailBox) {

        mailBox2 = mailBox.status
        userText.text = "User: " + user.name
        mailText.text = "Lat: " + mailBox.latitude.toString() + " Lon:" +  mailBox.longitude.toString()

        changeButtonString(mailBox2)

    }

    private fun changeButtonString(mailBox: MailBox.MailBoxStatus) {

        if (mailBox == MailBox.MailBoxStatus.LOCKED){
            button.text = getString(R.string.unlock)
        }else{
            button.text = getString(R.string.lock)
        }

    }

    @SuppressLint("SetTextI18n")
    fun getDistance(distance:Float){

        //Setting distance format
        if (distance >= 1000){
            distanceText.text = "Distance: " + "%.2f".format(distance/1000) + " KM"
        }else if(distance <1000){
            distanceText.text = "Distance: " + "%.2f".format(distance) + " M"
        }

        //Checking distance to enable button
        if (distance <= 100.0){
            Toast.makeText(requireActivity(), "You Can Unlock the Mailbox", Toast.LENGTH_LONG).show()
            button.isEnabled = true

        }else{
            Toast.makeText(requireActivity(), "You are NOT close enough to unlock the Mailbox", Toast.LENGTH_LONG).show()
            button.isEnabled = false

        }
    }

}
