package org.eu5.githubclient

import android.content.Intent
import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.eu5.githubclient.model.HttpClient
import org.eu5.githubclient.model.Item

var login = ""
var repos = ""
var id = ""
var followers = ""
var ifOffline = false
var tag = "______NB: "
var flnm = "githubaccount"
var gitName = "asanovrus"

var url = "https://api.github.com/users"
var urlfull = ""
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        BSubmit.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v:View?){
                gitName = ETCity.text.toString()
                talkServer()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       when (item.itemId) {
            R.id.action_settings -> showSettings()
            R.id.chartItem -> showChartActivity()
        }
        return true
    }

    fun showSettings(){
        var intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun showChartActivity(){
        var intent = Intent(this, OrientationActivity::class.java)
        startActivity(intent)
    }

    fun talkServer(){
        GlobalScope.launch{ startAction() }
    }

    @UiThread
    fun startAction(){
        var mclient = HttpClient(flnm)
        urlfull = url + "/" + gitName
        mclient.get(this, true, urlfull)
    }


    /**
     * after callback
     */
    fun updateView(){
        //getting recyclerview from xml
        val recyclerView = findViewById(R.id.RV) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        //crating an arraylist to store data in class
        val itms = ArrayList<Item>()

        //adding data to the list
        itms.add(Item("login", login))
        itms.add(Item("id", id))
        itms.add(Item("Repos", repos))
        itms.add(Item("Followers", followers))

        //creating adapter
        val adapter = CustomAdapter(itms)

        //add the adapter to recyclerview
        recyclerView.adapter = adapter
    }
}