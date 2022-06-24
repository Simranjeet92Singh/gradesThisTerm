package com.graderecorder.gradesthisterm

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewCourse : AppCompatActivity() {

    private var c_no: EditText?=null
    private var t_grades: EditText?=null
    private var g_rec: EditText?=null
    private var type: Spinner?=null
    private var addButton: Button?=null
    private var dataModels: DataModels?=null
    private var dB: Db?=null
    private var dbDAO:DbDAO?=null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_course)

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.green));
        }
        val topbar=findViewById<Toolbar>(R.id.topbar)
        setSupportActionBar(topbar)

        c_no = findViewById(R.id.c_no)
          t_grades=findViewById(R.id.t_grades)
          g_rec=findViewById(R.id.g_rec)
          type=findViewById(R.id.type)
          addButton=findViewById(R.id.buttonAdd)
          dataModels=DataModels()
        
        addButton?.setOnClickListener{
            if(c_no?.text.toString()==""){
                Toast.makeText(this@NewCourse,"Course Number is empty.", Toast.LENGTH_SHORT).show()
            } else if(type?.selectedItem?.toString()=="Select Item"){
                Toast.makeText(this@NewCourse,"Grade Type is empty.", Toast.LENGTH_SHORT).show()

            }else if(t_grades?.text.toString()==""){
                Toast.makeText(this@NewCourse,"Total Grades is empty.", Toast.LENGTH_SHORT).show()

            }else if(g_rec?.text.toString()==""){
                Toast.makeText(this@NewCourse,"Grades Recieved is empty.", Toast.LENGTH_SHORT).show()

            }else {
                if(t_grades?.text.toString().toDouble() >= 0.0 && t_grades?.text.toString().toDouble()<=100.0 && g_rec?.text.toString().toDouble() >= 0.0 && g_rec?.text.toString().toDouble()<=100.0 && g_rec?.text.toString().toDouble()<=g_rec?.text.toString().toDouble())
                    {
                        GlobalScope.launch {

                            dataModels?.key = 0
                            dataModels?.courseNumber = c_no?.text.toString()

                            dataModels?.totalGrades = t_grades?.text.toString().toDouble()
                            dataModels?.gradesReceived=g_rec?.text.toString().toDouble()

                            dataModels?.gradeType = type?.selectedItem?.toString()

                            dbDAO = Db?.getInstance(applicationContext).dbDAO()
                            dbDAO?.addData(dataModels)



                        }


                        Toast.makeText(applicationContext,"Record Save",Toast.LENGTH_SHORT).show()

                        t_grades?.text?.clear()
                        c_no?.text?.clear()
                        g_rec?.text?.clear()
                        type?.setSelection(0)


                    }

                else {
                    Toast.makeText(applicationContext,"Total Grades and Grade Recieved is between 0 and 100 Or Grade Recieved is less than Total Grades ", Toast.LENGTH_SHORT).show()


                } 



                    }
                }
            
            
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_bar_menu,menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.add ->{

                val i = Intent(this@NewCourse,NewCourse::class.java)
                startActivity(i)
            }

            R.id.aboutUs -> {
                val i = Intent(this@NewCourse,AboutUs::class.java)
                startActivity(i)

            }

            R.id.deleteAll ->{
                val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this@NewCourse)

                builder.setMessage("Do you want to delete All record?")
                builder.setTitle("Alert !")
                builder.setCancelable(false)
                builder
                    .setPositiveButton(
                        "Yes"
                    ) { dialog, which ->

                        GlobalScope.launch {
                            val databaseDAO = Db?.getInstance(this@NewCourse.applicationContext).dbDAO()

                            databaseDAO?.deleteAll()

                        }
                        recreate()
                        Toast.makeText(this@NewCourse,"All Record Deleted", Toast.LENGTH_SHORT).show()

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

            R.id.overView->{
                val i = Intent(this@NewCourse,OverView::class.java)
                startActivity(i)

            }
        }
        return true
    }

    override fun onBackPressed() {
        val i= Intent(this@NewCourse,MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity( i)

    }
}

