package com.example.jobshaduler.adopterclass.taskupdate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jobshaduler.R

class TaskupdateAdopter(val Data:ArrayList<String>):RecyclerView.Adapter<TaskupdateAdopter.V>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskupdateAdopter.V {
      val lay=LayoutInflater.from(parent.context).inflate(R.layout.subtask,parent,false)
        return V(lay)
    }

    override fun onBindViewHolder(holder: TaskupdateAdopter.V, position: Int) {
        
    }

    override fun getItemCount(): Int {
        return Data.size
    }
    inner class V(v:View):RecyclerView.ViewHolder(v){
        
    }
}