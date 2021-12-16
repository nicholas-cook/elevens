package com.souvenotes.elevens.pages

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.souvenotes.elevens.R

object GameBoardPage {

    val card1: ViewInteraction = Espresso.onView(withId(R.id.card_1))
    val card2: ViewInteraction = Espresso.onView(withId(R.id.card_2))
    val card3: ViewInteraction = Espresso.onView(withId(R.id.card_3))
    val card4: ViewInteraction = Espresso.onView(withId(R.id.card_4))
    val card5: ViewInteraction = Espresso.onView(withId(R.id.card_5))
    val card6: ViewInteraction = Espresso.onView(withId(R.id.card_6))
    val card7: ViewInteraction = Espresso.onView(withId(R.id.card_7))
    val card8: ViewInteraction = Espresso.onView(withId(R.id.card_8))
    val card9: ViewInteraction = Espresso.onView(withId(R.id.card_9))

    val endGameDialog = Espresso.onView(withText(R.string.play_again)).inRoot(isDialog())

    fun clickOnCardForPosition(position: Int) {
        when (position) {
            1 -> card1.perform(ViewActions.click())
            2 -> card2.perform(ViewActions.click())
            3 -> card3.perform(ViewActions.click())
            4 -> card4.perform(ViewActions.click())
            5 -> card5.perform(ViewActions.click())
            6 -> card6.perform(ViewActions.click())
            7 -> card7.perform(ViewActions.click())
            8 -> card8.perform(ViewActions.click())
            else -> card9.perform(ViewActions.click())
        }
    }

    private val possibleIndices = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

    fun getNewIndices(): Triple<Int, Int, Int> {
        val index1 = possibleIndices.random()
        var index2 = possibleIndices.random()
        while (index2 == index1) {
            index2 = possibleIndices.random()
        }
        var index3 = possibleIndices.random()
        while (index3 == index1 || index3 == index2) {
            index3 = possibleIndices.random()
        }
        return Triple(index1, index2, index3)
    }
}