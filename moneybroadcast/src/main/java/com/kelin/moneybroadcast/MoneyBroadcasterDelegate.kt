package com.kelin.moneybroadcast

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.WorkerThread
import com.kelin.moneybroadcast.voice.*
import com.kelin.moneybroadcast.voice.provider.DefaultVoiceProvider
import java.text.DecimalFormat
import java.util.concurrent.ArrayBlockingQueue
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
        private val CHINESE_UNIT = charArrayOf('元', '拾', '佰', '仟', '万', '拾', '佰', '仟', '亿', '拾', '佰', '仟')

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

    /**
     * 生产线程。
     */
    private val producerService by lazy { Executors.newSingleThreadExecutor() }

    /**
     * 消费线程。
     */
    private val consumerService by lazy { Executors.newSingleThreadExecutor() }

    /**
     * 默认的声音提供者。
     */
    private val defaultVoiceProvider by lazy { DefaultVoiceProvider() }

    /**
     * 播放器。
     */
    private val player by lazy { MediaPlayer() }

    /**
     * 数据源队列。
     */
    private val dataQueue by lazy { ArrayBlockingQueue<AmountPlayInfo>(10000, true) }

    /**
     * 准备好了的数据队列。
     */
    private val readyQueue by lazy { ArrayBlockingQueue<AmountPlayInfo>(1, true) }

    init {
        consumerService.execute {
            while (true) {
                dataQueue.take().also {
                    readyQueue.put(it)
                    doPlayV2(it)
                }
            }
        }
    }

    override fun playAll(amounts: Collection<AmountPlayInfo>) {
        producerService.execute { doProducer(amounts) }
    }

    private fun doProducer(amounts: Collection<AmountPlayInfo>) {
        synchronized(MoneyBroadcasterDelegate::class.java) {
            amounts.forEach { dataQueue.put(it) }
        }
    }

    override fun stop() {
        dataQueue.clear()
    }

    @WorkerThread
    private fun doPlayV2(amount: AmountPlayInfo) {
        synchronized(MoneyBroadcasterDelegate::class.java) {
            val srcList = getVoiceWhatListByAmount(amount).mapNotNullTo(ArrayList()) { what ->
                (provider?.invoke(what) ?: defaultVoiceProvider.onProvideVoice(what)).let {
                    if (it is NullVoice) {
                        null
                    } else {
                        it
                    }
                }
            }
            startPlay(srcList)
        }
    }

    private fun startPlay(srcList: ArrayList<VoiceRes>) {
        synchronized(MoneyBroadcasterDelegate::class.java) {
            val descriptor = context.resources.openRawResourceFd(srcList[0].res)
            player.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
            player.prepareAsync()
            player.setOnPreparedListener { player.start() }
            player.setOnCompletionListener {
                player.reset()
                srcList.removeAt(0)
                if (srcList.isNotEmpty()) {
                    startPlay(srcList)
                } else {
                    readyQueue.take()
                }
            }
        }
    }

    /**
     * 根据金额返回声音类型。
     */
    private fun getVoiceWhatListByAmount(amount: AmountPlayInfo): MutableList<VoiceWhat> {
        return if (amount.amount == 0.0 || amount.amount > 999999999.9999) {
            mutableListOf(amount.front)
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
                    .replace("元", "")
            }
        }
    }

    /**
     * 将客户的金额字符串转换为声音类型。
     */
    private fun transformReadableAmountToVoiceWhat(readableAmount: String): List<VoiceWhat> {
        return readableAmount.mapNotNull { voiceWhatPool[it] }
    }
}