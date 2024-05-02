package com.boringkm.coroutinepractice.chapter04

import android.util.Log
import java.util.Timer
import java.util.TimerTask
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

private const val COROUTINE_SUSPENDED = "foo"
private fun delay(timeMillis: Long, continuation: Continuation<Unit>): Any {
  Timer().schedule(object : TimerTask() {
    override fun run() {
      continuation.resumeWith(Result.success(Unit))
    }
  }, timeMillis)

  return COROUTINE_SUSPENDED
}

fun myFunction(continuation: Continuation<Unit>): Any {
  val continuation = continuation as? MyFunctionContinuation ?: MyFunctionContinuation(continuation)

  if (continuation.label == 0) {
    Log.i("Chapter04","Before")
    continuation.label = 1
    if (delay(1000, continuation) == COROUTINE_SUSPENDED) {
      return COROUTINE_SUSPENDED
    }
  }
  if (continuation.label == 1) {
    Log.i("Chapter04","After")
    return Unit
  }
  error("Impossible")
}


class MyFunctionContinuation(
  private val completion: Continuation<Unit>
) : Continuation<Unit> {
  override val context: CoroutineContext
    get() = completion.context

  var label = 0
  private var result: Result<Any>? = null

  override fun resumeWith(result: Result<Unit>) {
    this.result = result
    val res = try {
      val r = myFunction(this)
      if (r == COROUTINE_SUSPENDED) return
      Result.success(r as Unit)
    } catch (e: Throwable) {
      Result.failure(e)
    }
    completion.resumeWith(res)
  }
}
