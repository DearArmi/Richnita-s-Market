package com.example.mailbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        val userEditText = findViewById<EditText>(R.id.user_ed)
        val passwordEditText = findViewById<EditText>(R.id.pass_ed)
        val submitButton = findViewById<Button>(R.id.submit_bt)

        submitButton.setOnClickListener() {

            //Connecting to "API"
            val api = API()
            api.connectToURL()

            val user = User(userEditText.text.toString(), passwordEditText.text.toString())

            //Validating user
                if (api.searchUser(user)) {
                    //Searching user MailBox
                    val mailBox = api.searchMailBox()
                    openNewActivity(user, mailBox)
                } else {
                Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun openNewActivity(user:User, mailBox: MailBox) {

        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.USER_KEY, user)
        intent.putExtra(DetailsActivity.MAILBOX_KEY, mailBox)
        startActivity(intent)
    }




}