package com.example.jobshaduler.Activity

import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animation.LIB.AnimationKC
import com.example.jobshaduler.R
import com.example.jobshaduler.adopterclass.todays.tData
import com.example.jobshaduler.adopterclass.todays.upcoming.Tadopter
import com.example.jobshaduler.adopterclass.todays.upcoming.Uadopter
import com.example.jobshaduler.adopterclass.upcoming.udata
import com.example.jobshaduler.databinding.ActivityMainBinding
import com.example.task.classes.Cnave
import com.example.task.classes.Nave
import com.example.task.classes.naveobj
import com.example.task.classes.state

class MainActivity : AppCompatActivity() {
    lateinit var b: ActivityMainBinding
    lateinit var tdata: ArrayList<tData>
    lateinit var udata: ArrayList<udata>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        val ani = AnimationKC(this)
        nonavigatation()
        nave()
        ani.AnimationStater(b.headerimage, ani.long_toup_with_fadeout)
        ani.AnimationStater(b.title, ani.long_toup_with_fadeout)
        ani.AnimationStater(b.headernotibutton, ani.long_toup_with_fadeout)

        ani.AnimationStater(b.card, ani.long_toleft)
        ani.AnimationStater(b.todaystaskrelativelayout, ani.long_toleft)
        ani.AnimationStater(b.upcomingtaskrelativelayout, ani.long_toleft)
        ani.AnimationStater(b.navigatation, ani.long_toup)

        tdata()
        udata()
        b.todayrecycle.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        b.todayrecycle.adapter = Tadopter(tdata, this)
        b.upcomingrecycler.layoutManager = LinearLayoutManager(this)
        b.upcomingrecycler.adapter = Uadopter(udata, this)
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
                    startActivity(
                        Intent(
                            this,
                            Add::class.java
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

    fun tdata() {
        tdata = ArrayList<tData>()
        for (i in 1..10) {
            tdata.add(tData(R.drawable.dp, "task number $i", 5 * i))
        }
    }

    fun udata() {
        udata = ArrayList<udata>()
        for (i in 1..10) {
            udata.add(udata("task number $i", "$i 0/02/24"))
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
}