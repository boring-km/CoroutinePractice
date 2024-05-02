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
import com.boringkm.coroutinepractice.chapter04.myFunction
import com.boringkm.coroutinepractice.chapter04.myFunctionWithState
import kotlin.coroutines.Continuation


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
        Text(text = "Call Simple function")
      }
      Button(onClick = {
        val t = myFunctionWithState(
          Continuation(context) {
            Log.i("Chapter04Screen", "Result: $it")
          }
        )
        Log.i("Chapter04Screen", "function Result: $t")
      }) {
        Text(text = "Call Simple function with State")
      }
    }
  }
}

