package com.mozark.todoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() , RecylerViewAdapter.onClickListener{
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Bind Recycler View
        recyclerView = findViewById<RecyclerView>(R.id.rv_users)

        var addUsers = findViewById<FloatingActionButton>(R.id.add_users)
        addUsers.setOnClickListener(View.OnClickListener {
            Toast.makeText(this,"To be Done later on",Toast.LENGTH_LONG).show()
        })
        //Fetch the Data from the server
        getData()
    }

    fun getData(){
        val retrofit = Retrofit.Builder() .baseUrl("https://randomuser.me").addConverterFactory(GsonConverterFactory.create()).build()
        val userService = retrofit.create(UserService::class.java)
        val job = CoroutineScope(Dispatchers.IO).launch {
            val response = userService.allUsers.execute()
            Log.d("MA", "getData: response"+response.body())
            lateinit var adapter: RecylerViewAdapter
            if (response.isSuccessful) {
                //TODO:Create an adapter using coroutine call
                val body = response.body()
                CoroutineScope(Dispatchers.Main).launch{
                    adapter = RecylerViewAdapter(body,applicationContext,this@MainActivity)
                    recyclerView?.adapter = adapter
                    recyclerView.layoutManager =
                        LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false);
                    recyclerView.invalidate()
                    adapter.notifyDataSetChanged()
                }
            } else {
                Log.d("MainActivity", "getData: No Data from the server")
            }
        }

    }

    override fun onClick(position: Int) {
        val adapter = recyclerView.adapter as? RecylerViewAdapter
        val result = adapter?.response?.results?.get(position)
        var intent = Intent(this,DetailActivity::class.java)
        intent.putExtra("data",result)
        startActivity(intent)

    }
}