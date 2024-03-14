package com.example.jobshaduler.Activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.jobshaduler.R
import com.example.jobshaduler.classes.singleton.emailandpass
import com.example.jobshaduler.databinding.ActivityProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Profile : AppCompatActivity() {
    lateinit var b: ActivityProfileBinding
    var isedit = false
    lateinit var db: FirebaseFirestore
    lateinit var userEmailPass: HashMap<String, Any?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(b.root)


        db = Firebase.firestore
        b.editbutton.setOnClickListener {
            isedit = !isedit
            if (isedit) {
                b.editbutton.setBackgroundColor(getColor(R.color.card1))
            } else {
                b.editbutton.setBackgroundColor(getColor(R.color.card2))
            }
        }
        b.phonenoblock.setOnClickListener {
            editblock("edit phone No", b.profilephonenumber)
        }

        b.passb.setOnClickListener {
            editblock("change password", b.passb)
        }
        b.profilenameemail.setOnClickListener {
            editblock("change name", b.lundkball)

        }
        b.logout.setOnClickListener {
            Firebase.auth.signOut()
            emailandpass.compani=null
            emailandpass.jobtype=null
            emailandpass.email=null
            emailandpass.pass=null
            emailandpass.usename=null
            emailandpass.phone=null
            startActivity(Intent(this,Authactivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            })
        }
        b.lundkball.text = emailandpass.usename
        b.maderchodemail.text = emailandpass.email!!
        b.passb.text = emailandpass.pass
        b.profilejob.text = emailandpass.jobtype
        Glide.with(this).load(emailandpass.image).into(b.profiledp)
        b.backtomain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            })
        }

        b.profiledp.setOnClickListener {
            editblock("image url", b.lundkball)

        }
    }

    fun editblock(label: String, view: TextView) {
        if (isedit) {

            b.editblock.visibility = View.VISIBLE
            b.layoutbuttonsanctionlayoutlable.text = label

            b.editblocksubmit.setOnClickListener {
                view.text = b.layoutbuttonsanctionlayoutedit.text
                b.editblock.visibility = View.GONE
                settoemailpass(label, b.layoutbuttonsanctionlayoutedit.text.toString())

            }
            b.editblockcancle.setOnClickListener {
                b.editblock.visibility = View.GONE
            }
        }
    }

    override fun onBackPressed() {
        if (0 == 2)
            super.onBackPressed()
    }


    fun settoemailpass(type: String, setdata: String) {
        when (type) {
            "edit phone No" -> {
                emailandpass.phone = setdata
                Toast.makeText(this, "${emailandpass.phone}", Toast.LENGTH_SHORT).show()
                userEmailPass = hashMapOf(
                    "email" to emailandpass.email,
                    "password" to emailandpass.pass,
                    "phoneNo" to emailandpass.phone,
                    "compani" to emailandpass.compani,
                    "job_type" to emailandpass.jobtype,
                    "username" to emailandpass.usename,
                    "dp" to emailandpass.image)
                dpupdate()
            }

            "change password" -> {
                emailandpass.pass = setdata
                Toast.makeText(this, "${emailandpass.pass}", Toast.LENGTH_SHORT).show()
                userEmailPass = hashMapOf(
                    "email" to emailandpass.email,
                    "password" to emailandpass.pass,
                    "phoneNo" to emailandpass.phone,
                    "compani" to emailandpass.compani,
                    "job_type" to emailandpass.jobtype,
                    "username" to emailandpass.usename,
                    "dp" to emailandpass.image)
                dpupdate()
            }

            "change name" -> {
                emailandpass.usename = setdata
                Toast.makeText(this, "${emailandpass.usename}", Toast.LENGTH_SHORT).show()
                userEmailPass = hashMapOf(
                    "email" to emailandpass.email,
                    "password" to emailandpass.pass,
                    "phoneNo" to emailandpass.phone,
                    "compani" to emailandpass.compani,
                    "job_type" to emailandpass.jobtype,
                    "username" to emailandpass.usename,
                    "dp" to emailandpass.image)
                dpupdate()

            }
            "image url"->{
                emailandpass.image = setdata
                Toast.makeText(this, "${emailandpass.image}", Toast.LENGTH_SHORT).show()
                userEmailPass = hashMapOf(
                    "email" to emailandpass.email,
                    "password" to emailandpass.pass,
                    "phoneNo" to emailandpass.phone,
                    "compani" to emailandpass.compani,
                    "job_type" to emailandpass.jobtype,
                    "username" to emailandpass.usename,
                    "dp" to emailandpass.image)
                dpupdate()
                Glide.with(this).load(emailandpass.image).into(b.profiledp)
            }
        }
        dpupdate()
    }

    fun dpupdate() {
        db.collection(emailandpass.email!!).document("personalinfo").set(userEmailPass)
            .addOnCompleteListener {
                if (it.isSuccessful) {


                    Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show()
                }

            }.addOnFailureListener {
                Toast.makeText(this, "${it.cause}", Toast.LENGTH_SHORT).show()
            }
    }


}