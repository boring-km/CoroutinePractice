package com.boringkm.coroutinepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.boringkm.coroutinepractice.screens.chapter02ScreenComposable
import com.boringkm.coroutinepractice.screens.chapter03ScreenComposable
import com.boringkm.coroutinepractice.screens.chapter04ScreenComposable
import com.boringkm.coroutinepractice.ui.theme.CoroutinePracticeTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val navigationController = rememberNavController()
      CoroutinePracticeTheme {
        NavHost(navController = navigationController, startDestination = Routes.CHAPTER_04) {
          chapter02ScreenComposable()
          chapter03ScreenComposable()
          chapter04ScreenComposable()
        }
      }
    }
  }
}
