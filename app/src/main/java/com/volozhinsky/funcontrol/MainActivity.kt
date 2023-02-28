package com.volozhinsky.funcontrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val counterView = findViewById<CounterView>(R.id.counterView)
        findViewById<Button>(R.id.reverseButton).setOnClickListener{
            counterView.inverseCountUp()
        }
    }
}