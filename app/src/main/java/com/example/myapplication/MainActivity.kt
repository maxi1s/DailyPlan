package com.example.myapplication
import android.content.SharedPreferences
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var pref : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val listView = findViewById<ListView>(R.id.listView)
        val userData: EditText = findViewById(R.id.text_data)
        val button: Button = findViewById(R.id.button_start)
        val todos: MutableList<String> = mutableListOf()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, todos)
        listView.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
           val quest = AlertDialog.Builder(this,position)
            val text = listView.getItemAtPosition(position).toString()
            quest.setTitle("Удаление")
                .setMessage("Вы дейстивельно хотите удалить задачу:$text ?")
                .setPositiveButton("Да, удалить"){
                        dialog, id -> dialog.cancel()
                    adapter.remove(text)
                    Toast.makeText(this,"Мы удалили $text:", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("Нет"){
                        dialog, id -> dialog.cancel()
                }
            quest.show()
            quest.create()

        }?: throw IllegalStateException("Activity cannot be null")

        button.setOnClickListener {
            val text = userData.text.toString().trim()
            if(text != ""){
                adapter.insert(text,0)
            }

        }


    }

}
