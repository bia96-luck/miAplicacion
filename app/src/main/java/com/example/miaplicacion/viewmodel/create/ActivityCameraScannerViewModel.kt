package com.example.miaplicacion.viewmodel.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.miaplicacion.database.CardRepository
import com.example.miaplicacion.database.user.Cards
import kotlinx.coroutines.launch
import java.util.*

class ActivityCameraScannerViewModel(private val cardRepository: CardRepository): ViewModel() {

    private fun insertCard(cards: Cards) = viewModelScope.launch {
        cardRepository.insertCard(cards)
    }

    lateinit var cards: Cards

    lateinit var rawValue: String
    lateinit var storeType: String
    lateinit var storeName: String
    lateinit var storeNotes: String
    lateinit var barcodeType: String

    fun setNewCard() {
        val newCard = Cards(
            null,
            ((Calendar.getInstance()).time),
            rawValue,
            barcodeType,
            storeName,
            storeNotes,
            ((Calendar.getInstance()).time),
            0,
            storeType,
            false
        )
        Log.d("db check","${newCard.rawValue}")

        insertCard(newCard)

    }

    class Factory(private val repository: CardRepository) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ActivityCameraScannerViewModel(repository) as T

        }
    }

}