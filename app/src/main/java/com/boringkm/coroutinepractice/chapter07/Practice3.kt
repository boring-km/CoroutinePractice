package com.boringkm.coroutinepractice.chapter07

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun CoroutineScope.log(msg: String) {
    val name = coroutineContext[CoroutineName]?.name
    println("[$name] $msg")
}

fun main() = runBlocking(CoroutineName("main")) {
    log("Started")

    val v1 = async {
        delay(500)
        log("Running async v1")
        42
    }
    launch {
        delay(1000)
        log("Running launch")
    }

    val v2 = async(CoroutineName("c1")) {
        delay(500)
        log("Running async in c1")
        49
    }

    launch(CoroutineName("c2")) {
        delay(1000)
        log("Running launch in c2")
    }

    log("Result: ${v1.await()}")
    log("Result: ${v2.await()}")

    /*
    부모는 기본적으로 컨텍스트를 자식에게 전달한다.
    모든 자식은 빌더의 인자에서 정의된 특정 컨텍스트를 가질 수 있다. 인자로 전달된 컨텍스트는 부모로부터 상속받은 컨텍스트를 대체한다.
     */
}