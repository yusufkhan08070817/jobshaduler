package com.example.jobshaduler.adopterclass.task

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.animation.LIB.AnimationKC
import com.example.jobshaduler.R
import com.example.jobshaduler.classes.dataclass.currenttaskwithstatus

class taskadopter(val Data: ArrayList<currenttaskwithstatus>,val context:Context) : RecyclerView.Adapter<taskadopter.viewholde>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholde {
        return (viewholde(
            LayoutInflater.from(parent.context).inflate(R.layout.taskcard, parent, false)
        ))
    }

    override fun getItemCount(): Int {
        return Data.size
    }

    override fun onBindViewHolder(holder: viewholde, position: Int) {
        val currency = Data[position]
        val ani=AnimationKC(context)
        val setcolor = when (currency.percentage.toInt()) {
            in 0..50 -> {
                R.color.orang
            }
           in 50 .. 75->{
                R.color.card2
            }
           in 75..100->{
                R.color.card1
            }

            else -> {R.color.card1back}
        }
        val setprograss = when (currency.percentage.toInt()) {
            in 0..50 -> {
                ContextCompat.getDrawable(context,R.drawable.prograss1)
            }
            in 50 .. 75->{
                ContextCompat.getDrawable(context,R.drawable.prograssbar3)
            }
            in 75..100->{
                ContextCompat.getDrawable(context,R.drawable.prograssbar2)
            }

            else -> {ContextCompat.getDrawable(context,R.drawable.prograssbar)}
        }


        val setbuttoncolor: Drawable? = when (currency.percentage.toInt()) {
            in 0..50 -> {
                ContextCompat.getDrawable(context, R.drawable.three_dots_svgrepo_com)?.apply {
                    setColorFilter(
                        ContextCompat.getColor(context, R.color.orang),
                        PorterDuff.Mode.SRC_ATOP
                    )
                }
            }
            in 51..75 -> {
                ContextCompat.getDrawable(context, R.drawable.three_dots_svgrepo_com)?.apply {
                    setColorFilter(
                        ContextCompat.getColor(context, R.color.card2),
                        PorterDuff.Mode.SRC_ATOP
                    )
                }
            }
            in 76..100 -> {
                ContextCompat.getDrawable(context, R.drawable.three_dots_svgrepo_com)?.apply {
                    setColorFilter(
                        ContextCompat.getColor(context, R.color.card1),
                        PorterDuff.Mode.SRC_ATOP
                    )
                }
            }
            else -> {
                ContextCompat.getDrawable(context, R.drawable.three_dots_svgrepo_com)?.apply {
                    setColorFilter(
                        ContextCompat.getColor(context, R.color.black),
                        PorterDuff.Mode.SRC_ATOP
                    )
                }
            }
        }
        holder.teaskbutton.setImageDrawable(setbuttoncolor)
        holder.taskcardtitle.setTextColor(ContextCompat.getColor(context,setcolor))
     if (currency.percentage<50)
         holder.taskcardtitle.text="ongoing"
        else
         if (currency.percentage<80)
             holder.taskcardtitle.text="all most complete"
         else
             if (currency.percentage<=99)
                 holder.taskcardtitle.text="complete"
        else
                 holder.taskcardtitle.text="complete"
        holder.cardtitle.text=currency.title
        holder.cardtitle.setTextColor(setcolor)
        holder.timedhadule.text=currency.title
        holder.person.text=currency.teamChoose.size.toString()
        holder.cardprogressBar2.progress=currency.percentage.toInt()
        holder.cardprogressBar2.progressDrawable=setprograss
        holder.percentageofprograss.text=currency.percentage.toString()
        ani.AnimationStater(holder.taskcardlayout,ani.short_toleft)


    }

    class viewholde(v: View) : RecyclerView.ViewHolder(v) {
        val taskcardtitle: TextView = v.findViewById(R.id.taskcardtitle)
        val cardtitle: TextView = v.findViewById(R.id.cardtitle)
        val timedhadule: TextView = v.findViewById(R.id.timedhadule)
        val person: TextView = v.findViewById(R.id.person)
        val cardprogressBar2: ProgressBar = v.findViewById(R.id.cardprogressBar2)
        val percentageofprograss: TextView = v.findViewById(R.id.percentageofprograss)
        val teaskbutton:ImageButton=v.findViewById(R.id.teaskbutton)
        val taskcardlayout:RelativeLayout=v.findViewById(R.id.taskcardlayout)
    }
}