package com.example.jobshaduler.Activity
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import com.example.jobshaduler.R
import com.example.jobshaduler.VideoCallActivity
import com.example.jobshaduler.classes.singleton.emailandpass
import com.example.jobshaduler.classes.singleton.refs
import com.example.jobshaduler.databinding.ActivityMain2Binding
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService

class MainActivity2 : AppCompatActivity() {
lateinit var b:ActivityMain2Binding
    private lateinit var userIdTextField: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b= ActivityMain2Binding.inflate(layoutInflater)
        setContentView(b.root)

        // binding the ids to their respective elements
        userIdTextField =  b.userIdTextField
        userIdTextField.text = Editable.Factory.getInstance().newEditable(emailandpass.empid!!)

            b.buttonNext.setOnClickListener {





        }
        val intent = Intent(this@MainActivity2, VideoCallActivity::class.java)
        intent.putExtra("userID", refs.chattingref)
        startActivity(intent)

        videoCallServices(emailandpass.empid!!)

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