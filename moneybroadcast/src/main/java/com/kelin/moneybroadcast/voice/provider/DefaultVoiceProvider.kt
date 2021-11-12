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
    override fun onProvideVoice(what: VoiceWhat): VoiceRes {
        return when (what) {
            VoiceWhatSuccess -> RawVoice(R.raw.tts_success, 1500)
            VoiceWhatUnit -> RawVoice(R.raw.tts_yuan, 500)
            VoiceWhatDot -> RawVoice(R.raw.tts_dot, 500)
            VoiceWhatTenMillion -> RawVoice(R.raw.tts_ten_million, 500)
            VoiceWhatTenThousand -> RawVoice(R.raw.tts_ten_thousand, 500)
            VoiceWhatThousand -> RawVoice(R.raw.tts_thousand, 500)
            VoiceWhatHundred -> RawVoice(R.raw.tts_hundred, 500)
            VoiceWhatTen -> RawVoice(R.raw.tts_ten, 500)
            VoiceWhatNine -> RawVoice(R.raw.tts_9, 500)
            VoiceWhatEight -> RawVoice(R.raw.tts_8, 500)
            VoiceWhatSeven -> RawVoice(R.raw.tts_7, 500)
            VoiceWhatSix -> RawVoice(R.raw.tts_6, 500)
            VoiceWhatFive -> RawVoice(R.raw.tts_5, 500)
            VoiceWhatFour -> RawVoice(R.raw.tts_4, 500)
            VoiceWhatThree -> RawVoice(R.raw.tts_3, 500)
            VoiceWhatTwo, VoiceWhatTwain -> RawVoice(R.raw.tts_2, 500)
            VoiceWhatOne -> RawVoice(R.raw.tts_1, 500)
            VoiceWhatZero -> RawVoice(R.raw.tts_0, 500)
            VoiceWhatNull -> NullVoice()
        }
    }
}