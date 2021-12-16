package com.souvenotes.elevens.utils

class FakeElevensSharedPreferences : ElevensSharedPreferences {

    private var totalWinsLocal = 0
    override var totalWins: Int
        get() = totalWinsLocal
        set(value) {
            totalWinsLocal = value
        }

    private var totalLossesLocal = 0
    override var totalLosses: Int
        get() = totalLossesLocal
        set(value) {
            totalLossesLocal = value
        }

    private var totalGamesLocal = 0
    override var totalGames: Int
        get() = totalGamesLocal
        set(value) {
            totalGamesLocal = value
        }

    private var totalResetsLocal = 0
    override var totalResets: Int
        get() = totalResetsLocal
        set(value) {
            totalResetsLocal = value
        }

    override fun resetRecord() {
        totalWinsLocal = 0
        totalLossesLocal = 0
        totalGamesLocal = 0
        totalGamesLocal = 0
    }
}