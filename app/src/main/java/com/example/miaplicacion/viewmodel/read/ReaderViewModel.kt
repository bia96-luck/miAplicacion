package com.example.miaplicacion.viewmodel.read

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miaplicacion.database.CardRepository
import com.example.miaplicacion.usecases.CreateBitMap
import com.example.miaplicacion.viewmodel.extensionfunctions.toByteArray

class ReaderViewModel(private val cardRepository: CardRepository) : ViewModel() {

    private val _byteArray = MutableLiveData<ByteArray>()
    val byteArray: LiveData<ByteArray> get() = _byteArray

    fun setByteArray(rawValue:String, barcodeType:String){
        _byteArray.value = rawValue.let {

            CreateBitMap.createBitmap(it,barcodeType).toByteArray()
        }
    }

    class ReaderViewModelFactory(private val repository: CardRepository) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ReaderViewModel(repository) as T

        }
    }



}