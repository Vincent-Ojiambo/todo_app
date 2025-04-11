//package com.example.todo_app
//
//import Todo
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class TodoAdapter(val todos: MutableList<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
//
//    class TodoViewHolder(itemView: View, private val adapter: TodoAdapter) : RecyclerView.ViewHolder(itemView) { //Added adapter parameter
//        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
//        val completedTextView: TextView = itemView.findViewById(R.id.completedTextView)
//        val userIdTextView: TextView = itemView.findViewById(R.id.userIdTextView)
//        val idTextView: TextView = itemView.findViewById(R.id.idTextView)
//
//        init {
//            itemView.setOnClickListener {
//                if (adapterPosition != RecyclerView.NO_POSITION) {
//                    val todo = adapter.todos[adapterPosition] //Correctly access todos from the adapter.
//                    val intent = Intent(itemView.context, TodoDetailActivity::class.java)
//                    intent.putExtra("todo", todo)
//                    itemView.context.startActivity(intent)
//                }
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_card, parent, false)
//        return TodoViewHolder(view, this) //Pass the adapter.
//    }
//
//    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
//        val todo = todos[position]
//        holder.titleTextView.text = holder.itemView.context.getString(R.string.title_prefix, todo.title)
//        holder.completedTextView.text = holder.itemView.context.getString(R.string.completed_prefix, todo.completed)
//        holder.userIdTextView.text = holder.itemView.context.getString(R.string.user_id_prefix, todo.userId)
//        holder.idTextView.text = holder.itemView.context.getString(R.string.id_prefix, todo.id)
//    }
//
//    override fun getItemCount(): Int {
//        return todos.size
//    }
//}

package com.example.todo_app

import Todo
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(val todos: MutableList<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() { //Correct Class Declaration.

    class TodoViewHolder(itemView: View, private val adapter: TodoAdapter) : RecyclerView.ViewHolder(itemView) {
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

    override fun getItemCount(): Int { //Correct Method Name.
        return todos.size
    }
}