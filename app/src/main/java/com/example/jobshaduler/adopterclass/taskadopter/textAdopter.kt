package com.example.jobshaduler.adopterclass.taskupdate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jobshaduler.R

class textAdopter(val Data:ArrayList<String>):RecyclerView.Adapter<textAdopter.V>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): textAdopter.V {
        val lay=LayoutInflater.from(parent.context).inflate(R.layout.subtask,parent,false)
        return V(lay)
    }

    override fun onBindViewHolder(holder: textAdopter.V, position: Int) {

    }

    override fun getItemCount(): Int {
        return Data.size
    }
    inner class V(v:View):RecyclerView.ViewHolder(v){

    }
}