package com.example.jobshaduler.Activity

import android.app.ActivityManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.animation.LIB.AnimationKC
import com.example.jobshaduler.R
import com.example.jobshaduler.adopterclass.todays.tData
import com.example.jobshaduler.adopterclass.todays.upcoming.Tadopter
import com.example.jobshaduler.adopterclass.todays.upcoming.Uadopter
import com.example.jobshaduler.adopterclass.upcoming.udata
import com.example.jobshaduler.classes.dataclass.YourDataClass
import com.example.jobshaduler.classes.dataclass.currenttaskwithstatus
import com.example.jobshaduler.classes.service.ForgroundServices
import com.example.jobshaduler.classes.singleton.TodayTaskList
import com.example.jobshaduler.classes.singleton.emailandpass
import com.example.jobshaduler.classes.singleton.ty
import com.example.jobshaduler.classes.singleton.Upcomingtask
import com.example.jobshaduler.classes.singleton.refs
import com.example.jobshaduler.databinding.ActivityMainBinding
import com.example.task.classes.Cnave
import com.example.task.classes.Nave
import com.example.task.classes.naveobj
import com.example.task.classes.state
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService

class MainActivity : AppCompatActivity(),Tadopter.todaytaskclick {
    lateinit var b: ActivityMainBinding

    lateinit var udata: ArrayList<udata>
    lateinit var currenttask: List<currenttaskwithstatus>
    lateinit var nextdata: List<YourDataClass>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        b.headertitle.text = emailandpass.usename
        b.headersubtitle.text = emailandpass.today
        val ani = AnimationKC(this)

        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA),234)
        }

        nonavigatation()
        nave()
        ani.AnimationStater(b.headerimage, ani.long_toup_with_fadeout)
        ani.AnimationStater(b.title, ani.long_toup_with_fadeout)
        ani.AnimationStater(b.headernotibutton, ani.long_toup_with_fadeout)

        ani.AnimationStater(b.card, ani.long_toleft)
        ani.AnimationStater(b.todaystaskrelativelayout, ani.long_toleft)
        ani.AnimationStater(b.upcomingtaskrelativelayout, ani.long_toleft)
        ani.AnimationStater(b.navigatation, ani.long_toup)

        //  b.headerdp.setImageURI(emailandpass.image)
        Glide.with(this).load(emailandpass.image).into(b.headerdp)
        b.headerdp.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))
        }
        val isRunning = isServiceRunning(this, ForgroundServices::class.java)
        if (!isRunning) {
            val serviceIntent = Intent(this, ForgroundServices::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent)
            } else {
                startService(serviceIntent)
            }

        }else
        {
            val serviceIntent = Intent(this, ForgroundServices::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent)
            } else {
                startService(serviceIntent)
            }
        }

        fbget()
        nexttask()

        FirebaseDatabase.getInstance().getReference("employ/${emailandpass.empid}/notificatiton")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val data = snapshot.value
                        val bo = data.toString()
                        if (bo.toBoolean()) {
                            val tintColor = ContextCompat.getColor(this@MainActivity, R.color.Red1)

                            // Apply tint to the image
                            b.headernotibutton.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
                            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                            val vibrationEffect1: VibrationEffect

                            // this is the only type of the vibration which requires system version Oreo (API 26)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                                // this effect creates the vibration of default amplitude for 1000ms(1 sec)
                                vibrationEffect1 =
                                    VibrationEffect.createOneShot(2000, VibrationEffect.EFFECT_DOUBLE_CLICK)

                                // it is safe to cancel other vibrations currently taking place
                                vibrator.cancel()
                                vibrator.vibrate(vibrationEffect1)
                            }

                        }
                    } else Log.e("not get ", "ni mela ")

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(ContentValues.TAG, "Failed to read data: ${error.message}")
                }
            })
        b.headernotibutton.setOnClickListener {
            Firebase.database.reference.child("employ/${emailandpass.empid}/notificatiton")
                .setValue(false).addOnCompleteListener {
                    if (it.isSuccessful) {

                        val tintColor = ContextCompat.getColor(this@MainActivity, R.color.black)

                        // Apply tint to the image
                        b.headernotibutton.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
                    }
                }
        }

        state.state.observe(this) { t ->
            when (t) {
                1 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.home)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2), PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton1.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton5.setImageResource(R.drawable.chart)
                    naveobj.naveobj.imageButton2.setImageResource(R.drawable.clipboard)
                    naveobj.naveobj.imageButton3.setImageResource(R.drawable.add)
                    naveobj.naveobj.imageButton4.setImageResource(R.drawable.chat)
                }

                2 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.clipboard)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2), PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton2.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton1.setImageResource(R.drawable.home)
                    naveobj.naveobj.imageButton5.setImageResource(R.drawable.chart)
                    naveobj.naveobj.imageButton3.setImageResource(R.drawable.add)
                    naveobj.naveobj.imageButton4.setImageResource(R.drawable.chat)
                    startActivity(Intent(
                        this, task::class.java
                    ).apply {  })
                }

                3 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.add)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2), PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton3.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton1.setImageResource(R.drawable.home)
                    naveobj.naveobj.imageButton2.setImageResource(R.drawable.clipboard)
                    naveobj.naveobj.imageButton5.setImageResource(R.drawable.chart)
                    naveobj.naveobj.imageButton4.setImageResource(R.drawable.chat)

                    ty {
                        val jobtypr = emailandpass.jobtype
                        if (jobtypr == "employ") {
                            startActivity(Intent(this, taskupdate::class.java))
                        } else {
                            startActivity(Intent(this, Add::class.java))
                        }
                    }

                }

                4 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.chat)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2), PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton4.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton1.setImageResource(R.drawable.home)
                    naveobj.naveobj.imageButton2.setImageResource(R.drawable.clipboard)
                    naveobj.naveobj.imageButton3.setImageResource(R.drawable.add)
                    naveobj.naveobj.imageButton5.setImageResource(R.drawable.chart)
                    startActivity(Intent(
                        this, MSG::class.java
                    ).apply {  })

                }

                5 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.chart)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2), PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton5.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton1.setImageResource(R.drawable.home)
                    naveobj.naveobj.imageButton2.setImageResource(R.drawable.clipboard)
                    naveobj.naveobj.imageButton3.setImageResource(R.drawable.add)
                    naveobj.naveobj.imageButton4.setImageResource(R.drawable.chat)
                    startActivity(Intent(
                        this, Ana::class.java
                    ).apply {  })
                }
            }
        }
        b.headerdp.setOnClickListener {
            startActivity(Intent(this, Profile::class.java).apply {

            })
        }
        Toast.makeText(this, "${emailandpass.empid!!}", Toast.LENGTH_SHORT).show()
        videoCallServices(emailandpass.empid!!)
    }

    fun nave() {
        Cnave(this)
        Nave.getInstance(this.applicationContext)
        Nave.add(b.navigatation)
        naveobj.naveobj.imageButton1.setImageResource(R.drawable.home)
        naveobj.naveobj.imageButton2.setImageResource(R.drawable.clipboard)
        naveobj.naveobj.imageButton3.setImageResource(R.drawable.add)
        naveobj.naveobj.imageButton4.setImageResource(R.drawable.chat)
        naveobj.naveobj.imageButton5.setImageResource(R.drawable.chart)
    }


    override fun onBackPressed() {
        if (0 == 2) super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        Cnave(this)
        Nave.getInstance(this.applicationContext)
        Nave.add(b.navigatation)
    }

    fun nonavigatation() {
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        val decorView = window.decorView
        val uiOptions =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        decorView.systemUiVisibility = uiOptions
    }

    fun fbget() {
        TodayTaskList.tdata = ArrayList<tData>()
        Firebase.database.reference.child("employ/${emailandpass.empid}/currenttask").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val dataMap = task.result.value as? Map<String, Any>

                    dataMap?.let { map ->
                        currenttask = map.map { entry ->
                            val key = entry.key
                            val value = entry.value as Map<String, Any>

                            Log.d("FirebaseData", "Task: $key, Value: $value")

                            currenttaskwithstatus(
                                date = value["date"] as? String ?: "",
                                description = value["description"] as? String ?: "",
                                resourceLink = value["resourceLink"] as? String ?: "",
                                teamChoose = value["teamchoose"] as? List<String> ?: emptyList(),
                                subtask = value["subtask"] as? List<String> ?: emptyList(),
                                title = value["title"] as? String ?: "",
                                percentage = (value["percentage"] as? Number)?.toFloat() ?: 0f
                            )
                        }

                        val tempdata = ArrayList<currenttaskwithstatus>()
                        TodayTaskList.currenttask.clear()
                        for (data in currenttask) {
                            TodayTaskList.currenttask.add(data)

                            // Access individual properties of the YourDataClass instance
                            println("cDate: ${data.date}")
                            println("cDescription: ${data.description}")
                            println("cResource Link: ${data.resourceLink}")
                            println("cTeam Choose: ${data.teamChoose}")
                            println("cSubtask: ${data.subtask}")
                            println("cTitle: ${data.title}")
                            println("cPercent: ${data.percentage}")
if (data.percentage<99)
{

}
                            tempdata.add(data)

                            if (data.percentage > 50) {
                                b.taskcardprogressbarpercentagr.text = data.percentage.toString()
                                b.taskcardprogressBar.progress = data.percentage.toInt()
                                b.cardrelatvetext.text = "Your ${data.title} task is almost done."
                            } else {
                                b.taskcardprogressbarpercentagr.text = data.percentage.toString()
                                b.taskcardprogressBar.progress = data.percentage.toInt()
                                b.cardrelatvetext.text = "Your ${data.title} task has not been started yet."
                            }
                            TodayTaskList.tdata.add(tData(R.drawable.dp, data.title, data.percentage.toInt()))
                        }

                        Log.e("TempData", "$tempdata")
                        b.todayrecycle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                        b.todayrecycle.adapter = Tadopter(tempdata, this, this)
                    }
                } else {
                    Log.e("FirebaseError", "Error getting data", task.exception)
                }
            }

    }

    fun nexttask() {
        Upcomingtask.upcomingTask.clear()
        Firebase.database.reference.child("employ/${emailandpass.empid}/assignedtask").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val dataMap =
                        it.result.value as? Map<String, Map<String, Any>> // Assuming the data is a Map

                    dataMap?.let { map ->
                        nextdata = map.map { entry ->
                            val key = entry.key
                            val value = entry.value
                            YourDataClass(
                                date = value["date"] as? String ?: "",
                                description = value["descripatation"] as? String ?: "",
                                resourceLink = value["resorcelink"] as? String ?: "",
                                teamChoose = value["teamchoose"] as? List<String> ?: emptyList(),
                                subtask = value["subtask"] as? List<String> ?: emptyList(),
                                title = value["title"] as? String ?: "",
                                totaltask = value["totle"] as?Int?: 0
                            )
                        }

                        // Use dataList as needed
                        val titleString = ArrayList<Pair<String, String>>()
                        udata = ArrayList<udata>()
                        for (data in nextdata) {
                            // Access individual properties of the YourDataClass instance
                            println("Date: ${data.date}")
                            println("Description: ${data.description}")
                            println("Resource Link: ${data.resourceLink}")
                            println("Team Choose: ${data.teamChoose}")
                            println("Subtask: ${data.subtask}")
                            println("Title: ${data.title}")

                            Upcomingtask.upcomingTask.add(udata(data.title, data.date))
                        }
                        b.upcomingrecycler.layoutManager = LinearLayoutManager(this)
                        b.upcomingrecycler.adapter = Uadopter(Upcomingtask.upcomingTask, this)
                    }

                }
            }
    }
    fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
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
    override fun totayclick(position: Int) {
        refs.todayclickposition=position
        startActivity(Intent(this,Taskclick::class.java).apply {
            Intent.FLAG_ACTIVITY_NO_HISTORY
        })

    }

}