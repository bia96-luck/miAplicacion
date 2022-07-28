package com.example.miaplicacion.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.miaplicacion.database.user.Cards
import com.example.miaplicacion.database.user.CardsDAO


class CardRepository (private val cardDao: CardsDAO){

    fun getAllCards(): LiveData<List<Cards>> = cardDao.getAllCards()

    fun getFav(): LiveData<List<Cards>> = cardDao.getFav()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertCard(cards: Cards) {
        cardDao.insertNewCard(cards)
        getAllCards()
    }

    suspend fun updateCards(cards: Cards){
        cardDao.updateCard(cards)
    }

    suspend fun deleteCard(cards: Cards) {
        cardDao.deleteCard(cards)
    }





}