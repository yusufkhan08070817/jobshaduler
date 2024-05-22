package com.example.jobshaduler.adopterclass.ADD

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobshaduler.R

class ADDsubtask(val data:ArrayList<String>):RecyclerView.Adapter<ADDsubtask.inner>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ADDsubtask.inner {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.subtaskshower,parent,false)
        return inner(view)
    }

    override fun onBindViewHolder(holder: ADDsubtask.inner, position: Int) {
       val current=data[position]
        holder.text.text=current
    }

    override fun getItemCount(): Int {
     return data.size
    }
    class inner (val view:View):RecyclerView.ViewHolder(view)
    {
        val text:TextView=view.findViewById(R.id.subtaskshowertaskes)
    }
}