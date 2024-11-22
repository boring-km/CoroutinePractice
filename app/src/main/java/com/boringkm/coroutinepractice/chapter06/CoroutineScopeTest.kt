package com.boringkm.coroutinepractice.chapter06

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

suspend fun getMyNumbers(): List<Int> {
    delay(1000)
    return listOf(1, 2, 4, 5, 6, 7, 8, 9, 10)
}

suspend fun getSpecialNumbers(): List<Int> {
    delay(1000)
    return listOf(1, 3, 4, 7, 10)
}

fun hasSpecialNumber(item: Int, specialNumbers: List<Int>): Boolean {
    return specialNumbers.contains(item)
}

suspend fun getMySpecialNumbers(): List<Int> = coroutineScope {
    val myNumbers = async { getMyNumbers() }    // async 를 사용하려면 반드시 scope 안에 있어야 함
    val specialNumbers = getSpecialNumbers()
    myNumbers.await()
        .filter { hasSpecialNumber(it, specialNumbers) }
}

suspend fun main() {
    val mySpecialNumbers = getMySpecialNumbers()
    println(mySpecialNumbers)
}