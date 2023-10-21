package com.example.scanner2

import android.content.*
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TextActivity : AppCompatActivity(), HistoryAdapter.Listener {

    var Num = 0
    val adapter= HistoryAdapter(this)
    lateinit var sharedPreferences: SharedPreferences
    lateinit var sharedPreferencesNum: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        val value = intent.getStringExtra("QRCODE")
        textView = findViewById<TextView>(R.id.textView)
        textView.text = value

        sharedPreferencesNum = getSharedPreferences("Num", Context.MODE_PRIVATE)
        Num = sharedPreferencesNum.getInt("Num", 0)
        sharedPreferences = getSharedPreferences("$Num", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.putString("$Num", value)
        editor.apply()
        Num += 1
        editor = sharedPreferencesNum.edit()
        editor.putInt("Num", Num)
        editor.apply()

        findViewById<RecyclerView>(R.id.rcView).layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.rcView).adapter=adapter
        var i=0
        while(i<Num){
            sharedPreferences = getSharedPreferences("$i", Context.MODE_PRIVATE)
            adapter.HistiryCreate(sharedPreferences.getString("$i","").toString())
            i++
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            if(findViewById<RecyclerView>(R.id.rcView).visibility==View.VISIBLE){
                findViewById<RecyclerView>(R.id.rcView).visibility=View.INVISIBLE
            }else
                findViewById<RecyclerView>(R.id.rcView).visibility=View.VISIBLE
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(textView.text as String?))
            startActivity(intent)
        }
        textView.setOnLongClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Текст курва кода", textView.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this,"Текст успешно скопирован", Toast.LENGTH_LONG).show()
            true
        }
    }


    override fun onClick(history: History) {
        textView.text = history.text
        findViewById<RecyclerView>(R.id.rcView).visibility=View.INVISIBLE
    }
}