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
        internal val any by lazy { Any() }
        fun with(context: Context, provider: ((what: VoiceWhat) -> VoiceRes?)? = null): MoneyBroadcaster {
            synchronized(any) {
                return MoneyBroadcasterDelegate(context, provider)
            }
        }
    }

    fun play(amount: Double)

    fun play(amount: AmountPlayInfo)

    fun stop()
}