package com.example.miaplicacion.viewmodel.read

import androidx.lifecycle.*
import com.example.miaplicacion.database.CardRepository
import com.example.miaplicacion.database.user.Cards
import kotlinx.coroutines.launch
import java.util.*

class FragmentCardViewModel(private val cardRepository: CardRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    val text: LiveData<String> = _text


    private val _allCards = MutableLiveData<List<Cards>>()

    val allCards : LiveData<List<Cards>>
        get()=cardRepository.getAllCards()

    init {
        getAllCards()
    }

    private fun getAllCards() = viewModelScope.launch {
        _allCards.value = cardRepository.getAllCards().value

    }

    fun insertCard(cards: Cards) = viewModelScope.launch {
        cardRepository.insertCard(cards)

    }

    fun setFav(cards: Cards) = viewModelScope.launch {
        if (cards.cardsFav ){
            cards.let {
                cards.cardsFav = false
                cardRepository.updateCards(it)
            }
        }else{
            cards.let {
                cards.cardsFav = true
                cardRepository.updateCards(it)
            }
        }
    }

    fun useCard(cards: Cards) = viewModelScope.launch {
        val date = (Calendar.getInstance()).time
        cards.lastUseDate = date
        cards.useTimes = cards.useTimes.plus(1)
        cards.let {
            cardRepository.updateCards(it)
        }
    }

    class FragmentCardViewModelFactory(private val repository: CardRepository) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FragmentCardViewModel(repository) as T

        }
    }

}