package com.example.jobshaduler.Activity

import android.content.Intent
import android.graphics.Color
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
import com.example.bargraph.classes.Creategraph
import com.example.bargraph.classes.graphdata
import com.example.jobshaduler.R
import com.example.jobshaduler.adopterclass.Anaadopter.AnaAdopter
import com.example.jobshaduler.adopterclass.task.taskadopter
import com.example.jobshaduler.adopterclass.todays.tData
import com.example.jobshaduler.adopterclass.todays.upcoming.Uadopter
import com.example.jobshaduler.adopterclass.upcoming.udata
import com.example.jobshaduler.classes.dataclass.DatelistCount
import com.example.jobshaduler.classes.dataclass.YourDataClass
import com.example.jobshaduler.classes.dataclass.currenttaskwithstatus
import com.example.jobshaduler.classes.singleton.Upcomingtask
import com.example.jobshaduler.classes.singleton.emailandpass
import com.example.jobshaduler.databinding.ActivityAnaBinding
import com.example.task.classes.Cnave
import com.example.task.classes.Nave
import com.example.task.classes.naveobj
import com.example.task.classes.state
import com.google.firebase.Firebase
import com.google.firebase.database.database

class Ana : AppCompatActivity() {
    lateinit var b: ActivityAnaBinding
    lateinit var anadata: ArrayList<tData>
    lateinit var totle: List<currenttaskwithstatus>
    lateinit var complete: List<currenttaskwithstatus>
    val taskdata = ArrayList<currenttaskwithstatus>()
    var count:Int =0
    var premont=0
    val DatelistCount=ArrayList<DatelistCount>()
    val bardata = ArrayList<graphdata>()
    val month = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAnaBinding.inflate(layoutInflater)
        setContentView(b.root)
        nonavigatation()
        complete = emptyList()
        totle = emptyList()
        val ani = AnimationKC(this)
        ani.AnimationStater(b.anaheaderrelativelayout, ani.long_toleft)
        ani.AnimationStater(b.anapov, ani.long_toright)
        ani.AnimationStater(b.anaps, ani.long_toleft)
        ani.AnimationStater(b.anagraphlay, ani.long_toright)
        ani.AnimationStater(b.graphcolor, ani.long_toleft)
        anadata()

        b.anarecycle.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val bar = b.anagraph

        month.add("JAN"); month.add("FEB"); month.add("MAR"); month.add("APR"); month.add("MAY"); month.add(
            "JUN"
        ); month.add("JAN"); month.add("JULY"); month.add("AUG"); month.add("SEP"); month.add("NOV"); month.add(
            "DEC"
        );






        // b.anaheaderdp.setImageURI(emailandpass.image)
        Glide.with(this).load(emailandpass.image).into(b.anaheaderdp)
        b.anaheaderdp.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))
        }

        Nave.add(b.ananavigatation)
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
                        ).apply { })
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
                        ).apply { })
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
                    if (emailandpass.jobtype == "employ") {
                        startActivity(
                            Intent(
                                this,
                                taskupdate::class.java
                            ).apply { })
                    } else {
                        startActivity(
                            Intent(
                                this,
                                Add::class.java
                            ).apply { })
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
                    startActivity(
                        Intent(
                            this,
                            MSG::class.java
                        ).apply { })
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
                }
            }
        }
        totle()
    }

    fun anadata() {
        anadata = ArrayList<tData>()

        for (i in 0 until 12) {
            anadata.add(tData(R.drawable.dp, "month[i]", 40 + 5 * i))
        }
    }

    fun setinitent(cls: Class<*>): Intent {
        val intent = Intent(this, cls::class.java)

        return intent
    }

    override fun onBackPressed() {
        if (0 == 2)
            super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        Cnave(this)
        Nave.getInstance(this.applicationContext)
        Nave.add(b.ananavigatation)
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

    fun totle() {
        taskdata.clear()
        Firebase.database.reference.child("employ/${emailandpass.empid}/completed")
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
                                    description = value["description"] as? String ?: "",
                                    resourceLink = value["resourceLink"] as? String ?: "",
                                    teamChoose = value["teamchoose"] as? List<String>
                                        ?: emptyList(),
                                    subtask = value["subtask"] as? List<String> ?: emptyList(),
                                    title = value["title"] as? String ?: "",
                                    percentage = (value["percentage"] as? Number)?.toFloat() ?: 0f
                                )
                            }

                            // Use dataList as needed

                            for (data in complete) {
                                // Access individual properties of the YourDataClass instance
                                println("cDate: ${data.date}")
                                println("cDescription: ${data.description}")
                                println("cResource Link: ${data.resourceLink}")
                                println("cTeam Choose: ${data.teamChoose}")
                                println("cSubtask: ${data.subtask}")
                                println("cTitle: ${data.title}")
                                println("cpercent:${data.percentage}")
                                Log.e("tasksample", "${data}")
                                taskdata.add(
                                    data
                                )

                                b.anarecycle.adapter = AnaAdopter(taskdata)
                            }


                        }

                    } else {
                        it.addOnFailureListener {
                            Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

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
                                description = value["description"] as? String ?: "",
                                resourceLink = value["resourceLink"] as? String ?: "",
                                teamChoose = value["teamchoose"] as? List<String> ?: emptyList(),
                                subtask = value["subtask"] as? List<String> ?: emptyList(),
                                title = value["title"] as? String ?: "",
                                percentage = (value["percentage"] as? Number)?.toFloat() ?: 0f
                            )
                        }

                        // Use dataList as needed

                        for (data in totle) {
                            // Access individual properties of the YourDataClass instance
                            println("cDate: ${data.date}")
                            println("cDescription: ${data.description}")
                            println("cResource Link: ${data.resourceLink}")
                            println("cTeam Choose: ${data.teamChoose}")
                            println("cSubtask: ${data.subtask}")
                            println("cTitle: ${data.title}")
                            println("cpercent:${data.percentage}")

                            taskdata.add(
                                data
                            )
                        }
                        b.anarecycle.adapter = AnaAdopter(taskdata)

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
                                description = value["description"] as? String ?: "",
                                resourceLink = value["resourceLink"] as? String ?: "",
                                teamChoose = value["teamchoose"] as? List<String> ?: emptyList(),
                                subtask = value["subtask"] as? List<String> ?: emptyList(),
                                title = value["title"] as? String ?: "",
                                percentage = (value["percentage"] as? Number)?.toFloat() ?: 0f
                            )
                        }

                        // Use dataList as needed

                        for (data in totle) {
                            // Access individual properties of the YourDataClass instance
                            println("cDate: ${data.date}")
                            println("cDescription: ${data.description}")
                            println("cResource Link: ${data.resourceLink}")
                            println("cTeam Choose: ${data.teamChoose}")
                            println("cSubtask: ${data.subtask}")
                            println("cTitle: ${data.title}")
                            println("cpercent:${data.percentage}")

                            taskdata.add(
                                data
                            )
                        }
                        b.anarecycle.adapter = AnaAdopter(taskdata)
                        for ( d in taskdata)
                        {

                            val date =  d.date

                            // Find the position of the second space
                            val secondSpaceIndex = date.indexOf(' ', date.indexOf(' ') + 1)

                            // Extract the month part
                            val month = date.substring(date.indexOf(' ') + 1, secondSpaceIndex).trim().toInt()

                            if (premont!=month)
                            {
                                DatelistCount.add((DatelistCount(count,month)))

                                Log.e("datemonthcount","month $month and count ${count + 1}")
                                premont=month
                                count=0

                            }else
                            {
                                count++

                            }
                        }
                        var unshorteddate=aggregateMonthCounts(DatelistCount)

                        for (data in unshorteddate) {
                            println("Month ${data.month}: ${data.count}")
                        }
                        for (i in 0 until month.size) {

                            bardata.add(graphdata(unshorteddate[i].count*10, month[i]))
                        }
                        Creategraph(
                            this,
                            b.anagraph,
                            bardata,
                            80f,
                            getColor(R.color.card2),
                            Graph_background = false,
                            alternativecolor = true,
                            color2 = getColor(R.color.card1)
                        )

                    }

                }
            }

        b.anarecycle.adapter = AnaAdopter(taskdata)



    }
    fun aggregateMonthCounts(dataList: List<DatelistCount>): ArrayList<DatelistCount> {
        // Create a map to store the counts for each month
        val monthCountsMap = mutableMapOf<Int, Int>()

        // Iterate through the dataList and aggregate the counts
        for (data in dataList) {
            val existingCount = monthCountsMap.getOrDefault(data.month, 0)
            monthCountsMap[data.month] = existingCount + data.count
        }

        // Include months with zero count
        val allMonths = (1..12).toList() // Assuming months range from 1 to 12
        for (month in allMonths) {
            monthCountsMap.putIfAbsent(month, 0)
        }

        // Convert the map to a list of DatelistCount objects
        val result = ArrayList<DatelistCount>()
        for ((month, count) in monthCountsMap) {
            result.add(DatelistCount(count, month))
        }

        // Sort the list by month in ascending order
        result.sortBy { it.month }

        return result
    }
}