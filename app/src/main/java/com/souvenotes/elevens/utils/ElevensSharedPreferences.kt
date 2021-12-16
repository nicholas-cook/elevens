package com.souvenotes.elevens.utils

interface ElevensSharedPreferences {

    companion object {
        const val TOTAL_WINS = "TOTAL_WINS"
        const val TOTAL_LOSSES = "TOTAL_LOSSES"
        const val TOTAL_GAMES = "TOTAL_GAMES"
        const val TOTAL_RESETS = "TOTAL_RESETS"
    }

    var totalWins: Int
    var totalLosses: Int
    var totalGames: Int
    var totalResets: Int

    fun resetRecord()
}