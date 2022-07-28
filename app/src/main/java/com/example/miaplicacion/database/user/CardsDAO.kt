package com.example.miaplicacion.database.user

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.miaplicacion.constants.Constants

@Dao
interface CardsDAO {

    @Insert
    suspend fun insertNewCard(cards: Cards)

    @Delete
    suspend fun deleteCard(cards: Cards)

    @Update
    suspend fun updateCard(cards: Cards)

    @Query("SELECT * FROM ${Constants.DATABASE_NAME} ORDER BY ${Constants.COLUMN_NAME_USE_TIMES} DESC")
    fun getAllCards(): LiveData<List<Cards>>

    @Query("SELECT * FROM ${Constants.DATABASE_NAME} WHERE ${Constants.COLUMN_NAME_CARDS_FAV} LIKE 1")
    fun getFav(): LiveData<List<Cards>>





}