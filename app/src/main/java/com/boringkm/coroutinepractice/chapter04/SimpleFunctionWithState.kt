package com.boringkm.coroutinepractice.chapter04

import java.util.Timer
import java.util.TimerTask
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

//suspend fun myFunctionWithState() {
//  println("Before")
//  var counter = 0
//  delay(1000)
//  counter++
//  println("Counter: $counter")
//  println("After")
//}

private const val COROUTINE_SUSPENDED = "foo"
fun myFunctionWithState(continuation: Continuation<Unit>): Any {
  val continuation = continuation as? MyFunctionContinuationWithState ?: MyFunctionContinuationWithState(continuation)
  var counter = continuation.counter

  if (continuation.label == 0) {
    println("Before")
    continuation.label = 0
    continuation.counter = counter
    continuation.label = 1
    if (delay(1000, continuation) == COROUTINE_SUSPENDED) {
      return COROUTINE_SUSPENDED
    }
  }
  if (continuation.label == 1) {
    counter += 1
    println("Counter: $counter")
    println("After")
    return Unit
  }
  error("Impossible")
}

private fun delay(timeMillis: Long, continuation: Continuation<Unit>): Any {
  Timer().schedule(object : TimerTask() {
    override fun run() {
      continuation.resumeWith(Result.success(Unit))
    }
  }, timeMillis)

  return COROUTINE_SUSPENDED
}


class MyFunctionContinuationWithState(
  private val completion: Continuation<Unit>
): Continuation<Unit> {
  override val context: CoroutineContext
    get() = completion.context

  var result: Result<Unit>? = null
  var label = 0
  var counter = 0

  override fun resumeWith(result: Result<Unit>) {
    this.result = result
    val res = try {
      val r = myFunctionWithState(this)
      if (r == COROUTINE_SUSPENDED) {
        return
      }
      Result.success(r as Unit)
    } catch (e: Throwable) {
      Result.failure(e)
    }
    completion.resumeWith(res)
  }
}
