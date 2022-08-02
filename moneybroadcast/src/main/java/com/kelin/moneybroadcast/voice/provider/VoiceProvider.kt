package com.kelin.moneybroadcast.voice.provider

import com.kelin.moneybroadcast.voice.VoiceRes
import com.kelin.moneybroadcast.voice.VoiceWhat

/**
 * **描述:** 声音提供者。
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2021/11/11 2:31 PM
 *
 * **版本:** v 1.0.0
 */
interface VoiceProvider {
    fun provideVoice(what: VoiceWhat): VoiceRes?
}