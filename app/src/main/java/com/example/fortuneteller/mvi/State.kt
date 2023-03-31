package com.gadalka.mvi

import com.example.fortuneteller.models.CardModel

data class State(
    val firstCard: CardModel = CardModel(),
    val secondCard: CardModel = CardModel(),
    val thirdCard: CardModel = CardModel(),
    val offset: Int = 0,
    val allCards: ArrayList<CardModel> = arrayListOf(),
    val actualCardId: Int = -1,
    val bottomSheetShown: Boolean = false,
    val isShowInfo: Boolean = false,
    val isVibrate: Boolean = false,
    val isShowAd: Boolean = true
)
