package com.example.jobshaduler.classes.singleton

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService

object refs {
    var todattaask:String=""
    var videoid:String=""
    var audioid:String=""
    var screenshearid:String=""
    var updatgetask:String=""
   var docRef= FirebaseFirestore.getInstance().collection(emailandpass.compani!!).document("employlist")
    var todayclickposition:Int=0
    var chattingref:String=""

     fun videoCallServices(userID: String,application:Application) {
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
}