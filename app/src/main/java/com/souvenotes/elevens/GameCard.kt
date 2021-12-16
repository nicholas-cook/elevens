package com.souvenotes.elevens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

enum class GameCard(
    @DrawableRes val imageRes: Int,
    val index: Int,
    val value: Int,
    @StringRes val contentDescription: Int
) {
    //Clubs
    ACE_OF_CLUBS(R.drawable.ace_of_clubs, 1, 1, R.string.ace_of_clubs),
    TWO_OF_CLUBS(R.drawable.two_of_clubs, 2, 2, R.string.two_of_clubs),
    THREE_OF_CLUBS(R.drawable.three_of_clubs, 3, 3, R.string.three_of_clubs),
    FOUR_OF_CLUBS(R.drawable.four_of_clubs, 4, 4, R.string.four_of_clubs),
    FIVE_OF_CLUBS(R.drawable.five_of_clubs, 5, 5, R.string.five_of_clubs),
    SIX_OF_CLUBS(R.drawable.six_of_clubs, 6, 6, R.string.six_of_clubs),
    SEVEN_OF_CLUBS(R.drawable.seven_of_clubs, 7, 7, R.string.seven_of_clubs),
    EIGHT_OF_CLUBS(R.drawable.eight_of_clubs, 8, 8, R.string.eight_of_clubs),
    NINE_OF_CLUBS(R.drawable.nine_of_clubs, 9, 9, R.string.nine_of_clubs),
    TEN_OF_CLUBS(R.drawable.ten_of_clubs, 10, 10, R.string.ten_of_clubs),
    JACK_OF_CLUBS(R.drawable.jack_of_clubs, 11, 11, R.string.jack_of_clubs),
    QUEEN_OF_CLUBS(R.drawable.queen_of_clubs, 12, 12, R.string.queen_of_clubs),
    KING_OF_CLUBS(R.drawable.king_of_clubs, 13, 13, R.string.king_of_clubs),

    // Hearts
    ACE_OF_HEARTS(R.drawable.ace_of_hearts, 14, 1, R.string.ace_of_hearts),
    TWO_OF_HEARTS(R.drawable.two_of_hearts, 15, 2, R.string.two_of_hearts),
    THREE_OF_HEARTS(R.drawable.three_of_hearts, 16, 3, R.string.three_of_hearts),
    FOUR_OF_HEARTS(R.drawable.four_of_hearts, 17, 4, R.string.four_of_hearts),
    FIVE_OF_HEARTS(R.drawable.five_of_hearts, 18, 5, R.string.five_of_hearts),
    SIX_OF_HEARTS(R.drawable.six_of_hearts, 19, 6, R.string.six_of_hearts),
    SEVEN_OF_HEARTS(R.drawable.seven_of_hearts, 20, 7, R.string.seven_of_hearts),
    EIGHT_OF_HEARTS(R.drawable.eight_of_hearts, 21, 8, R.string.eight_of_hearts),
    NINE_OF_HEARTS(R.drawable.nine_of_hearts, 22, 9, R.string.nine_of_hearts),
    TEN_OF_HEARTS(R.drawable.ten_of_hearts, 23, 10, R.string.ten_of_hearts),
    JACK_OF_HEARTS(R.drawable.jack_of_hearts, 24, 11, R.string.jack_of_hearts),
    QUEEN_OF_HEARTS(R.drawable.queen_of_hearts, 25, 12, R.string.queen_of_hearts),
    KING_OF_HEARTS(R.drawable.king_of_hearts, 26, 13, R.string.king_of_hearts),

    // Spades
    ACE_OF_SPADES(R.drawable.ace_of_spades, 27, 1, R.string.ace_of_spades),
    TWO_OF_SPADES(R.drawable.two_of_spades, 28, 2, R.string.two_of_spades),
    THREE_OF_SPADES(R.drawable.three_of_spades, 29, 3, R.string.three_of_spades),
    FOUR_OF_SPADES(R.drawable.four_of_spades, 30, 4, R.string.four_of_spades),
    FIVE_OF_SPADES(R.drawable.five_of_spades, 31, 5, R.string.five_of_spades),
    SIX_OF_SPADES(R.drawable.six_of_spades, 32, 6, R.string.six_of_spades),
    SEVEN_OF_SPADES(R.drawable.seven_of_spades, 33, 7, R.string.seven_of_spades),
    EIGHT_OF_SPADES(R.drawable.eight_of_spades, 34, 8, R.string.eight_of_spades),
    NINE_OF_SPADES(R.drawable.nine_of_spades, 35, 9, R.string.nine_of_spades),
    TEN_OF_SPADES(R.drawable.ten_of_spades, 36, 10, R.string.ten_of_spades),
    JACK_OF_SPADES(R.drawable.jack_of_spades, 37, 11, R.string.jack_of_spades),
    QUEEN_OF_SPADES(R.drawable.queen_of_spades, 38, 12, R.string.queen_of_spades),
    KING_OF_SPADES(R.drawable.king_of_spades, 39, 13, R.string.king_of_spades),

    // Diamonds
    ACE_OF_DIAMONDS(R.drawable.ace_of_diamonds, 40, 1, R.string.ace_of_diamonds),
    TWO_OF_DIAMONDS(R.drawable.two_of_diamonds, 41, 2, R.string.two_of_diamonds),
    THREE_OF_DIAMONDS(R.drawable.three_of_diamonds, 42, 3, R.string.three_of_diamonds),
    FOUR_OF_DIAMONDS(R.drawable.four_of_diamonds, 43, 4, R.string.four_of_diamonds),
    FIVE_OF_DIAMONDS(R.drawable.five_of_diamonds, 44, 5, R.string.five_of_diamonds),
    SIX_OF_DIAMONDS(R.drawable.six_of_diamonds, 45, 6, R.string.six_of_diamonds),
    SEVEN_OF_DIAMONDS(R.drawable.seven_of_diamonds, 46, 7, R.string.seven_of_diamonds),
    EIGHT_OF_DIAMONDS(R.drawable.eight_of_diamonds, 47, 8, R.string.eight_of_diamonds),
    NINE_OF_DIAMONDS(R.drawable.nine_of_diamonds, 48, 9, R.string.nine_of_diamonds),
    TEN_OF_DIAMONDS(R.drawable.ten_of_diamonds, 49, 10, R.string.ten_of_diamonds),
    JACK_OF_DIAMONDS(R.drawable.jack_of_diamonds, 50, 11, R.string.jack_of_diamonds),
    QUEEN_OF_DIAMONDS(R.drawable.queen_of_diamonds, 51, 12, R.string.queen_of_diamonds),
    KING_OF_DIAMONDS(R.drawable.king_of_diamonds, 52, 13, R.string.king_of_diamonds),

    NO_CARD(R.drawable.black_joker, -1, -1, R.string.joker);

    companion object {

        fun getGameCardForIndex(index: Int): GameCard {
            values().find { it.index == index }?.let {
                return it
            }
            throw NoSuchElementException("No GameCard exists for index $index")
        }

        fun isFaceCard(gameCard: GameCard): Boolean {
            return gameCard.value >= 11
        }
    }
}