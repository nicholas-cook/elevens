package com.souvenotes.elevens.gameboard

import androidx.lifecycle.*
import com.souvenotes.elevens.GameCard
import com.souvenotes.elevens.utils.ElevensSharedPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameBoardViewModel(private val elevensSharedPrefs: ElevensSharedPreferences) : ViewModel() {

    companion object {
        private const val WINNING_PAIR = 11
        private const val JACK_AND_QUEEN = 23
        private const val JACK_AND_KING = 24
        private const val QUEEN_AND_KING = 25
        private const val FULL_FACE_SET = 36
    }

    private val deck = mutableListOf<Int>()

    // A Pair where the first is an Int signifying position on the board
    // and the second is a GameCard signifying the card at the position
    private var selected1 = Pair(-1, GameCard.NO_CARD)
    private var selected2 = Pair(-1, GameCard.NO_CARD)
    private var selected3 = Pair(-1, GameCard.NO_CARD)

    private lateinit var card1: Pair<Boolean, GameCard>
    private lateinit var card2: Pair<Boolean, GameCard>
    private lateinit var card3: Pair<Boolean, GameCard>
    private lateinit var card4: Pair<Boolean, GameCard>
    private lateinit var card5: Pair<Boolean, GameCard>
    private lateinit var card6: Pair<Boolean, GameCard>
    private lateinit var card7: Pair<Boolean, GameCard>
    private lateinit var card8: Pair<Boolean, GameCard>
    private lateinit var card9: Pair<Boolean, GameCard>
    private lateinit var cardList: MutableList<GameCard>

    private val _card1Display = MutableLiveData<Pair<Boolean, GameCard>>()
    val card1Display: LiveData<Pair<Boolean, GameCard>> = _card1Display.distinctUntilChanged()

    private val _card2Display = MutableLiveData<Pair<Boolean, GameCard>>()
    val card2Display: LiveData<Pair<Boolean, GameCard>> = _card2Display.distinctUntilChanged()

    private val _card3Display = MutableLiveData<Pair<Boolean, GameCard>>()
    val card3Display: LiveData<Pair<Boolean, GameCard>> = _card3Display.distinctUntilChanged()

    private val _card4Display = MutableLiveData<Pair<Boolean, GameCard>>()
    val card4Display: LiveData<Pair<Boolean, GameCard>> = _card4Display.distinctUntilChanged()

    private val _card5Display = MutableLiveData<Pair<Boolean, GameCard>>()
    val card5Display: LiveData<Pair<Boolean, GameCard>> = _card5Display.distinctUntilChanged()

    private val _card6Display = MutableLiveData<Pair<Boolean, GameCard>>()
    val card6Display: LiveData<Pair<Boolean, GameCard>> = _card6Display.distinctUntilChanged()

    private val _card7Display = MutableLiveData<Pair<Boolean, GameCard>>()
    val card7Display: LiveData<Pair<Boolean, GameCard>> = _card7Display.distinctUntilChanged()

    private val _card8Display = MutableLiveData<Pair<Boolean, GameCard>>()
    val card8Display: LiveData<Pair<Boolean, GameCard>> = _card8Display.distinctUntilChanged()

    private val _card9Display = MutableLiveData<Pair<Boolean, GameCard>>()
    val card9Display: LiveData<Pair<Boolean, GameCard>> = _card9Display.distinctUntilChanged()

    private val _showLosingDialog = MutableLiveData<Boolean>()
    val showLosingDialog: LiveData<Boolean> = _showLosingDialog.distinctUntilChanged()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading.distinctUntilChanged()

    private val _showWinningDialog = MutableLiveData<Boolean>()
    val showWinningDialog: LiveData<Boolean> = _showWinningDialog.distinctUntilChanged()

    private var currentDeckPosition = 0

    var didWin = false
    var didLose = false

    init {
        for (i in 1..52) {
            deck.add(i)
        }

        resetBoard()
    }

    fun resetBoard(fromRestart: Boolean = false) {
        didLose = false
        didWin = false
        _loading.value = true
        _showLosingDialog.value = false
        _showWinningDialog.value = false

        deck.shuffle()

        card1 = Pair(false, GameCard.getGameCardForIndex(deck[0]))
        card2 = Pair(false, GameCard.getGameCardForIndex(deck[1]))
        card3 = Pair(false, GameCard.getGameCardForIndex(deck[2]))
        card4 = Pair(false, GameCard.getGameCardForIndex(deck[3]))
        card5 = Pair(false, GameCard.getGameCardForIndex(deck[4]))
        card6 = Pair(false, GameCard.getGameCardForIndex(deck[5]))
        card7 = Pair(false, GameCard.getGameCardForIndex(deck[6]))
        card8 = Pair(false, GameCard.getGameCardForIndex(deck[7]))
        card9 = Pair(false, GameCard.getGameCardForIndex(deck[8]))

        cardList = mutableListOf(
            card1.second,
            card2.second,
            card3.second,
            card4.second,
            card5.second,
            card6.second,
            card7.second,
            card8.second,
            card9.second
        )

        currentDeckPosition = 9

        if (!checkForPossibleWin()) {
            resetBoard(fromRestart)
            return
        }

        for (i in 1..9) {
            updateCardSelectedForPosition(i, false)
        }

        if (fromRestart) {
            elevensSharedPrefs.totalResets++
        }

        _loading.value = false
    }

    fun onCardSelected(position: Int) {
        if (_loading.value == true) {
            return
        }
        _loading.value = true
        updateCardSelectedForPosition(position, true)
        if (selected1.first == -1) {
            selected1 = Pair(position, getGameCardForPosition(position))
            _loading.value = false
        } else if (selected1.first != -1 && selected2.first == -1 && selected3.first == -1) {
            val newlySelected = getGameCardForPosition(position)
            when {
                isValidFaceCardSet(selected1.second, newlySelected) -> {
                    // Two face cards selected, user can select one more
                    selected2 = Pair(position, newlySelected)
                    _loading.value = false
                }
                isValidPair(selected1.second, newlySelected) -> {
                    // Valid pair selected, deal two new cards
                    viewModelScope.launch {
                        delay(500)
                        setNewCardForPosition(selected1.first)
                        setNewCardForPosition(position)
                        selected1 = Pair(-1, GameCard.NO_CARD)
                        selected2 = Pair(-1, GameCard.NO_CARD)
                        if (!checkForPossibleWin()) {
                            updateRecord(false)
                            didLose = true
                            _showLosingDialog.postValue(true)
                        }
                        _loading.postValue(false)
                    }
                }
                else -> {
                    viewModelScope.launch {
                        delay(500)
                        // Invalid pair, reset selected state
                        updateCardSelectedForPosition(selected1.first, false)
                        updateCardSelectedForPosition(position, false)
                        selected1 = Pair(-1, GameCard.NO_CARD)
                        selected2 = Pair(-1, GameCard.NO_CARD)
                        _loading.postValue(false)
                    }
                }
            }
        } else if (selected1.first != -1 && selected2.first != -1 && selected3.first == -1) {
            val newlySelected = getGameCardForPosition(position)
            when {
                isValidFaceCardSet(selected1.second, selected2.second, newlySelected) -> {
                    // Three valid face cards selected, deal three new cards
                    viewModelScope.launch {
                        delay(500)
                        setNewCardForPosition(selected1.first)
                        setNewCardForPosition(selected2.first)
                        setNewCardForPosition(position)
                        selected1 = Pair(-1, GameCard.NO_CARD)
                        selected2 = Pair(-1, GameCard.NO_CARD)
                        selected3 = Pair(-1, GameCard.NO_CARD)
                        if (!checkForPossibleWin()) {
                            updateRecord(false)
                            didLose = true
                            _showLosingDialog.postValue(true)
                        }
                        _loading.postValue(false)
                    }
                }
                else -> {
                    viewModelScope.launch {
                        delay(500)
                        updateCardSelectedForPosition(selected1.first, false)
                        updateCardSelectedForPosition(selected2.first, false)
                        updateCardSelectedForPosition(position, false)
                        selected1 = Pair(-1, GameCard.NO_CARD)
                        selected2 = Pair(-1, GameCard.NO_CARD)
                        selected3 = Pair(-1, GameCard.NO_CARD)
                        _loading.postValue(false)
                    }
                }
            }
        }
    }

    private fun getGameCardForPosition(position: Int): GameCard {
        return when (position) {
            1 -> card1.second
            2 -> card2.second
            3 -> card3.second
            4 -> card4.second
            5 -> card5.second
            6 -> card6.second
            7 -> card7.second
            8 -> card8.second
            else -> card9.second
        }
    }

    private fun updateCardSelectedForPosition(position: Int, selected: Boolean) {
        when (position) {
            1 -> {
                card1 = Pair(selected, card1.second)
                _card1Display.postValue(card1)
            }
            2 -> {
                card2 = Pair(selected, card2.second)
                _card2Display.postValue(card2)
            }
            3 -> {
                card3 = Pair(selected, card3.second)
                _card3Display.postValue(card3)
            }
            4 -> {
                card4 = Pair(selected, card4.second)
                _card4Display.postValue(card4)
            }
            5 -> {
                card5 = Pair(selected, card5.second)
                _card5Display.postValue(card5)
            }
            6 -> {
                card6 = Pair(selected, card6.second)
                _card6Display.postValue(card6)
            }
            7 -> {
                card7 = Pair(selected, card7.second)
                _card7Display.postValue(card7)
            }
            8 -> {
                card8 = Pair(selected, card8.second)
                _card8Display.postValue(card8)
            }
            else -> {
                card9 = Pair(selected, card9.second)
                _card9Display.postValue(card9)
            }
        }
    }

    private fun setNewCardForPosition(position: Int) {
        if (currentDeckPosition >= 52) {
            currentDeckPosition++
            if (currentDeckPosition == 61) {
                updateRecord(true)
                didWin = true
                _showWinningDialog.postValue(true)
            }
            return
        }

        val newCard = GameCard.getGameCardForIndex(deck[currentDeckPosition])
        when (position) {
            1 -> {
                card1 = Pair(false, newCard)
                _card1Display.postValue(card1)
            }
            2 -> {
                card2 = Pair(false, newCard)
                _card2Display.postValue(card2)
            }
            3 -> {
                card3 = Pair(false, newCard)
                _card3Display.postValue(card3)
            }
            4 -> {
                card4 = Pair(false, newCard)
                _card4Display.postValue(card4)
            }
            5 -> {
                card5 = Pair(false, newCard)
                _card5Display.postValue(card5)
            }
            6 -> {
                card6 = Pair(false, newCard)
                _card6Display.postValue(card6)
            }
            7 -> {
                card7 = Pair(false, newCard)
                _card7Display.postValue(card7)
            }
            8 -> {
                card8 = Pair(false, newCard)
                _card8Display.postValue(card8)
            }
            else -> {
                card9 = Pair(false, newCard)
                _card9Display.postValue(card9)
            }
        }
        cardList[position - 1] = newCard
        currentDeckPosition++
    }

    private fun isValidFaceCardSet(gameCard1: GameCard, gameCard2: GameCard): Boolean {
        val total = gameCard1.value + gameCard2.value
        return when (gameCard1.value) {
            11 -> {
                total == JACK_AND_QUEEN || total == JACK_AND_KING
            }
            12 -> {
                total == JACK_AND_QUEEN || total == QUEEN_AND_KING
            }
            13 -> {
                total == JACK_AND_KING || total == QUEEN_AND_KING
            }
            else -> false
        }
    }

    private fun isValidFaceCardSet(
        gameCard1: GameCard,
        gameCard2: GameCard,
        gameCard3: GameCard
    ): Boolean {
        return (gameCard1.value + gameCard2.value + gameCard3.value) == FULL_FACE_SET
    }

    private fun isValidPair(gameCard1: GameCard, gameCard2: GameCard): Boolean {
        return (gameCard1.value + gameCard2.value) == WINNING_PAIR
    }

    private fun checkForPossibleWin(): Boolean {
        val ace = cardList.find { it.value == 1 }
        val ten = cardList.find { it.value == 10 }
        if (ace != null && ten != null) {
            return true
        }

        val two = cardList.find { it.value == 2 }
        val nine = cardList.find { it.value == 9 }
        if (two != null && nine != null) {
            return true
        }

        val three = cardList.find { it.value == 3 }
        val eight = cardList.find { it.value == 8 }
        if (three != null && eight != null) {
            return true
        }

        val four = cardList.find { it.value == 4 }
        val seven = cardList.find { it.value == 7 }
        if (four != null && seven != null) {
            return true
        }

        val five = cardList.find { it.value == 5 }
        val six = cardList.find { it.value == 6 }
        if (five != null && six != null) {
            return true
        }

        val jack = cardList.find { it.value == 11 }
        val queen = cardList.find { it.value == 12 }
        val king = cardList.find { it.value == 13 }
        if (jack != null && queen != null && king != null) {
            return true
        }

        return false
    }

    private fun updateRecord(didWin: Boolean) {
        with(elevensSharedPrefs) {
            totalGames++
            if (didWin) {
                totalWins++
            } else {
                totalLosses++
            }
        }
    }
}