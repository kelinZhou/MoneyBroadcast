package com.kelin.moneybroadcastdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.kelin.moneybroadcast.AmountPlayInfo
import com.kelin.moneybroadcast.MoneyBroadcaster
import com.kelin.moneybroadcast.voice.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val moneyBroadcaster1 by lazy { MoneyBroadcaster.with(applicationContext) }
    private val moneyBroadcaster2 by lazy {
        MoneyBroadcaster.with(applicationContext) {
            return@with when (it) {
                VoiceWhatSuccess -> {
                    AssetVoice("sound/tts_success.mp3", 1500)
                }
                VoiceWhatUnit -> {
                    RawVoice(R.raw.yuan, 1500)
                }
                else -> {
                    null
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlay.setOnClickListener {
            if (!etAmount.text.isNullOrEmpty()) {
                moneyBroadcaster1.play(etAmount.text.toString().toDouble())
                etAmount.text = null
            }
        }
        btnPlay2.setOnClickListener {
            if (!etAmount.text.isNullOrEmpty()) {
                moneyBroadcaster2.play(etAmount.text.toString().toDouble())
                etAmount.text = null
            }
        }
        btnPlay3.setOnClickListener {
            if (!etAmount.text.isNullOrEmpty()) {
                MoneyBroadcaster.with(applicationContext).play(
                    AmountPlayInfo(etAmount.text.toString().toDouble(), VoiceWhatNull)
                )
                etAmount.text = null
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_stop, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menu_stop) {
            moneyBroadcaster1.stop()
            moneyBroadcaster2.stop()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}