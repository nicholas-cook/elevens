package com.souvenotes.elevens.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.souvenotes.elevens.utils.ElevensSharedPreferences.Companion.TOTAL_GAMES
import com.souvenotes.elevens.utils.ElevensSharedPreferences.Companion.TOTAL_LOSSES
import com.souvenotes.elevens.utils.ElevensSharedPreferences.Companion.TOTAL_RESETS
import com.souvenotes.elevens.utils.ElevensSharedPreferences.Companion.TOTAL_WINS

class ElevensSharedPreferencesImpl(context: Context) : ElevensSharedPreferences {

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("com.souvenotes.elevens.sharedprefs", Context.MODE_PRIVATE)

    override var totalWins: Int
        get() = sharedPreferences.getInt(TOTAL_WINS, 0)
        set(value) = sharedPreferences.edit { putInt(TOTAL_WINS, value) }

    override var totalLosses: Int
        get() = sharedPreferences.getInt(TOTAL_LOSSES, 0)
        set(value) = sharedPreferences.edit { putInt(TOTAL_LOSSES, value) }

    override var totalGames: Int
        get() = sharedPreferences.getInt(TOTAL_GAMES, 0)
        set(value) = sharedPreferences.edit { putInt(TOTAL_GAMES, value) }

    override var totalResets: Int
        get() = sharedPreferences.getInt(TOTAL_RESETS, 0)
        set(value) = sharedPreferences.edit { putInt(TOTAL_RESETS, value) }

    override fun resetRecord() {
        totalWins = 0
        totalLosses = 0
        totalGames = 0
        totalResets = 0
    }
}