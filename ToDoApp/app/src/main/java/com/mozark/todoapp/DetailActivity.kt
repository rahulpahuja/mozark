package com.mozark.todoapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.mozark.todoapp.databinding.ActivityDetailBinding
import com.mozark.todoapp.model.Result
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_detail)
        val data = intent.getSerializableExtra("data") as? Result
        Toast.makeText(this,"Hello From Detail Activity",Toast.LENGTH_LONG).show()
        val binding: ActivityDetailBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_detail)
        binding.user = data

  //      val imageView = findViewById<ImageView>(R.id.iv_users)
    //    val url = data?.picture?.medium
//        Picasso.get().load(url).into(imageView)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}