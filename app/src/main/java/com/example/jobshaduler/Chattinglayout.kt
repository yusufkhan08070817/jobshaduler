package com.example.jobshaduler

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobshaduler.Activity.MainActivity2
import com.example.jobshaduler.adopterclass.msgadopter.chattingAdopter.mychateadopter
import com.example.jobshaduler.classes.dataclass.Message
import com.example.jobshaduler.classes.singleton.emailandpass
import com.example.jobshaduler.classes.singleton.refs
import com.example.jobshaduler.databinding.ActivityChattinglayoutBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService

class Chattinglayout : AppCompatActivity() {
    lateinit var b: ActivityChattinglayoutBinding
    lateinit var msg: ArrayList<Message>

    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityChattinglayoutBinding.inflate(layoutInflater)
        setContentView(b.root)
        val recycler = b.recyclerView
        msg = ArrayList<Message>()
b.secondperson.text=refs.chattingref
        database = FirebaseDatabase.getInstance().reference
        recycler.layoutManager = LinearLayoutManager(this);
        b.sendButton.setOnClickListener {
            msg.add(Message(b.messageInput.text.toString(), "",emailandpass.empid!!, refs.chattingref))
            firebase(msg)
            b.messageInput.text=Editable.Factory.getInstance().newEditable("")
        }

        setupDatabaseListener()

        b.audiocall.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }
        b.videocall.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }


        // Set a listener for changes in the database


        videoCallServices(emailandpass.empid!!)


    }

    fun firebase(chattingdata: ArrayList<Message>) {
        Firebase.database.reference.child("employ/${refs.chattingref}/chatting/${emailandpass.empid}")
            .setValue(chattingdata).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "send", Toast.LENGTH_SHORT).show()


                }
            }.addOnFailureListener {
                Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
            }

    }
    private fun setupDatabaseListener() {
        database.child("employ/${emailandpass.empid}/chatting/${refs.chattingref}").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear the old list
                msg.clear()

                // Iterate through all children in the snapshot
                for (dataSnapshot in snapshot.children) {
                    val message = dataSnapshot.getValue(Message::class.java)
                    if (message != null) {
                        msg.add(message)
                    }
                }
                b.recyclerView.adapter=mychateadopter(msg)

                // Notify adapter or handle the updated list
                // For example, if you are using a RecyclerView, notify the adapter here
                // recyclerView.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
                error.toException().printStackTrace()
            }
        })
    }

    private fun videoCallServices(userID: String) {
        val appID: Long = 1232378385 // your App ID of Zoge Cloud Project
        val appSign =
            "ca2395df49aa00f574fa36961dd9c09b73585e0014cd6a50700a5d8570283a9d" // your App Sign of Zoge Cloud Project
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


}