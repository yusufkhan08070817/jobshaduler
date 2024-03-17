package com.example.jobshaduler.classes.singleton

import com.example.jobshaduler.adopterclass.task.TaskData
import com.example.jobshaduler.classes.dataclass.YourDataClass
import com.example.jobshaduler.classes.dataclass.currenttaskwithstatus

object currenttask {
    val currenttask=ArrayList<currenttaskwithstatus>()
    var status:Int=0
}