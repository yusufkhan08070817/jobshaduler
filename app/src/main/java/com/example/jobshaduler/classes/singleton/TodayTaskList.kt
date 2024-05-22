package com.example.jobshaduler.classes.singleton

import com.example.jobshaduler.adopterclass.todays.tData
import com.example.jobshaduler.classes.dataclass.currenttaskwithstatus

object TodayTaskList {
  var  tdata= ArrayList<tData>()
  var currenttask=ArrayList<currenttaskwithstatus>()
}