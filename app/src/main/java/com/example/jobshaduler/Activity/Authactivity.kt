package com.example.jobshaduler.Activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.animation.LIB.AnimationKC
import com.example.jobshaduler.R
import com.example.jobshaduler.classes.singleton.emailandpass
import com.example.jobshaduler.databinding.ActivityAuthactivityBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class Authactivity : AppCompatActivity() {
    var passwordtogle: Boolean = false
    lateinit var auth: FirebaseAuth
    lateinit var ani: AnimationKC
    private lateinit var b: ActivityAuthactivityBinding
    lateinit var db: FirebaseFirestore
    lateinit var getdocref: DocumentReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAuthactivityBinding.inflate(layoutInflater)
        setContentView(b.root)

        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore
        currentdate()

        checkforpermision()
        ani = AnimationKC(this)
        nonavigatation()
        ani.AnimationStater(b.createemail, ani.toleft)
        ani.AnimationStater(b.createpassword, ani.toleft)
        ani.AnimationStater(b.signupsignin, ani.toright)
        ani.AnimationStater(b.cardiconback, ani.zero_to_origal)

        b.employ.setOnClickListener {

            emailandpass.jobtype = "employ"
            emailandpass.phone = b.phonenumberenter.text.toString()
            emailandpass.compani = b.complayname.text.toString()
            emailandpass.usename = ""
            emailandpass.image = ""
            val userEmailPass = hashMapOf(
                "email" to emailandpass.email,
                "password" to emailandpass.pass,
                "phoneNo" to emailandpass.phone,
                "compani" to emailandpass.compani,
                "job_type" to emailandpass.jobtype
            )
            Log.e("msg", "$userEmailPass")
            var emp = emailandpass.email!!.substringBefore("@")
            if (emp.contains("."))
                emp = emp.replace(".", "m")

            if (emp.contains("#"))
                emp = emp.replace("#", "m")

            if (emp.contains("$"))
                emp = emp.replace("$", "m")

            if (emp.contains("["))
                emp = emp.replace("[", "m")

            if (emp.contains("]"))
                emp = emp.replace("]", "m")
            emailandpass.empid = emp

            Toast.makeText(this, "${emailandpass.empid}", Toast.LENGTH_SHORT).show()

            db.collection("${emailandpass.compani}").document("employlist")
                .update("employ_list", FieldValue.arrayUnion(emailandpass.empid))
                .addOnSuccessListener {
                    // Update successful
                    println("Data added to array successfully!")
                }
                .addOnFailureListener { e ->
                    // Handle failures
                    println("Error adding data to array: $e")
                }
            db.collection(emailandpass.email!!).document("personalinfo").set(userEmailPass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this, Profile::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        })
                        Toast.makeText(this, "db store", Toast.LENGTH_SHORT).show()
                    }

                }.addOnFailureListener {
                    Toast.makeText(this, "${it.cause}", Toast.LENGTH_SHORT).show()
                }
        }
        b.admin.setOnClickListener {
            emailandpass.jobtype = "admin"
            emailandpass.phone = b.phonenumberenter.text.toString()
            emailandpass.compani = b.complayname.text.toString()
            emailandpass.usename = ""
            emailandpass.image = ""
            val userEmailPass = hashMapOf(
                "email" to emailandpass.email,
                "password" to emailandpass.pass,
                "phoneNo" to emailandpass.phone,
                "compani" to emailandpass.compani,
                "job_type" to emailandpass.jobtype
            )

            val employlist = hashMapOf(
                "employ_list" to null
            )
            db.collection("${emailandpass.compani}").document("employlist").set(employlist)
                .addOnSuccessListener {
                    println("successfull")
                }.addOnFailureListener {
                println("fail to upload ${it.cause} and msg${it.message}")
            }

            Log.e("msg", "$userEmailPass")
            db.collection(emailandpass.email!!).document("personalinfo").set(userEmailPass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this, Profile::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        })
                        Toast.makeText(this, "db store", Toast.LENGTH_SHORT).show()
                    }

                }.addOnFailureListener {
                    Toast.makeText(this, "${it.cause}", Toast.LENGTH_SHORT).show()
                }
        }

        b.backbuttontocreateaccount.setOnClickListener {
            accountdelete()
            backani()
        }
        b.signup.setOnClickListener {
            val email = b.email.text.toString()
            val pass = b.password.text.toString()
            val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
            if (!emailRegex.matches(email) || email.isEmpty()) {
                Toast.makeText(this, "in correct email;", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pass.length < 6) {
                Toast.makeText(this, "password galat h", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                if (it.isSuccessful) {
                    emailandpass.email = email
                    emailandpass.pass = pass
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
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                if (it.isSuccessful) {
                    getdocref =
                        Firebase.firestore.collection(email).document("personalinfo")
                    getdocref.get().addOnSuccessListener {
                        if (it.exists()) {

                            emailandpass.compani = it.getString("compani")
                            emailandpass.pass = it.getString("password")
                            emailandpass.phone = it.getString("phoneNo")
                            emailandpass.jobtype = it.getString("job_type")
                            emailandpass.usename = it.getString("username")
                            emailandpass.image = it.getString("dp")

                            var emp = email.substringBefore("@")
                            if (emp.contains("."))
                                emp = emp.replace(".", "m")

                            if (emp.contains("#"))
                                emp = emp.replace("#", "m")

                            if (emp.contains("$"))
                                emp = emp.replace("$", "m")

                            if (emp.contains("["))
                                emp = emp.replace("[", "m")

                            if (emp.contains("]"))
                                emp = emp.replace("]", "m")
                            emailandpass.empid = emp
                            Toast.makeText(this, "${emailandpass.usename}", Toast.LENGTH_SHORT)
                                .show()
                            Log.e("msg", emailandpass.compani!!)
                            Log.e("msg", emailandpass.pass!!)
                            Log.e("msg", emailandpass.phone!!)
                            Log.e("msg", emailandpass.jobtype!!)
                            Log.e("msg", emailandpass.empid!!)

                            startActivity(Intent(this, MainActivity::class.java).apply {
                                setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

                            })

                            finish()
                        }
                    }
                    startActivity(Intent(this, MainActivity::class.java).apply {
                        setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

                    })
                    finish()
                } else {
                    Log.e("fbauth error", "${it.exception}")
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

            if (currentUser.email != null) {
                emailandpass.email = currentUser.email
                getdocref =
                    Firebase.firestore.collection(emailandpass.email!!).document("personalinfo")
                getdocref.get().addOnSuccessListener {
                    if (it.exists()) {

                        emailandpass.compani = it.getString("compani")
                        emailandpass.pass = it.getString("password")
                        emailandpass.phone = it.getString("phoneNo")
                        emailandpass.jobtype = it.getString("job_type")
                        emailandpass.usename = it.getString("username")
                        emailandpass.image = it.getString("dp")

                        var emp = emailandpass.email!!.substringBefore("@")
                        if (emp.contains("."))
                            emp = emp.replace(".", "m")

                        if (emp.contains("#"))
                            emp = emp.replace("#", "m")

                        if (emp.contains("$"))
                            emp = emp.replace("$", "m")

                        if (emp.contains("["))
                            emp = emp.replace("[", "m")

                        if (emp.contains("]"))
                            emp = emp.replace("]", "m")
                        emailandpass.empid = emp
                        Toast.makeText(this, "${emailandpass.usename}", Toast.LENGTH_SHORT).show()
                        Log.e("msg", emailandpass.compani!!)
                        Log.e("msg", emailandpass.pass!!)
                        Log.e("msg", emailandpass.phone!!)
                        Log.e("msg", emailandpass.jobtype!!)
                        Log.e("msg", emailandpass.empid!!)

                        startActivity(Intent(this, MainActivity::class.java).apply {
                            setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

                        })

                        finish()
                    }
                }
            }
        }


    }

    fun accountdelete() {
        val user = FirebaseAuth.getInstance().currentUser
        val credential = EmailAuthProvider.getCredential(emailandpass.email!!, emailandpass.pass!!)
        user?.reauthenticate(credential)?.addOnCompleteListener {
            user.delete().addOnCompleteListener { task ->
                auth.signOut();
                if (task.isSuccessful) {
                    Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show()

                }


            }

        }?.addOnFailureListener {
            Toast.makeText(this, "${it.message} ${it.cause}", Toast.LENGTH_SHORT).show()
        }
    }

    fun backani() {
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

    fun currentdate() {
        val localdate = LocalDate.now()
        val formater = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val date = localdate.format(formater)
        emailandpass.today = date
    }
    fun checkforpermision()
    {
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.POST_NOTIFICATIONS)!=PackageManager.PERMISSION_GRANTED&&
            ContextCompat.checkSelfPermission(this,android.Manifest.permission.FOREGROUND_SERVICE)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.POST_NOTIFICATIONS,android.Manifest.permission.FOREGROUND_SERVICE),100)

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==100)
        {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

            }
            else
            {
                Toast.makeText(this, "we need permision :(", Toast.LENGTH_SHORT).show()
            }
        }
    }
}