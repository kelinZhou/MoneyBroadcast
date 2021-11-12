package com.kelin.moneybroadcast.voice

/**
 * **描述:** 用来获取声音资源的依据。
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2021/11/11 2:35 PM
 *
 * **版本:** v 1.0.0
 */
sealed class VoiceWhat

/**
 * 无声音。
 */
object VoiceWhatNull : VoiceWhat()

/**
 * 收款成功。
 */
object VoiceWhatSuccess : VoiceWhat()

/**
 * 金额单位。
 */
object VoiceWhatUnit : VoiceWhat()

/**
 * 小数点。
 */
object VoiceWhatDot : VoiceWhat()

/**
 * 亿。
 */
object VoiceWhatTenMillion : VoiceWhat()

/**
 * 万。
 */
object VoiceWhatTenThousand : VoiceWhat()

/**
 * 千。
 */
object VoiceWhatThousand : VoiceWhat()

/**
 * 百。
 */
object VoiceWhatHundred : VoiceWhat()

/**
 * 十。
 */
object VoiceWhatTen : VoiceWhat()

/**
 * 九。
 */
object VoiceWhatNine : VoiceWhat()

/**
 * 八。
 */
object VoiceWhatEight : VoiceWhat()

/**
 * 七。
 */
object VoiceWhatSeven : VoiceWhat()

/**
 * 六。
 */
object VoiceWhatSix : VoiceWhat()

/**
 * 五。
 */
object VoiceWhatFive : VoiceWhat()

/**
 * 四。
 */
object VoiceWhatFour : VoiceWhat()

/**
 * 三。
 */
object VoiceWhatThree : VoiceWhat()

/**
 * 二。
 */
object VoiceWhatTwo : VoiceWhat()

/**
 * 一。
 */
object VoiceWhatOne : VoiceWhat()

/**
 * 零。
 */
object VoiceWhatZero : VoiceWhat()

/**
 * 两，当2出现在某些特定位置的时候需要读作两，例如220 需要读作 两百二十。
 */
object VoiceWhatTwain : VoiceWhat()