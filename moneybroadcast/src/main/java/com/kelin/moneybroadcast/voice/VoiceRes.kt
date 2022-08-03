package com.kelin.moneybroadcast.voice

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
    open val res: Any
)

/**
 * raw资源下的声音资源。
 */
class RawVoice(override val res: Int) : VoiceRes(res)

/**
 * asset资源下的声音资源。
 */
class AssetVoice(override val res: String) : VoiceRes(res)

/**
 * 无任何声音，当某些字符不需要有声音时使用。
 */
class NullVoice : VoiceRes(0)