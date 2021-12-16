package com.souvenotes.elevens.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.souvenotes.elevens.gameboard.GameBoardActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, GameBoardActivity::class.java))
        finish()
    }
}