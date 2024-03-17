package com.example.jobshaduler.Activity

import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.animation.LIB.AnimationKC
import com.example.jobshaduler.R
import com.example.jobshaduler.adopterclass.task.TaskData
import com.example.jobshaduler.adopterclass.task.taskadopter
import com.example.jobshaduler.adopterclass.todays.tData
import com.example.jobshaduler.adopterclass.todays.upcoming.Tadopter
import com.example.jobshaduler.classes.dataclass.currenttaskwithstatus
import com.example.jobshaduler.classes.singleton.currenttask
import com.example.jobshaduler.classes.singleton.emailandpass
import com.example.jobshaduler.databinding.ActivityTaskBinding
import com.example.task.classes.Cnave
import com.example.task.classes.Nave
import com.example.task.classes.naveobj
import com.example.task.classes.state
import com.google.firebase.Firebase
import com.google.firebase.database.database

class task : AppCompatActivity() {
    lateinit var b: ActivityTaskBinding
    lateinit var currenttask: List<currenttaskwithstatus>
    lateinit var complete: List<currenttaskwithstatus>
    lateinit var totle: List<currenttaskwithstatus>
    val taskdata = ArrayList<TaskData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(b.root)
        nonavigatation()
        clickchange()

        val ani = AnimationKC(this)
        ani.AnimationStater(b.taskheaderrelativelayout, ani.long_toleft)
        ani.AnimationStater(b.all, ani.long_toleft)
        ani.AnimationStater(b.ongoing, ani.long_toup)
        ani.AnimationStater(b.complete, ani.long_toright)

        //  b.anaheaderdp.setImageURI(emailandpass.image)
        Glide.with(this).load(emailandpass.image).into(b.anaheaderdp)
        b.anaheaderdp.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))
        }


        Nave.add(b.navigatation)
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
                    startActivity(
                        Intent(
                            this,
                            if (emailandpass.jobtype == "admin") Add::class.java else taskupdate::class.java
                        ).apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) })
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
                    startActivity(
                        Intent(
                            this,
                            MSG::class.java
                        ).apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) })

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
    }

    fun setinitent(cls: Class<*>): Intent {
        val intent = Intent(this, cls::class.java)

        return intent
    }

    fun clickchange() {
        b.all.setOnClickListener {
            b.all.setCardBackgroundColor(getColor(R.color.card2))
            b.ongoing.setCardBackgroundColor(getColor(R.color.white))
            b.complete.setCardBackgroundColor(getColor(R.color.white))
totle()
        }
        b.ongoing.setOnClickListener {
            b.all.setCardBackgroundColor(getColor(R.color.white))
            b.complete.setCardBackgroundColor(getColor(R.color.white))
            b.ongoing.setCardBackgroundColor(getColor(R.color.card2))
            fbget()

        }
        b.complete.setOnClickListener {
            b.complete.setCardBackgroundColor(getColor(R.color.card2))
            b.ongoing.setCardBackgroundColor(getColor(R.color.white))
            b.all.setCardBackgroundColor(getColor(R.color.white))
            complete()

        }

    }

    override fun onBackPressed() {
        if (0 == 2)
            super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        Cnave(this)
        Nave.getInstance(this.applicationContext)
        Nave.add(b.navigatation)
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

    fun fbget() {
        Firebase.database.reference.child("employ/${emailandpass.empid}/currenttask")
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val dataMap =
                        it.result.value as? Map<String, Map<String, Any>> // Assuming the data is a Map

                    dataMap?.let { map ->
                        currenttask = map.map { entry ->
                            val key = entry.key
                            val value = entry.value
                            currenttaskwithstatus(
                                date = value["date"] as? String ?: "",
                                description = value["descripatation"] as? String ?: "",
                                resourceLink = value["resorcelink"] as? String ?: "",
                                teamChoose = value["teamchoose"] as? List<String> ?: emptyList(),
                                subtask = value["subtask"] as? List<String> ?: emptyList(),
                                title = value["title"] as? String ?: "",
                                percentage = value["percentage"] as? Float ?: 0f
                            )
                        }

                        // Use dataList as needed

                        for (data in currenttask) {
                            // Access individual properties of the YourDataClass instance
                            println("cDate: ${data.date}")
                            println("cDescription: ${data.description}")
                            println("cResource Link: ${data.resourceLink}")
                            println("cTeam Choose: ${data.teamChoose}")
                            println("cSubtask: ${data.subtask}")
                            println("cTitle: ${data.title}")
                            println("cpercent:${data.percentage}")

                            taskdata.add(
                                TaskData(
                                    "project ${data.title}",
                                    if (data.percentage < 50) "ongoing" else "running",
                                    data.percentage.toInt(),
                                    "09 am ",
                                    data.subtask.size
                                )
                            )
                        }
                        b.taskrecycler.layoutManager = LinearLayoutManager(this)
                        b.taskrecycler.adapter = taskadopter(taskdata, this)

                    }

                }
            }
    }

    fun complete() {
        Firebase.database.reference.child("employ/${emailandpass.empid}/complete")
            .get().addOnCompleteListener {
                if (it.result != null) {
                    if (it.isSuccessful) {
                        val dataMap =
                            it.result.value as? Map<String, Map<String, Any>> // Assuming the data is a Map

                        dataMap?.let { map ->
                            complete = map.map { entry ->
                                val key = entry.key
                                val value = entry.value
                                currenttaskwithstatus(
                                    date = value["date"] as? String ?: "",
                                    description = value["descripatation"] as? String ?: "",
                                    resourceLink = value["resorcelink"] as? String ?: "",
                                    teamChoose = value["teamchoose"] as? List<String>
                                        ?: emptyList(),
                                    subtask = value["subtask"] as? List<String> ?: emptyList(),
                                    title = value["title"] as? String ?: "",
                                    percentage = value["percentage"] as? Float ?: 0f
                                )
                            }

                            // Use dataList as needed

                            for (data in currenttask) {
                                // Access individual properties of the YourDataClass instance
                                println("cDate: ${data.date}")
                                println("cDescription: ${data.description}")
                                println("cResource Link: ${data.resourceLink}")
                                println("cTeam Choose: ${data.teamChoose}")
                                println("cSubtask: ${data.subtask}")
                                println("cTitle: ${data.title}")
                                println("cpercent:${data.percentage}")

                                taskdata.add(
                                    TaskData(
                                        "project ${data.title}",
                                        if (data.percentage < 50) "ongoing" else "running",
                                        data.percentage.toInt(),
                                        "09 am ",
                                        data.subtask.size
                                    )
                                )
                            }
                            b.taskrecycler.layoutManager = LinearLayoutManager(this)
                            b.taskrecycler.adapter = taskadopter(taskdata, this)

                        }

                    }
                }
            }
    }

    fun totle() {
        Firebase.database.reference.child("employ/${emailandpass.empid}/currenttask")
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val dataMap =
                        it.result.value as? Map<String, Map<String, Any>> // Assuming the data is a Map

                    dataMap?.let { map ->
                        totle = map.map { entry ->
                            val key = entry.key
                            val value = entry.value
                            currenttaskwithstatus(
                                date = value["date"] as? String ?: "",
                                description = value["descripatation"] as? String ?: "",
                                resourceLink = value["resorcelink"] as? String ?: "",
                                teamChoose = value["teamchoose"] as? List<String> ?: emptyList(),
                                subtask = value["subtask"] as? List<String> ?: emptyList(),
                                title = value["title"] as? String ?: "",
                                percentage = value["percentage"] as? Float ?: 0f
                            )
                        }

                        // Use dataList as needed

                        for (data in currenttask) {
                            // Access individual properties of the YourDataClass instance
                            println("cDate: ${data.date}")
                            println("cDescription: ${data.description}")
                            println("cResource Link: ${data.resourceLink}")
                            println("cTeam Choose: ${data.teamChoose}")
                            println("cSubtask: ${data.subtask}")
                            println("cTitle: ${data.title}")
                            println("cpercent:${data.percentage}")

                            taskdata.add(
                                TaskData(
                                    "project ${data.title}",
                                    if (data.percentage < 50) "ongoing" else "running",
                                    data.percentage.toInt(),
                                    "09 am ",
                                    data.subtask.size
                                )
                            )
                        }
                        b.taskrecycler.layoutManager = LinearLayoutManager(this)
                        b.taskrecycler.adapter = taskadopter(taskdata, this)

                    }

                }
            }
        Firebase.database.reference.child("employ/${emailandpass.empid}/assignedtask")
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val dataMap =
                        it.result.value as? Map<String, Map<String, Any>> // Assuming the data is a Map

                    dataMap?.let { map ->
                        totle = map.map { entry ->
                            val key = entry.key
                            val value = entry.value
                            currenttaskwithstatus(
                                date = value["date"] as? String ?: "",
                                description = value["descripatation"] as? String ?: "",
                                resourceLink = value["resorcelink"] as? String ?: "",
                                teamChoose = value["teamchoose"] as? List<String> ?: emptyList(),
                                subtask = value["subtask"] as? List<String> ?: emptyList(),
                                title = value["title"] as? String ?: "",
                                percentage = value["percentage"] as? Float ?: 0f
                            )
                        }

                        // Use dataList as needed

                        for (data in currenttask) {
                            // Access individual properties of the YourDataClass instance
                            println("cDate: ${data.date}")
                            println("cDescription: ${data.description}")
                            println("cResource Link: ${data.resourceLink}")
                            println("cTeam Choose: ${data.teamChoose}")
                            println("cSubtask: ${data.subtask}")
                            println("cTitle: ${data.title}")
                            println("cpercent:${data.percentage}")

                            taskdata.add(
                                TaskData(
                                    "project ${data.title}",
                                    if (data.percentage < 50) "ongoing" else "running",
                                    data.percentage.toInt(),
                                    "09 am ",
                                    data.subtask.size
                                )
                            )
                        }
                        b.taskrecycler.layoutManager = LinearLayoutManager(this)
                        b.taskrecycler.adapter = taskadopter(taskdata, this)

                    }

                }
            }
    }
}