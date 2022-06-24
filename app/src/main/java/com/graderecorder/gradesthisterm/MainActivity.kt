package com.graderecorder.gradesthisterm

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var list:ArrayList<DataModels>?= ArrayList()
    private var rec:RecyclerView? = null

    private var dbDAO:DbDAO?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.green));
        }


        val topbar=findViewById<Toolbar>(R.id.topbar)
        setSupportActionBar(topbar)

        rec=findViewById(R.id.recyclerviewMain)
        rec?.layoutManager= LinearLayoutManager(applicationContext)
        rec?.setHasFixedSize(false)

        GlobalScope.launch {
            dbDAO = Db?.getInstance(applicationContext).dbDAO()
            list = dbDAO?.getAllData() as ArrayList<DataModels>




            MainScope().launch {
                val  adapter= RecyclerViewAdapter(applicationContext, list!!)

                rec?.adapter=adapter
                adapter.notifyDataSetChanged()



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

                val i = Intent(this@MainActivity,NewCourse::class.java)
                startActivity(i)
            }

            R.id.aboutUs -> {


            }

            R.id.deleteAll ->{
                val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this@MainActivity)

                builder.setMessage("Do you want to delete All record?")
                builder.setTitle("Alert !")
                builder.setCancelable(false)
                builder
                    .setPositiveButton(
                        "Yes"
                    ) { dialog, which ->

                        GlobalScope.launch {
                            val databaseDAO = Db?.getInstance(this@MainActivity.applicationContext).dbDAO()
                            val dataModels= DataModels()
                            databaseDAO?.deleteAll()
                            recreate()
                        }

                        Toast.makeText(this@MainActivity,"All Record Deleted", Toast.LENGTH_SHORT).show()

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

            }
        }
        return true
    }

}