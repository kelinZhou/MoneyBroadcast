package com.kelin.moneybroadcast.voice

import androidx.annotation.RawRes

/**
 * **描述:** 声音资源。
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2021/11/11 1:39 PM
 *
 * **版本:** v 1.0.0
 */
sealed class VoiceRes(
    open val res: Any,
    open val duration: Long
)

class RawVoice(@RawRes override val res: Int, override val duration: Long) : VoiceRes(res, duration)

class AssetVoice(override val res: String, override val duration: Long) : VoiceRes(res, duration)