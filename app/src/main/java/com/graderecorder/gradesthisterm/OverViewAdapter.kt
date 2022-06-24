package com.graderecorder.gradesthisterm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OverViewAdapter (val context: Context, val list:List<OverViewModel>?) :
    RecyclerView.Adapter<OverViewAdapter.ItemViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverViewAdapter.ItemViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.over_view_list,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val model=list?.get(position)


        holder.tv_over?.text="For ${model?.cName.toString()} , grades submitted are ${model?.gradesSubmitted.toString()}% and grades to Date are ${model?.gradesToDate.toString()}% "


    }



    override fun getItemCount(): Int {
        return list!!.size
    }

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tv_over: TextView?=view?.findViewById(R.id.tv_overView)



    }


}