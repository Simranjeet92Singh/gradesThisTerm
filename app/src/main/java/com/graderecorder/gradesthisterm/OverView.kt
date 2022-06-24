package com.graderecorder.gradesthisterm

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat

class OverView: AppCompatActivity() {

    private var rec:RecyclerView?=null
    var overViewModel:ArrayList<OverViewModel>?=ArrayList()
    var dataModels:ArrayList<DataModels>?= ArrayList()
    private var dbDAO: DbDAO?=null

    var x : Double = 0.0
    var y :Double =0.0
    var p : Double = 0.0
    var q :Double =0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.over_view)
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.green));
        }

        rec=findViewById(R.id.rec_overview)
        rec?.layoutManager = LinearLayoutManager(applicationContext)
        rec?.setHasFixedSize(false)

        GlobalScope.launch {
            dbDAO = Db?.getInstance(applicationContext).dbDAO()
            dataModels = dbDAO!!.getAllData() as ArrayList<DataModels>

            for(  j in 0..getcourseName(dataModels!!)?.size!!-1){
                overViewModel?.add(
                    OverViewModel(getcourseName(dataModels!!)?.get(j).toString(),getgradesSubmitted(dataModels!!).get(j)
                    ,getgradestoDate(dataModels!!).get(j))
                )


            }

            MainScope().launch {
                val adapter = OverViewAdapter(applicationContext, overViewModel)

                rec?.adapter = adapter
                adapter.notifyDataSetChanged()

            }
        }


    }
    fun getcourseName(mlist:ArrayList<DataModels>):List<String>?{

        var newList:ArrayList<String>?=ArrayList()
        var newListOne:List<String>?=ArrayList()
        for(position in 0..mlist.size-1){
            newList?.add(mlist.get(position).courseNumber.toString())
        }
        newListOne= newList?.distinct()
        Log.d("New list",newListOne.toString())
        return newListOne

    }

    fun getgradesSubmitted( mlist:ArrayList<DataModels>):ArrayList<Double>{

        var n : ArrayList<Double> = ArrayList()



        for(  j in 0..getcourseName(mlist)?.size!!-1){

            var p : Double = 0.0
            var q :Double =0.0

            for(i in 0..mlist.size-1){
                if(mlist.get(i).courseNumber == getcourseName(mlist)?.get(j).toString()){
                    p=p+mlist.get(i).gradesReceived!!
                    q=q+mlist.get(i).totalGrades!!

                }else{
                    p=p+0
                    q=q+0
                }

            }
            n.add(roundOffDecimal((p/q)*100))
        }

        return n
    }

    fun getgradestoDate(mlist:ArrayList<DataModels>):ArrayList<Double>{
        var n : ArrayList<Double> = ArrayList()
        for(  j in 0..getcourseName(mlist)?.size!!-1) {
            for (i in 0..mlist.size - 1) {
                x = x + mlist.get(i).gradesReceived!!
                y = y + mlist.get(i).totalGrades!!
            }
            n.add(roundOffDecimal((x/y)*100))
        }


        return n
    }
    fun roundOffDecimal(number: Double): Double{
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toDouble()
    }

}