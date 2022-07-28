package com.example.miaplicacion.viewmodel.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miaplicacion.database.CardRepository

class ModifyCardViewModel(private val repository: CardRepository) : ViewModel() {


    class FragmentAddCardViewModelFactory(private val repository: CardRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ModifyCardViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ModifyCardViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}