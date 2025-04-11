package com.example.todo_app

import Todo
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.squareup.picasso.Picasso

class TodoDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_detail)

        val todo = intent.getParcelableExtra<Todo>("todo")

        val titleTextView: TextView = findViewById(R.id.detailTitleTextView)
        val completedTextView: TextView = findViewById(R.id.detailCompletedTextView)
        val userIdTextView: TextView = findViewById(R.id.detailUserIdTextView)
        val idTextView: TextView = findViewById(R.id.detailIdTextView)
        val imageView: ImageView = findViewById(R.id.detailImageView)

        if (todo != null) {
            titleTextView.text = "Title: ${todo.title}"
            completedTextView.text = "Completed: ${todo.completed}"
            userIdTextView.text = "User ID: ${todo.userId}"
            idTextView.text = "ID: ${todo.id}"

            Picasso.get()
                .load("https://via.placeholder.com/300")
                .into(imageView, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        Log.d("Picasso", "Image loaded successfully")
                    }

                    override fun onError(e: Exception?) {
                        Log.e("Picasso", "Image load failed: ${e?.message}")
                    }
                })
        } else {
            Log.e("TodoDetailActivity", "Todo object is null")
            //Handle the null todo object, perhaps by finishing the activity.
            finish()
        }
    }
}