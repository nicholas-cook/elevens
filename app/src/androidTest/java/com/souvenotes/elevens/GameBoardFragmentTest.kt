package com.souvenotes.elevens

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.action.ViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.souvenotes.elevens.gameboard.GameBoardFragment
import com.souvenotes.elevens.pages.GameBoardPage
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
@RunWith(AndroidJUnit4::class)
class GameBoardFragmentTest {

    private fun getNavController() =
        TestNavHostController(ApplicationProvider.getApplicationContext()).apply {
            runOnUiThread {
                setGraph(R.navigation.nav_graph_game_board)
                setCurrentDestination(R.id.gameBoardFragment)
            }
        }

    private fun launchScenario(): FragmentScenario<GameBoardFragment> {
        return launchFragmentInContainer(
            null,
            R.style.Theme_MaterialComponents_DayNight_NoActionBar,
            GameBoardFragmentFactory(getNavController())
        )
    }

    @Test
    fun testGame() {
        launchScenario()

        var indices = GameBoardPage.getNewIndices()
        repeat(5000) {
            try {
                GameBoardPage.clickOnCardForPosition(indices.first)
                GameBoardPage.clickOnCardForPosition(indices.second)
                GameBoardPage.clickOnCardForPosition(indices.third)
            } catch (e: Exception) {
                GameBoardPage.endGameDialog.perform(ViewActions.click())
            } finally {
                indices = GameBoardPage.getNewIndices()
            }
        }

        print("ALMOST DONE")
    }
}

@KoinApiExtension
class GameBoardFragmentFactory(private val navController: TestNavHostController) :
    FragmentFactory() {

    private fun setUpScenario(
        fragment: Fragment,
        testNavHostController: TestNavHostController
    ): Fragment {
        fragment.viewLifecycleOwnerLiveData.observeForever {
            it?.let {
                Navigation.setViewNavController(fragment.requireView(), testNavHostController)
            }
        }
        return fragment
    }

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val gameBoardFragment = GameBoardFragment()
        setUpScenario(gameBoardFragment, navController)
        return gameBoardFragment
    }
}