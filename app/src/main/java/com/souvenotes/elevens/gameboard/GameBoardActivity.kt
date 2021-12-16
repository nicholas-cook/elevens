package com.souvenotes.elevens.gameboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.souvenotes.elevens.R

class GameBoardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_board)

        supportActionBar?.hide()
    }
}