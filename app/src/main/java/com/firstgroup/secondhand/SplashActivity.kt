package com.firstgroup.secondhand

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.firstgroup.secondhand.core.network.utils.AuthInterceptor
import com.firstgroup.secondhand.ui.auth.AuthActivity
import com.firstgroup.secondhand.ui.main.MainActivity
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject lateinit var authInterceptor: AuthInterceptor

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        checkLoginStatus()
        setContent {
            MdcTheme {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }

    private fun checkLoginStatus() {
        viewModel.checkSession()
        lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(lifecycle)
                .collect { uiState ->
                    when (uiState) {
                        is MainUiState.Initial -> {
                            // do nothing until data is loaded
                        }
                        is MainUiState.Loaded -> {
                            if (uiState.isLoggedIn) {
                                authInterceptor.setToken(uiState.token)
                                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                            } else {
                                startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
                            }
                        }
                    }
                }
        }
    }
}