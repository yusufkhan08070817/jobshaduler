package com.example.jobshaduler.Activity

import android.content.Intent
import android.graphics.BlendMode
import android.graphics.Color
import android.os.Build

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.widget.Toast
import com.example.animation.LIB.AnimationKC
import com.example.jobshaduler.FireBase.FBAuth
import com.example.jobshaduler.R
import com.example.jobshaduler.databinding.ActivityAuthactivityBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.Objects

class Authactivity : AppCompatActivity() {
    var passwordtogle: Boolean = false
    lateinit var auth: FirebaseAuth
    lateinit var ani: AnimationKC
    private lateinit var b: ActivityAuthactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAuthactivityBinding.inflate(layoutInflater)
        setContentView(b.root)
        auth = FirebaseAuth.getInstance()
        ani = AnimationKC(this)
        nonavigatation()
        ani.AnimationStater(b.createemail, ani.toleft)
        ani.AnimationStater(b.createpassword, ani.toleft)
        ani.AnimationStater(b.signupsignin, ani.toright)
        ani.AnimationStater(b.cardiconback, ani.zero_to_origal)



        b.backbuttontocreateaccount.setOnClickListener {

            b.backbuttontocreateaccount.visibility = View.GONE
            ani.AnimationStater(b.backbuttontocreateaccount, ani.go_out_from_left)
            b.createemail.visibility = View.VISIBLE
            ani.AnimationStater(b.createemail, ani.zero_to_origal)
            b.phonenumber.visibility = View.GONE
            ani.AnimationStater(b.phonenumber, ani.orignal_to_zero)
            b.createpassword.visibility = View.VISIBLE
            ani.AnimationStater(b.createpassword, ani.zero_to_origal)
            b.companiname.visibility = View.GONE
            ani.AnimationStater(b.companiname, ani.orignal_to_zero)
            b.signupsignin.visibility = View.VISIBLE
            ani.AnimationStater(b.signupsignin, ani.toright)
            b.jobtype.visibility = View.GONE
            ani.AnimationStater(b.jobtype, ani.go_out_from_right)
        }
        b.signup.setOnClickListener {
            val email = b.email.text.toString()
            val pass = b.password.text.toString()
            val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
            if (!emailRegex.matches(email) || email.isEmpty()) {
                Toast.makeText(this, "in currect email;", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pass.length < 6) {
                Toast.makeText(this, "password galat h", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                if (it.isSuccessful) {
                    createani()
                } else {
                    Log.e("fbauth error", "${it.exception}")
                }
            }
        }
        b.passwordtogale.setOnClickListener {
            tr {
                if (passwordtogle) {
                    b.passwordtogale.setImageResource(R.drawable.showpass)
                    b.password.inputType = InputType.TYPE_CLASS_TEXT

                } else {
                    b.passwordtogale.setImageResource(R.drawable.hidepass)
                    b.password.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD


                }
                passwordtogle = !passwordtogle
            }

        }
b.signin.setOnClickListener {
    val email = b.email.text.toString()
    val pass = b.password.text.toString()
    val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    if (!emailRegex.matches(email) || email.isEmpty()) {
        Toast.makeText(this, "in currect email;", Toast.LENGTH_SHORT).show()
        return@setOnClickListener
    }
    if (pass.length < 6) {
        Toast.makeText(this, "password galat h", Toast.LENGTH_SHORT).show()
        return@setOnClickListener
    }
    auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
        if (it.isSuccessful)
        {
            startActivity(Intent(this, MainActivity::class.java).apply {
                setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

            })
            finish()
        }else{
            Log.e("fbauth error","${it.exception}")
        }
    }
}

    }

    fun tr(func: () -> Unit) {
        try {
            func()
        } catch (E: Exception) {
            Log.e("error ", "$E")
        }
    }

    fun nonavigatation() {
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        decorView.systemUiVisibility = uiOptions


    }

    fun createani() {
        b.backbuttontocreateaccount.visibility = View.VISIBLE
        ani.AnimationStater(b.backbuttontocreateaccount, ani.long_toright)
        b.createemail.visibility = View.GONE
        ani.AnimationStater(b.createemail, ani.orignal_to_zero)
        b.phonenumber.visibility = View.VISIBLE
        ani.AnimationStater(b.phonenumber, ani.zero_to_origal)
        b.createpassword.visibility = View.GONE
        ani.AnimationStater(b.createpassword, ani.orignal_to_zero)
        b.companiname.visibility = View.VISIBLE
        ani.AnimationStater(b.companiname, ani.zero_to_origal)
        b.signupsignin.visibility = View.GONE
        ani.AnimationStater(b.signupsignin, ani.go_out_from_right)
        b.jobtype.visibility = View.VISIBLE
        ani.AnimationStater(b.jobtype, ani.long_toright)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java).apply {
                setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

            })
            finish()
        }


    }


}