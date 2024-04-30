package com.boringkm.coroutinepractice.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boringkm.coroutinepractice.Routes
import java.util.Timer
import java.util.TimerTask
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext


fun NavGraphBuilder.chapter04ScreenComposable() {
  composable(Routes.CHAPTER_04) {
    Chapter04Screen()
  }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Chapter04Screen() {
  Scaffold {
    // get coroutine context
    val scope = rememberCoroutineScope()
    val context = scope.coroutineContext

    Column(
      Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Button(onClick = {
        myFunction(
          Continuation(context) {
            Log.i("Chapter04Screen", "Result: $it")
          }
        )
      }) {
        Text(text = "Call function")
      }
    }
  }
}

const val COROUTINE_SUSPENDED = "foo"
fun delay(timeMillis: Long, continuation: Continuation<Unit>): Any {
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