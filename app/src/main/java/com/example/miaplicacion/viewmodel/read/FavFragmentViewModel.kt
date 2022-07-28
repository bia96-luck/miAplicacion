package com.example.miaplicacion.viewmodel.read

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miaplicacion.database.CardRepository
import com.example.miaplicacion.database.user.Cards

class FavFragmentViewModel(private val cardRepository: CardRepository) : ViewModel()  {

    private val _text = MutableLiveData<String>().apply {
        value = "Tarjetas favoritas"
    }
    val text: LiveData<String> = _text
    val favCards: LiveData<List<Cards>>
        get() = cardRepository.getFav()


    class Factory(private val cardRepository: CardRepository) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavFragmentViewModel(cardRepository) as T

        }
    }


}