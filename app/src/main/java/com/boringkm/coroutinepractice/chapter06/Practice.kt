package com.boringkm.coroutinepractice.chapter06

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val res1 = GlobalScope.async {
        delay(1000)
        "Text 1"
    }
    val res2 = GlobalScope.async {
        delay(3000)
        "Text 2"
    }
    val res3 = GlobalScope.async {
        delay(2000)
        "Text 3"
    }
    println("Result 1: ${res1.await()}")
    println("Result 2: ${res2.await()}")
    println("Result 3: ${res3.await()}")
}