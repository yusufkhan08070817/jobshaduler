package com.example.jobshaduler.adopterclass.Anaadopter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.jobshaduler.R
import com.example.jobshaduler.adopterclass.todays.tData
import com.example.jobshaduler.adopterclass.todays.upcoming.Tadopter
import com.example.jobshaduler.classes.dataclass.currenttaskwithstatus

class AnaAdopter(val data: ArrayList<currenttaskwithstatus>) : RecyclerView.Adapter<AnaAdopter.viewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.anarecyclelay, parent, false)
        return viewholder(layout)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val current = data[position]
        val colorResId = if (position % 2 == 0) R.color.card2 else R.color.card1
        val color = ContextCompat.getColor(holder.itemView.context, colorResId)
        holder.Card.setCardBackgroundColor(color)
        holder.prograssbar.progress=current.percentage.toInt()
        holder.percentage.text=current.percentage.toString()
        holder.title.text=current.title

    }

    class viewholder(v: View) : RecyclerView.ViewHolder(v) {
        val Card: CardView = v.findViewById(R.id.tlay)
        val backimage: ImageView = v.findViewById(R.id.tlayimage)
        val icon: ImageView = v.findViewById(R.id.tlayimageicon)
        val prograssbar: ProgressBar = v.findViewById(R.id.talyprogressBar2)
        val percentage: TextView =v.findViewById(R.id.tlaypercentage)
        val title: TextView =v.findViewById(R.id.tlaytitle)

    }
}