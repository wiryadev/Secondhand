package com.firstgroup.secondhand

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firstgroup.secondhand.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(
            Intent(this, AuthActivity::class.java)
        )
    }
}