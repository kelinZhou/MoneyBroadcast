package com.kelin.moneybroadcastdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kelin.moneybroadcast.AmountPlayInfo
import com.kelin.moneybroadcast.MoneyBroadcaster
import com.kelin.moneybroadcast.voice.*
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
        btnPlay2.setOnClickListener {
            if (!etAmount.text.isNullOrEmpty()) {
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
                }.play(etAmount.text.toString().toDouble())
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
}