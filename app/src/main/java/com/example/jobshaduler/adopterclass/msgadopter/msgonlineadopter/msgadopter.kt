package com.example.jobshaduler.adopterclass.msgadopter.msgonlineadopter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jobshaduler.R
class msgadopter():RecyclerView.Adapter<msgadopter.view>() {
    class view(v:View):RecyclerView.ViewHolder(v){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): view {
        val lay=LayoutInflater.from(parent.context).inflate(R.layout.members,parent,false)
       return view(lay)
    }

    override fun getItemCount(): Int {
      return 10
    }

    override fun onBindViewHolder(holder: view, position: Int) {

    }
}