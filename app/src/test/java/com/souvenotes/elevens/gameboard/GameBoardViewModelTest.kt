package com.souvenotes.elevens.gameboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.souvenotes.elevens.MainCoroutineRule
import com.souvenotes.elevens.utils.FakeElevensSharedPreferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GameBoardViewModelTest {

    private val elevensSharedPreferences = FakeElevensSharedPreferences()

    private val possibleIndices = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

    private lateinit var viewModel: GameBoardViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        viewModel = GameBoardViewModel(elevensSharedPreferences)
    }

    @Test
    fun testTheBoard() {
        runBlockingTest {
            var indices = getNewIndices()

            repeat(100000) {
                viewModel.onCardSelected(indices.first)
                viewModel.onCardSelected(indices.second)

                if (viewModel.didWin || viewModel.didLose) {
                    viewModel.resetBoard()
                    indices = getNewIndices()
                    return@repeat
                }

                viewModel.onCardSelected(indices.third)

                if (viewModel.didWin || viewModel.didLose) {
                    viewModel.resetBoard()
                    indices = getNewIndices()
                    return@repeat
                }

                indices = getNewIndices()
            }

            print("Games: ${elevensSharedPreferences.totalGames}, Wins: ${elevensSharedPreferences.totalWins}, Losses: ${elevensSharedPreferences.totalLosses}")
        }
    }

    private fun getNewIndices(): Triple<Int, Int, Int> {
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