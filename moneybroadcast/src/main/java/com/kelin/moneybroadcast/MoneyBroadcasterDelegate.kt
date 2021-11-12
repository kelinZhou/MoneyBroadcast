package com.kelin.moneybroadcast

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import com.kelin.moneybroadcast.voice.*
import com.kelin.moneybroadcast.voice.provider.DefaultVoiceProvider
import java.text.DecimalFormat
import java.util.concurrent.Executors

/**
 * **描述:** 金额播报者。
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2021/11/11 1:29 PM
 *
 * **版本:** v 1.0.0
 */
internal class MoneyBroadcasterDelegate(private val context: Context, private val provider: ((what: VoiceWhat) -> VoiceRes?)?) : MoneyBroadcaster {

    companion object {
        private val CHINESE_UNIT = charArrayOf('零', '拾', '佰', '仟', '万', '拾', '佰', '仟', '亿', '拾', '佰', '仟')

        val voiceWhatPool = mapOf(
            Pair('亿', VoiceWhatTenMillion),
            Pair('万', VoiceWhatTenThousand),
            Pair('仟', VoiceWhatThousand),
            Pair('佰', VoiceWhatHundred),
            Pair('拾', VoiceWhatTen),
            Pair('0', VoiceWhatZero),
            Pair('1', VoiceWhatOne),
            Pair('2', VoiceWhatTwo),
            Pair('3', VoiceWhatThree),
            Pair('4', VoiceWhatFour),
            Pair('5', VoiceWhatFive),
            Pair('6', VoiceWhatSix),
            Pair('7', VoiceWhatSeven),
            Pair('8', VoiceWhatEight),
            Pair('9', VoiceWhatNine),
            Pair('.', VoiceWhatDot),
        )
    }

    private val defaultVoiceProvider by lazy { DefaultVoiceProvider() }

    private val executorService by lazy { Executors.newCachedThreadPool() }

    override fun play(amount: Double) {
        play(AmountPlayInfo(amount))
    }

    override fun play(amount: AmountPlayInfo) {
        executorService.execute {
            doPlay(amount)
        }
    }

    private fun doPlay(amount: AmountPlayInfo) {
        synchronized(MoneyBroadcasterDelegate::class.java) {
            SoundPool(1, AudioManager.STREAM_MUSIC, 0).also { player ->
                val soundList = getVoiceWhatListByAmount(amount).mapNotNull { what ->
                    (provider?.invoke(what) ?: defaultVoiceProvider.onProvideVoice(what)).let {
                        loadVoiceDataByVoiceRes(player, it)?.let { id ->
                            SoundId(
                                id,
                                it.duration
                            )
                        }
                    }
                }
                Thread.sleep(500L)
                soundList.forEachIndexed { i, sound ->
                    player.play(sound.id, 1F, 1F, 100, 0, 1F)
                    if (i < soundList.lastIndex) {
                        Thread.sleep(sound.duration)
                    }
                }
            }
        }
    }

    private fun loadVoiceDataByVoiceRes(player: SoundPool, res: VoiceRes): Int? {
        return when (res) {
            is RawVoice -> {
                player.load(context, res.res, 100)
            }
            is AssetVoice -> {
                player.load(context.assets.openFd(res.res), 100)
            }
            else -> null
        }
    }

    /**
     * 根据金额返回声音类型。
     */
    private fun getVoiceWhatListByAmount(amount: AmountPlayInfo): List<VoiceWhat> {
        return if (amount.amount == 0.0 || amount.amount > 999999999.9999) {
            listOf(amount.front)
        } else {
            val amountStr = DecimalFormat("0.####").format(amount.amount)
            ArrayList<VoiceWhat>(amountStr.length + 2).apply {
                add(amount.front)
                addAll(transformReadableNumbers(amountStr))
                add(amount.unit)
            }
        }
    }

    /**
     * 将金额的字符串形式转换为声音类型。
     */
    private fun transformReadableNumbers(amount: String): List<VoiceWhat> {
        return if (amount.contains(".")) {
            val amountParts = amount.split('.')
            ArrayList<VoiceWhat>().apply {
                addAll(
                    transformReadableAmountToVoiceWhat(intAmountToReadableString(amountParts[0].toInt()))
                )
                add(VoiceWhatDot)
                addAll(
                    transformReadableAmountToVoiceWhat(amountParts[1])
                )
            }
        } else {
            transformReadableAmountToVoiceWhat(
                intAmountToReadableString(amount.toInt())
            )
        }
    }

    /**
     * 将整数金额转换为可读的字符串。
     */
    private fun intAmountToReadableString(value: Int): String {
        when (value) {
            0 -> {
                return "0"
            }
            10 -> {
                return "拾"
            }
            in 11..19 -> {
                return "拾${value % 10}"
            }
            else -> {
                val res = StringBuffer()
                var moneyNum = value
                var i = 0
                while (moneyNum > 0) {
                    res.insert(0, CHINESE_UNIT[i++])
                    res.insert(0, moneyNum % 10)
                    moneyNum /= 10
                }
                return res.replace("0[拾佰仟]".toRegex(), "0")
                    .replace("0+亿".toRegex(), "亿")
                    .replace("0+万".toRegex(), "万")
                    .replace("0+元".toRegex(), "元")
                    .replace("0+".toRegex(), "0")
                    .replace("零", "")
            }
        }
    }

    /**
     * 将客户的金额字符串转换为声音类型。
     */
    private fun transformReadableAmountToVoiceWhat(readableAmount: String): List<VoiceWhat> {
        return readableAmount.mapNotNull { voiceWhatPool[it] }
    }

    /**
     * 封装SoundPool的ID和时长。
     */
    data class SoundId(val id: Int, val duration: Long)
}