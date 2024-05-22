package com.example.jobshaduler.Activity

import android.app.Application
import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.animation.LIB.AnimationKC
import com.example.jobshaduler.Chattinglayout
import com.example.jobshaduler.R
import com.example.jobshaduler.VideoCallActivity
import com.example.jobshaduler.adopterclass.msgadopter.chattingAdopter.chattingadopter
import com.example.jobshaduler.adopterclass.msgadopter.msgonlineadopter.msgadopter
import com.example.jobshaduler.classes.singleton.emailandpass
import com.example.jobshaduler.classes.singleton.refs
import com.example.jobshaduler.databinding.ActivityMsgBinding
import com.example.searchandchips.library.dataclass.Data
import com.example.task.classes.Cnave
import com.example.task.classes.Nave
import com.example.task.classes.naveobj
import com.example.task.classes.state
import com.google.firebase.firestore.FirebaseFirestore
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallConfig
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallFragment
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService

class MSG : AppCompatActivity(),chattingadopter.chattingclick {
    lateinit var b: ActivityMsgBinding
    lateinit var emparray:ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMsgBinding.inflate(layoutInflater)
        setContentView(b.root)
        nonavigatation()
        Nave.add(b.msgnave)
        emparray=ArrayList<String>()

        b.onlineStatus.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        b.onlineStatus.adapter = msgadopter()
        b.recchatting.layoutManager = LinearLayoutManager(this)

       // b.msgheaderdp.setImageURI(emailandpass.image)
        Glide.with(this).load(emailandpass.image).into(b.msgheaderdp)
        b.msgheaderdp.setOnClickListener {
            startActivity(Intent(this,Profile::class.java))
        }
        val ani = AnimationKC(this)
        ani.AnimationStater(b.msgheaderrelativelayout, ani.long_toleft)
        ani.AnimationStater(b.sercahcard, ani.long_toright)
        ani.AnimationStater(b.onlinetext, ani.long_toleft)
        state.state.observe(this) { t ->
            when (t) {
                1 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.home)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2),
                        PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton1.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton5.setImageResource(R.drawable.chart)
                    naveobj.naveobj.imageButton2.setImageResource(R.drawable.clipboard)
                    naveobj.naveobj.imageButton3.setImageResource(R.drawable.add)
                    naveobj.naveobj.imageButton4.setImageResource(R.drawable.chat)
                    startActivity(
                        Intent(
                            this,
                            MainActivity::class.java
                        ).apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) })

                }

                2 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.clipboard)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2),
                        PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton2.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton1.setImageResource(R.drawable.home)
                    naveobj.naveobj.imageButton5.setImageResource(R.drawable.chart)
                    naveobj.naveobj.imageButton3.setImageResource(R.drawable.add)
                    naveobj.naveobj.imageButton4.setImageResource(R.drawable.chat)
                    startActivity(
                        Intent(
                            this,
                            task::class.java
                        ).apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) })
                }

                3 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.add)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2),
                        PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton3.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton1.setImageResource(R.drawable.home)
                    naveobj.naveobj.imageButton2.setImageResource(R.drawable.clipboard)
                    naveobj.naveobj.imageButton5.setImageResource(R.drawable.chart)
                    naveobj.naveobj.imageButton4.setImageResource(R.drawable.chat)
                    if (emailandpass.jobtype=="employ")
                    {
                        startActivity(
                            Intent(
                                this,
                                taskupdate::class.java
                            ).apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) })
                    }else
                    {
                        startActivity(
                            Intent(
                                this,
                                Add::class.java
                            ).apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) })
                    }
                }

                4 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.chat)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2),
                        PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton4.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton1.setImageResource(R.drawable.home)
                    naveobj.naveobj.imageButton2.setImageResource(R.drawable.clipboard)
                    naveobj.naveobj.imageButton3.setImageResource(R.drawable.add)
                    naveobj.naveobj.imageButton5.setImageResource(R.drawable.chart)

                }

                5 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.chart)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2),
                        PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton5.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton1.setImageResource(R.drawable.home)
                    naveobj.naveobj.imageButton2.setImageResource(R.drawable.clipboard)
                    naveobj.naveobj.imageButton3.setImageResource(R.drawable.add)
                    naveobj.naveobj.imageButton4.setImageResource(R.drawable.chat)
                    startActivity(
                        Intent(
                            this,
                            Ana::class.java
                        ).apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) })
                }
            }
        }

        getemploylist()
        b.recchatting.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        b.recchatting.adapter = chattingadopter(emparray,this)


    }
fun getemploylist()
{
    val docRef = FirebaseFirestore.getInstance().collection(emailandpass.compani!!).document("employlist")
    docRef.get().addOnSuccessListener { documentSnapshot ->
        if (documentSnapshot.exists()) {
            val arrayList = documentSnapshot.get("employ_list")
            if (arrayList != null) {
                if (arrayList is List<*>) {
                   emparray = arrayList as ArrayList<String>
                    // Loop through the string array elements
                    for (item in emparray) {
                        Log.e("elements", item)
                        Toast.makeText(this, "$item", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Handle unexpected data type
                }

                b.recchatting.adapter = chattingadopter(emparray,this)
                println("ArrayList from Firestore:")

            } else {
                // ArrayList is null or not of expected type
                println("Failed to retrieve ArrayList from Firestore")
            }
        } else {
            // Document doesn't exist
            println("Document does not exist")
        }
    }.addOnFailureListener { e ->
        // Handle failures
        println("Error fetching document: $e")
    }
}
    override fun onResume() {
        super.onResume()
        Cnave(this)
        Nave.getInstance(this.applicationContext)
        Nave.add(b.msgnave)
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

    override fun chaattingclickposition(position: Int) {




        val intent = Intent(this, Chattinglayout::class.java)
refs.chattingref=emparray[position]
        intent.putExtra("userID", emparray[position])
        videoCallServices(emailandpass.empid!!)
        startActivity(intent)


    }

    private fun videoCallServices(userID: String) {
        val appID: Long = 1232378385 // your App ID of Zoge Cloud Project
        val appSign = "ca2395df49aa00f574fa36961dd9c09b73585e0014cd6a50700a5d8570283a9d" // your App Sign of Zoge Cloud Project
        val application = application // Android's application context
        val callInvitationConfig = ZegoUIKitPrebuiltCallInvitationConfig()
        //    callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true
        val notificationConfig = ZegoNotificationConfig()
        notificationConfig.sound = "zego_uikit_sound_call"
        notificationConfig.channelID = "CallInvitation"
        notificationConfig.channelName = "CallInvitation"
        ZegoUIKitPrebuiltCallInvitationService.init(
            application,
            appID,
            appSign,
            userID,
            userID,
            callInvitationConfig
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        ZegoUIKitPrebuiltCallInvitationService.unInit()
    }
}