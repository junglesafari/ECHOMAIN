package com.example.martin.echo.activities

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.example.martin.echo.R
import com.example.martin.echo.R.id.navigation_recycler_view
import com.example.martin.echo.activities.MainActivity.Statified.drawerLayout
import com.example.martin.echo.adapters.NavigationDrawerAdapter
import com.example.martin.echo.fragments.MainScreen
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    var images_for_navdrawer = intArrayOf(R.drawable.navigation_allsongs,R.drawable.navigation_favorites
    ,R.drawable.navigation_settings,R.drawable.navigation_aboutus)
var navigationDrawerIconList:ArrayList<String> = arrayListOf()

   object Statified{
    var drawerLayout:DrawerLayout?=null
   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         val toolbar=findViewById<android.support.v7.widget.Toolbar>(R.id.toolbar)
                 setSupportActionBar(toolbar)
        navigationDrawerIconList.add("All songs")
        navigationDrawerIconList.add("Favourites ")
        navigationDrawerIconList.add("Settings")
        navigationDrawerIconList.add("About Us")
             MainActivity.Statified.drawerLayout=findViewById(R.id.drawer_layout)
        var toggle=ActionBarDrawerToggle(this@MainActivity,MainActivity.Statified.drawerLayout
                ,toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        MainActivity.Statified.drawerLayout?.setDrawerListener(toggle)
        toggle.syncState()
        var mainScreenFragment= MainScreen()
        this.supportFragmentManager
                .beginTransaction()
                .add(R.id.details_fragment,mainScreenFragment,"MainScreenFragment")
                .commit()
        var _navigationAdaptor =NavigationDrawerAdapter(navigationDrawerIconList,images_for_navdrawer,this)
        _navigationAdaptor.notifyDataSetChanged()
        var recyclerview=findViewById<RecyclerView>(R.id.navigation_recycler_view)
        navigation_recycler_view.layoutManager=LinearLayoutManager(this)
        navigation_recycler_view.itemAnimator=DefaultItemAnimator()
        navigation_recycler_view.adapter=_navigationAdaptor
        navigation_recycler_view.setHasFixedSize(true)
    }

    override fun onStart() {
        super.onStart()
    }
}