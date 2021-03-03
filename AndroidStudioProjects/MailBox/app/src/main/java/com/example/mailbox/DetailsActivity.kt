package com.example.mailbox


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast


class DetailsActivity : AppCompatActivity(), MapsFragment2.MailBoxListener {

    private lateinit var detailFragment: DetailFragment
    private lateinit var mapFragment: MapsFragment2
    private var backPressTime:Long = 0

    companion object {
        const val MAILBOX_KEY = "mailbox"
        const val USER_KEY = "user"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map_frag2) as MapsFragment2
        detailFragment = supportFragmentManager.findFragmentById(R.id.detail_frag) as DetailFragment

        val extras = intent.extras!!
        val user = extras.getParcelable<User>(USER_KEY)!!
        val mailBox = extras.getParcelable<MailBox>(MAILBOX_KEY)!!

        //Sending User data DetailFragment
        detailFragment.setUserData(user, mailBox)
        //Sending MailBox data MapFragment
        mapFragment.setMailBoxLocation(mailBox)

    }

    //Closing DetailActivity when tapping back twice
    override fun onBackPressed() {

        if (backPressTime+1000 > System.currentTimeMillis()){
            super.onBackPressed()
                openMainActivity()
            return
        }else{
            Toast.makeText(this, "Press back again to close",Toast.LENGTH_SHORT).show()
        }
        backPressTime = System.currentTimeMillis()

    }

    //Using interface from MapFragment to get distance
    override fun onShowedLocation(distance: Float) {

        //Passing distance to DetailFragment
        detailFragment.getDistance(distance)
    }

    private fun openMainActivity() {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}