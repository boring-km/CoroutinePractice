package com.boringkm.coroutinepractice.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boringkm.coroutinepractice.Routes


fun NavGraphBuilder.mainScreenComposable() {
  composable(Routes.MAIN) {
    MainScreen()
  }
}

val numberSequenceTextState =  mutableStateOf("0")
val sequenceIterator = getSequenceBuilder().iterator()
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
  Scaffold {
    Column {
      Text(text = numberSequenceTextState.value)
      Spacer(modifier = Modifier.size(10.dp))
      Button(onClick = {
        numberSequenceTextState.value = sequenceIterator.next().toString()
      }) {
        Text(text = "Next")
      }
    }
  }
}

fun getSequenceBuilder() = sequence {
  var i = 0
  while (true) {
    yield(++i)
  }
}
