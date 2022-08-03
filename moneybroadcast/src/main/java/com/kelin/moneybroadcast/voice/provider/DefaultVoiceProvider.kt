package com.kelin.moneybroadcast.voice.provider

import com.kelin.moneybroadcast.*
import com.kelin.moneybroadcast.voice.*

/**
 * **描述:** 默认的声音提供者。
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2021/11/11 2:40 PM
 *
 * **版本:** v 1.0.0
 */
class DefaultVoiceProvider : VoiceProvider {
    override fun provideVoice(what: VoiceWhat): VoiceRes {
        return when (what) {
            VoiceWhatPrefix -> RawVoice(R.raw.tts_success)
            VoiceWhatUnit -> RawVoice(R.raw.tts_yuan)
            VoiceWhatDot -> RawVoice(R.raw.tts_dot)
            VoiceWhatTenMillion -> RawVoice(R.raw.tts_ten_million)
            VoiceWhatTenThousand -> RawVoice(R.raw.tts_ten_thousand)
            VoiceWhatThousand -> RawVoice(R.raw.tts_thousand)
            VoiceWhatHundred -> RawVoice(R.raw.tts_hundred)
            VoiceWhatTen -> RawVoice(R.raw.tts_ten)
            VoiceWhatNine -> RawVoice(R.raw.tts_9)
            VoiceWhatEight -> RawVoice(R.raw.tts_8)
            VoiceWhatSeven -> RawVoice(R.raw.tts_7)
            VoiceWhatSix -> RawVoice(R.raw.tts_6)
            VoiceWhatFive -> RawVoice(R.raw.tts_5)
            VoiceWhatFour -> RawVoice(R.raw.tts_4)
            VoiceWhatThree -> RawVoice(R.raw.tts_3)
            VoiceWhatTwo, VoiceWhatTwain -> RawVoice(R.raw.tts_2)
            VoiceWhatOne -> RawVoice(R.raw.tts_1)
            VoiceWhatZero -> RawVoice(R.raw.tts_0)
            VoiceWhatNull -> NullVoice()
        }
    }
}