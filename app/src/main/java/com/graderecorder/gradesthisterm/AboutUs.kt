package com.graderecorder.gradesthisterm

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AboutUs: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_us)


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.green));
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_bar_menu,menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.add ->{

                val i = Intent(this@AboutUs,NewCourse::class.java)
                startActivity(i)
            }

            R.id.aboutUs -> {
                val i = Intent(this@AboutUs,AboutUs::class.java)
                startActivity(i)

            }

            R.id.deleteAll ->{
                val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this@AboutUs)

                builder.setMessage("Do you want to delete All record?")
                builder.setTitle("Alert !")
                builder.setCancelable(false)
                builder
                    .setPositiveButton(
                        "Yes"
                    ) { dialog, which ->

                        GlobalScope.launch {
                            val databaseDAO = Db?.getInstance(this@AboutUs.applicationContext).dbDAO()

                            databaseDAO?.deleteAll()

                        }
                        recreate()
                        Toast.makeText(this@AboutUs,"All Record Deleted", Toast.LENGTH_SHORT).show()

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
                val i = Intent(this@AboutUs,OverView::class.java)
                startActivity(i)

            }
        }
        return true
    }
    }