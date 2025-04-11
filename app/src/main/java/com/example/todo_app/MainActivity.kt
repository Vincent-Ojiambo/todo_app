package com.example.todo_app

import Todo
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL




class MainActivity : ComponentActivity() {

    private lateinit var todoRecyclerView: RecyclerView
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var todoIdEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        todoRecyclerView = findViewById(R.id.todoRecyclerView)
        todoIdEditText = findViewById(R.id.todoIdEditText)
        submitButton = findViewById(R.id.submitButton)
        backButton = findViewById(R.id.backButton)

        todoRecyclerView.layoutManager = LinearLayoutManager(this)
        todoAdapter = TodoAdapter(mutableListOf())
        todoRecyclerView.adapter = todoAdapter

        backButton.setOnClickListener {
            fetchAllTodos()
        }

        submitButton.setOnClickListener {
            val todoId = todoIdEditText.text.toString()
            if (todoId.isNotEmpty()) {
                fetchTodo(todoId.toInt())
            } else {
                Toast.makeText(this, R.string.please_enter_todo_id, Toast.LENGTH_SHORT).show()
            }
        }

        fetchAllTodos()
        backButton.visibility = Button.GONE
    }

    private fun fetchAllTodos() {
        backButton.visibility = Button.GONE
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL("https://jsonplaceholder.typicode.com/todos")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    withContext(Dispatchers.Main) {
                        try {
                            val jsonArray = JSONArray(response)
                            val todos = mutableListOf<Todo>()
                            for (i in 0 until jsonArray.length()) {
                                val jsonObject = jsonArray.getJSONObject(i)
                                val todo = Todo.fromJson(jsonObject)
                                todos.add(todo)
                            }
                            todoAdapter.todos.clear()
                            todoAdapter.todos.addAll(todos)
                            todoAdapter.notifyDataSetChanged()
                        } catch (e: JSONException) {
                            Log.e("MainActivity", "JSON parsing error: ${e.message}")
                            Toast.makeText(this@MainActivity, R.string.network_error, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Log.e("MainActivity", "HTTP Error: $responseCode")
                        Toast.makeText(this@MainActivity, R.string.network_error, Toast.LENGTH_SHORT).show()
                    }
                }
                connection.disconnect()
            } catch (e: MalformedURLException) {
                withContext(Dispatchers.Main) {
                    Log.e("MainActivity", "Malformed URL: ${e.message}")
                    Toast.makeText(this@MainActivity, R.string.network_error, Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    Log.e("MainActivity", "Network error: ${e.message}")
                    Toast.makeText(this@MainActivity, R.string.network_error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchTodo(todoId: Int) {
        backButton.visibility = Button.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL("https://jsonplaceholder.typicode.com/todos/$todoId")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    withContext(Dispatchers.Main) {
                        try {
                            val todo = Todo.fromJson(JSONObject(response))
                            todoAdapter.todos.clear()
                            todoAdapter.todos.add(todo)
                            todoAdapter.notifyDataSetChanged()
                        } catch (e: JSONException) {
                            Log.e("MainActivity", "JSON parsing error: ${e.message}")
                            Toast.makeText(this@MainActivity, R.string.network_error, Toast.LENGTH_SHORT).show()
                            fetchAllTodos()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, R.string.todo_not_found, Toast.LENGTH_SHORT).show()
                        fetchAllTodos()
                    }
                }
                connection.disconnect()
            } catch (e: MalformedURLException) {
                withContext(Dispatchers.Main) {
                    Log.e("MainActivity", "Malformed URL: ${e.message}")
                    Toast.makeText(this@MainActivity, R.string.network_error, Toast.LENGTH_SHORT).show()
                    fetchAllTodos()
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    Log.e("MainActivity", "Network error: ${e.message}")
                    Toast.makeText(this@MainActivity, R.string.network_error, Toast.LENGTH_SHORT).show()
                    fetchAllTodos()
                }
            }
        }
    }

    inner class TodoAdapter(val todos: MutableList<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

        inner class TodoViewHolder(itemView: View, private val adapter: TodoAdapter) : RecyclerView.ViewHolder(itemView) {
            val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
            val completedTextView: TextView = itemView.findViewById(R.id.completedTextView)
            val userIdTextView: TextView = itemView.findViewById(R.id.userIdTextView)
            val idTextView: TextView = itemView.findViewById(R.id.idTextView)

            init {
                itemView.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        val todo = adapter.todos[adapterPosition]
                        val intent = Intent(itemView.context, TodoDetailActivity::class.java)
                        intent.putExtra("todo", todo)
                        itemView.context.startActivity(intent)
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_card, parent, false)
            return TodoViewHolder(view, this)
        }

        override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
            val todo = todos[position]
            holder.titleTextView.text = holder.itemView.context.getString(R.string.title_prefix, todo.title)
            holder.completedTextView.text = holder.itemView.context.getString(R.string.completed_prefix, todo.completed)
            holder.userIdTextView.text = holder.itemView.context.getString(R.string.user_id_prefix, todo.userId)
            holder.idTextView.text = holder.itemView.context.getString(R.string.id_prefix, todo.id)
        }

        override fun getItemCount(): Int {
            return todos.size
        }
    }
}