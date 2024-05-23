package com.example.jobshaduler.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobshaduler.adopterclass.taskupdate.TaskupdateAdopter
import com.example.jobshaduler.classes.dataclass.YourDataClass
import com.example.jobshaduler.classes.dataclass.currenttaskwithstatus
import com.example.jobshaduler.classes.singleton.TodayTaskList
import com.example.jobshaduler.classes.singleton.currenttask
import com.example.jobshaduler.classes.singleton.emailandpass
import com.example.jobshaduler.classes.singleton.refs
import com.example.jobshaduler.databinding.ActivityTaskclickBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Taskclick : AppCompatActivity(), TaskupdateAdopter.checked {

    lateinit var b: ActivityTaskclickBinding
    var count = 0
    var percent = 0f
    lateinit var datamap: Map<String, Any>
    lateinit var current: currenttaskwithstatus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityTaskclickBinding.inflate(layoutInflater)
        setContentView(b.root)

        val position = refs.todayclickposition
        val data = TodayTaskList.currenttask[position]
        val taskupdate = b.taskclicksubtask
        val sublist = data.subtask

        val yourDataClass = YourDataClass(
            data.date,
            description = data.description,
            resourceLink = data.resourceLink,
            teamChoose = data.teamChoose,
            data.subtask,
            title = data.title,
            totaltask = data.subtask.size
        )

        val adopter = TaskupdateAdopter(yourDataClass, this)
        b.taskclicktitle.text = data.title
        b.taskclickassigndate.text = data.date.toString().replace(" ", "-", false)
        b.todaydate.text = emailandpass.today
        b.resorcelink.text = data.resourceLink
        taskupdate.layoutManager = LinearLayoutManager(this)
        taskupdate.adapter = adopter

        // Fetching current task data from Firebase
        Firebase.database.reference.child("employ/${emailandpass.empid}/currenttask/${yourDataClass.title}")
            .get().addOnCompleteListener { it ->
            if (it.isSuccessful) {
                datamap = it.result.value as Map<String, Any>

                current = currenttaskwithstatus(
                    date = datamap["date"] as? String ?: "",
                    description = datamap["description"] as? String ?: "",
                    resourceLink = datamap["resourceLink"] as? String ?: "",
                    teamChoose = (datamap["teamChoose"] as? List<*>)?.filterIsInstance<String>()
                        ?: emptyList(),
                    subtask = (datamap["subtask"] as? List<*>)?.filterIsInstance<String>()
                        ?: emptyList(),
                    title = datamap["title"] as? String ?: "",
                    percentage = datamap["percentage"] as? Float ?: 0f
                )
                Log.e("fetch", "$current")
            } else {
                Log.e("fetch", "Failed to fetch data: ${it.exception?.message}")
            }
        }

        b.taskclicksubmit.setOnClickListener {
            val updatedTask = currenttaskwithstatus(
                date = data.date,
                description = data.description,
                resourceLink = data.resourceLink,
                teamChoose = data.teamChoose,
                subtask = data.subtask,
                title = data.title,
                percentage = percent
            )
            Log.e("tag", "$updatedTask")

            if (updatedTask.percentage < 99)
            {
                Firebase.database.reference.child("employ/${emailandpass.empid}/currenttask/${yourDataClass.title}")
                    .setValue(updatedTask).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(this, "Failed to update task", Toast.LENGTH_SHORT).show()
                            Log.e("update", "Failed to update data: ${it.exception?.message}")
                        }
                    }
            }else
            {
                Firebase.database.reference.child("employ/${emailandpass.empid}/completed/${yourDataClass.title}")
                    .setValue(updatedTask).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(this, "Failed to update task", Toast.LENGTH_SHORT).show()
                            Log.e("update", "Failed to update data: ${it.exception?.message}")
                        }
                    }

                removeData("employ/${emailandpass.empid}/currenttask/${yourDataClass.title}")
                val myToast = Toast.makeText(applicationContext,"Your task is completed",Toast.LENGTH_SHORT)
                myToast.setGravity(Gravity.LEFT,200,200)
                myToast.show()
                startActivity(Intent(this,MainActivity::class.java))
            }
        }
    }

    private fun removeData(path: String) {
        Firebase.database.reference.child(path).removeValue()
            .addOnSuccessListener {
                Log.e("remove", "Data removed successfully!")
            }
            .addOnFailureListener {
                Log.e("remove", "Failed to remove data: ${it.message}")
            }
    }

    override fun checked(isclick: Boolean, DataList: YourDataClass, Datasize: Int) {
        if (isclick) {
            count++
        } else {
            count--
        }

        val c = count.toFloat() / Datasize.toFloat()
        percent = c * 100f
        Toast.makeText(this, "$count ${Datasize} $c", Toast.LENGTH_SHORT).show()

        if (percent > 5) {
            currenttask.currenttask.add(
                currenttaskwithstatus(
                    date = DataList.date,
                    description = DataList.description,
                    resourceLink = DataList.resourceLink,
                    teamChoose = DataList.teamChoose,
                    subtask = DataList.subtask,
                    title = DataList.title,
                    percentage = percent
                )
            )
        }
    }
}
