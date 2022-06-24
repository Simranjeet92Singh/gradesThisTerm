package com.graderecorder.gradesthisterm

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecyclerViewAdapter (val context: Context, val list:ArrayList<DataModels>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ItemViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.main_list,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val list=list.get(position)


        holder.courseName?.text=list.courseNumber
        holder.courseType?.text=list.gradeType
        holder.totalGrade?.text=list.totalGrades.toString()
        holder.obtGrades?.text=list.gradesReceived.toString()





        holder.itemView.setOnClickListener {

            val builder: AlertDialog.Builder = AlertDialog.Builder(context)

            builder.setMessage("Do you want to delete the record?")
            builder.setTitle("Alert !")
            builder.setCancelable(false)
            builder
                .setPositiveButton(
                    "Yes"
                ) { dialog, which ->

                    GlobalScope.launch {
                        val dbDAO = Db?.getInstance(context).dbDAO()

                        dbDAO?.delete(list)



                    }


                    Toast.makeText(context," Record Deleted", Toast.LENGTH_SHORT).show()
                    val i = Intent(context,MainActivity::class.java)
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(context,i,null)

                }

            builder
                .setNegativeButton(
                    "No",
                    DialogInterface.OnClickListener { dialog, which ->

                        dialog.cancel()
                    })

            val alertDialog = builder.create()


            alertDialog.show()
        }
    }



    override fun getItemCount(): Int {
        return list.size
    }

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val courseName: TextView?=view?.findViewById(R.id.c_no)
        val courseType: TextView?=view?.findViewById(R.id.type)
        val totalGrade: TextView?=view?.findViewById(R.id.t_grades)
        val obtGrades: TextView?=view?.findViewById(R.id.g_rec)

    }


}