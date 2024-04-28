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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun NavGraphBuilder.chapter03ScreenComposable() {
  composable(Routes.CHAPTER_03) {
    Chapter03Screen()
  }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Chapter03Screen() {
  Scaffold {
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
      val coroutine = rememberCoroutineScope()
      Button(onClick = {
        coroutine.launch {
          callFunction()
        }
      }) {
        Text(text = "Call function")
      }
      Button(onClick = {
        coroutine.launch {
          callFunctionWithThread()
        }
      }) {
        Text(text = "Call function with thread")
      }
      Button(onClick = {
        coroutine.launch {
          callFunctionWithExecutor()
        }
      }) {
        Text(text = "Call function with executor")
      }
      Button(onClick = {
        coroutine.launch {
          coroutineScope {
            Log.d("Chapter03Screen", "Before launch")

            launch {
              delayAndResume()
            }
            suspendAndSetContinuation()

            Log.d("Chapter03Screen", "After launch")
          }
        }

      }) {
        Text(text = "Suspend and resume button")
      }
    }
  }
}

suspend fun callFunction() {
  Log.d("Chapter03Screen", "Before")
  suspendCoroutine<Unit> {
    Log.d("Chapter03Screen", "In Coroutine")
    it.resume(Unit) // 여기서 resume을 안하면 멈춰있다.
      // -> 잠시 중단되었다가 다시 시작되는 코드이지만, 최적화를 통해 곧바로 재개된다면 아예 중단되지 않는거나 다름없다.
  }
  Log.d("Chapter03Screen", "After")
}

suspend fun callFunctionWithThread() {
  Log.d("Chapter03Screen", "Before")
  suspendCoroutine {
    continueAfterSecond(it)
  }
  Log.d("Chapter03Screen", "After")
}

suspend fun callFunctionWithExecutor() {
  Log.d("Chapter03Screen", "Before")
  delay(1000)
  Log.d("Chapter03Screen", "After")
}


// 실제 delay 함수와 유사하다
private suspend fun delay(timeMillis: Long) = suspendCoroutine {
  executor.schedule({ it.resume(Unit) }, timeMillis, TimeUnit.MILLISECONDS)
}

fun continueAfterSecond(continuation: Continuation<Unit>) {
  thread {
    Thread.sleep(1000)
    continuation.resume(Unit)
  }
}

private val executor = Executors.newSingleThreadScheduledExecutor {
  Thread(it, "scheduler").apply { isDaemon = true }
}

// 메모리 누수가 발생할 수 있기 때문에 이렇게 구현하지 말라고 책에 써있음
var continuation: Continuation<Unit>? = null

suspend fun suspendAndSetContinuation() {
  suspendCoroutine<Unit> {
    continuation = it
  }
}

suspend fun delayAndResume() {
  delay(1000)
  continuation?.resume(Unit)
}