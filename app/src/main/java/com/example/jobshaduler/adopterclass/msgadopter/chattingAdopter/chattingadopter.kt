package com.example.jobshaduler.adopterclass.msgadopter.chattingAdopter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobshaduler.R

class chattingadopter (val data:ArrayList<String>,val Chattingclick:chattingclick):RecyclerView.Adapter<chattingadopter.views>(){
  inner  class  views(v:View):RecyclerView.ViewHolder(v)
    {
val personname :TextView=v.findViewById(R.id.personname)
        val chattingDP:ImageButton=v.findViewById(R.id.chattingDP)
        val chattinglay:RelativeLayout=v.findViewById(R.id.chattinglay)
        init {
            chattinglay.setOnClickListener {
                Chattingclick.chaattingclickposition(position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): views {
        val lay=LayoutInflater.from(parent.context).inflate(R.layout.chates,parent,false)
        return views(lay)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: views, position: Int) {
val current=data[position]
        holder.personname.text=current
    }
    interface chattingclick{
        fun chaattingclickposition(position: Int)
    }
}