package com.kelin.moneybroadcast

import android.content.Context
import com.kelin.moneybroadcast.voice.VoiceRes
import com.kelin.moneybroadcast.voice.VoiceWhat

/**
 * **描述:** 金额播报者。
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2021/11/11 1:29 PM
 *
 * **版本:** v 1.0.0
 */
interface MoneyBroadcaster {
    companion object {
        /**
         * 创建一个MoneyBroadcaster。
         */
        fun create(context: Context, provider: ((what: VoiceWhat) -> VoiceRes?)? = null): MoneyBroadcaster {
            synchronized(MoneyBroadcaster::class.java) {
                return MoneyBroadcasterDelegate(context, provider)
            }
        }
    }

    fun play(vararg numbers: NumberInfo) {
        playAll(numbers.toList())
    }

    fun play(vararg numbers: Double) {
        playAll(numbers.map { NumberInfo(it) })
    }

    fun play(numbers: Iterable<Double>) {
        playAll(numbers.map { NumberInfo(it) })
    }

    fun playAll(numbers: Iterable<NumberInfo>)

    fun stop()
}