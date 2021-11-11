package com.kelin.moneybroadcast

import com.kelin.moneybroadcast.voice.VoiceWhat
import com.kelin.moneybroadcast.voice.VoiceWhatSuccess
import com.kelin.moneybroadcast.voice.VoiceWhatUnit

/**
 * **描述:** 金额播报信息。
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2021/11/11 1:36 PM
 *
 * **版本:** v 1.0.0
 */
class AmountPlayInfo(
    val amount: Double,
    val front: VoiceWhat = VoiceWhatSuccess,
    val unit: VoiceWhat = VoiceWhatUnit
) {

    class Builder {
        private var mFront: VoiceWhat = VoiceWhatSuccess
        private var mAmount: Double = 0.0
        private var mUnit: VoiceWhat = VoiceWhatUnit

        fun setFront(front: VoiceWhat): Builder {
            mFront = front
            return this
        }

        fun setAmount(amount: Double): Builder {
            mAmount = amount
            return this
        }

        fun setUnit(unit: VoiceWhat): Builder {
            mUnit = unit
            return this
        }

        fun build(): AmountPlayInfo {
            return AmountPlayInfo(mAmount, mFront, mUnit)
        }
    }
}