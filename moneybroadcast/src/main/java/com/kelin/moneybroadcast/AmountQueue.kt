package com.kelin.moneybroadcast

import java.util.concurrent.ArrayBlockingQueue

/**
 * **描述:** 金额队列。
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2022/4/15 7:07 PM
 *
 * **版本:** v 1.0.0
 */
object AmountQueue {
    internal val dataQueue by lazy { ArrayBlockingQueue<AmountPlayInfo>(10000, true) }
    internal val readyQueue by lazy { ArrayBlockingQueue<AmountPlayInfo>(1, true) }
}