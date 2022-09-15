package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    lateinit var adapter : TaskItemAdapter

    var listOfTask = mutableListOf<String>()
    val OnLongClickListener = object : TaskItemAdapter.OnLongClickListener{
        override fun onItemLongClicked(position: Int) {
            listOfTask.removeAt(position)

            adapter.notifyDataSetChanged()
            saveItems()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //detect when user clicks on add button

        //findViewById<Button>(R.id.button).setOnClickListener {
        //code that is executed on click
        // Log.i("Caren","User Clicked Button")
        //}

        loadItems()

        val recyclerView = findViewById<RecyclerView>(R.id.recView)
         adapter = TaskItemAdapter(listOfTask, OnLongClickListener)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputtedText = findViewById<EditText>(R.id.addTaskName)

        //set up button and input field
        findViewById<Button>(R.id.button).setOnClickListener {
            //code that is executed on click
            //grab user input text
            val userInputtedTask = inputtedText.text.toString()
            //add string to list of task
            listOfTask.add(userInputtedTask)

            adapter.notifyItemInserted(listOfTask.size - 1)

            inputtedText.setText("")

            saveItems()
        }
    }
    //save data
    fun getDataFile() : File{
        return File(filesDir, "data.text")
    }

    fun loadItems() {
        try {
            listOfTask = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

    fun saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), listOfTask)
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

}