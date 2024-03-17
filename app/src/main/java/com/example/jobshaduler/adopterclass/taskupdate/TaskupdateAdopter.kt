package com.example.jobshaduler.adopterclass.taskupdate

import android.annotation.SuppressLint
import android.util.Log
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.jobshaduler.R
import com.example.jobshaduler.classes.dataclass.YourDataClass
import com.example.jobshaduler.classes.singleton.currenttask
import com.example.jobshaduler.classes.singleton.emailandpass
import com.google.firebase.Firebase
import com.google.firebase.database.database

class TaskupdateAdopter(val Data:YourDataClass,val Checked:checked):RecyclerView.Adapter<TaskupdateAdopter.V>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskupdateAdopter.V {
     val layout=LayoutInflater.from(parent.context).inflate(R.layout.subtask,parent,false)
        return V(layout)
    }


    override fun onBindViewHolder(holder: TaskupdateAdopter.V, position: Int) {
        val currunt=Data.subtask[position]
        holder.subtaskes.text=currunt


    }

    override fun getItemCount(): Int {
        return Data.subtask.size
    }
    inner class V(v:View):RecyclerView.ViewHolder(v){
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        val completed:Switch=v.findViewById(R.id.completed)
        val subtaskes:TextView=v.findViewById(R.id.subtaskes)
        init {
            completed.setOnCheckedChangeListener { buttonView, isChecked ->

Checked.checked(isChecked,Data,Data.subtask.size)

            }
        }
    }
    interface checked{ fun checked(isclick:Boolean,DataList: YourDataClass,Datasize:Int)}
}