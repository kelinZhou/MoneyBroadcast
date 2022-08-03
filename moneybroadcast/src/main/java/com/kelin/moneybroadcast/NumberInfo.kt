package com.kelin.moneybroadcast

import com.kelin.moneybroadcast.voice.VoiceWhat
import com.kelin.moneybroadcast.voice.VoiceWhatPrefix
import com.kelin.moneybroadcast.voice.VoiceWhatUnit

/**
 * **描述:** 数字播报信息。
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2021/11/11 1:36 PM
 *
 * **版本:** v 1.0.0
 */
class NumberInfo(
    /**
     * 要播报的数字。
     */
    val number: Double,
    /**
     * 设置再播报金额之前需要播报的语音，如不需要则设置为[com.kelin.moneybroadcast.voice.VoiceWhatNull]，
     * 如果[prefix]和[unit]同时设置为[com.kelin.moneybroadcast.voice.VoiceWhatNull]将意味着只会播报数字。
     */
    val prefix: VoiceWhat = VoiceWhatPrefix,
    /**
     * 设置数字的单位，将会在数字播报完毕之后播报该语音，如不需要则设置为[com.kelin.moneybroadcast.voice.VoiceWhatNull]，
     * 如果[prefix]和[unit]同时设置为[com.kelin.moneybroadcast.voice.VoiceWhatNull]将意味着只会播报数字。
     */
    val unit: VoiceWhat = VoiceWhatUnit
) {

    class Builder {
        private var mFront: VoiceWhat = VoiceWhatPrefix
        private var mNumber: Double = 0.0
        private var mUnit: VoiceWhat = VoiceWhatUnit

        fun setFront(front: VoiceWhat): Builder {
            mFront = front
            return this
        }

        fun setAmount(number: Double): Builder {
            mNumber = number
            return this
        }

        fun setUnit(unit: VoiceWhat): Builder {
            mUnit = unit
            return this
        }

        fun build(): NumberInfo {
            return NumberInfo(mNumber, mFront, mUnit)
        }
    }
}