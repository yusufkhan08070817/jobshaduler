package com.example.jobshaduler.adopterclass.msgadopter.chattingAdopter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobshaduler.R
import com.example.jobshaduler.classes.dataclass.Message
import com.example.jobshaduler.classes.singleton.emailandpass

class mychateadopter(val data:ArrayList<Message>):RecyclerView.Adapter<mychateadopter.view>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mychateadopter.view {
    val layout=LayoutInflater.from(parent.context).inflate(R.layout.chatting,parent,false)
        return view(layout)
    }

    override fun onBindViewHolder(holder: mychateadopter.view, position: Int) {
        val current=data[position]
        if(current.sander==emailandpass.empid!!)
        {
            holder.fiesr.text=data[position].first.toString()
            holder.second.text=data[position].second.toString()
        }else
        {
            holder.second.text=data[position].first.toString()
            holder.fiesr.text=data[position].second.toString()
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
    class view(v:View):RecyclerView.ViewHolder(v)
    {

        val fiesr :TextView=v.findViewById(R.id.chatefirstperson)
        val second :TextView=v.findViewById(R.id.chatesecondperson)


    }
}