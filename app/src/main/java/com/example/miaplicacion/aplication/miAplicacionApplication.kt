package com.example.miaplicacion.aplication

import android.app.Application
import com.example.miaplicacion.database.CardRepository
import com.example.miaplicacion.database.user.CardsDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class miAplicacionApplication: Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { CardsDataBase.getDatabase(this , applicationScope) }

    val repository by lazy { CardRepository(database.cardsDao()) }
}