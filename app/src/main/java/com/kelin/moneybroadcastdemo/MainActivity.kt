package com.kelin.moneybroadcastdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kelin.moneybroadcast.MoneyBroadcaster
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlay.setOnClickListener {
            if (!etAmount.text.isNullOrEmpty()) {
                MoneyBroadcaster.with(applicationContext).play(etAmount.text.toString().toDouble())
                etAmount.text = null
            }
        }
    }
}