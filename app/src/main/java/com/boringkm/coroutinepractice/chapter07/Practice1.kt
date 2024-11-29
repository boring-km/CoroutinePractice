package com.boringkm.coroutinepractice.chapter07

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun main() {
    var ctx: CoroutineContext = CoroutineName("boring")
    val coroutineName = ctx[CoroutineName]
    println(coroutineName)
//    ctx = ctx.plus(Job())
//    val job = ctx[Job]
//    println(job)
//    println(ctx.job)
    println(ctx[Job]?.isActive)

    val ctx2 = Job()
    println(ctx2[Job]?.isActive)

    val empty = EmptyCoroutineContext
    println(empty[CoroutineName]?.name)
    println(empty[Job]?.isActive)

    // 1. CoroutineContext의 속성이 합쳐진다
    // 2. 비어있는 CoroutineContext 를 추가
    val ctx3 = ctx + ctx2 + empty

    CoroutineScope(ctx3).launch {
        println(this.coroutineContext[CoroutineName]?.name)
        println(this.coroutineContext[Job]?.isActive)

        val newCtx = this.coroutineContext.minusKey(CoroutineName)  // 원소 제거
        println("newCtx: ${newCtx[CoroutineName]?.name}")
    }

    Thread.sleep(1000)
}