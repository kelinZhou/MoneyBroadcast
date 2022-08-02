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

    fun play(vararg amounts: AmountPlayInfo) {
        playAll(amounts.toList())
    }

    fun play(vararg amounts: Double) {
        playAll(amounts.map { AmountPlayInfo(it) })
    }

    fun play(amounts: Collection<Double>) {
        playAll(amounts.map { AmountPlayInfo(it) })
    }

    fun playAll(amounts: Collection<AmountPlayInfo>)

    fun stop()
}