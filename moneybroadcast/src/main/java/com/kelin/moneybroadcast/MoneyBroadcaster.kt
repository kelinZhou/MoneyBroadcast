package com.kelin.moneybroadcast

import android.content.Context
import com.kelin.moneybroadcast.voice.provider.DefaultVoiceProvider
import com.kelin.moneybroadcast.voice.provider.VoiceProvider

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

        fun with(context: Context, provider: VoiceProvider = DefaultVoiceProvider()): MoneyBroadcaster {
            synchronized(MoneyBroadcaster::class.java) {
                return MoneyBroadcasterDelegate(context, provider)
            }
        }
    }

    fun play(amount: Double)

    fun play(amount: AmountPlayInfo)
}