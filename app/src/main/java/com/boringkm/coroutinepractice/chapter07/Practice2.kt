package com.boringkm.coroutinepractice.chapter07

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun main() {
    val ctx = CoroutineName("Name1") + Job()
    println(ctx[CoroutineName]?.name)
    ctx.fold("abcdefg") { acc: String, element: CoroutineContext.Element -> "$acc${element}" }.also(::println)
    val job: CoroutineContext = EmptyCoroutineContext
    ctx.fold(job) { acc, element: CoroutineContext.Element -> acc + element }.also(::println)

    val numbers = listOf(1, 2, 3, 4, 5)
//    val sum = numbers.fold(0) { acc, number -> acc + number }
//    println(sum)
    val zero = 0
    zero + numbers.sum()
}