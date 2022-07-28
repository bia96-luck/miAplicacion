package com.example.miaplicacion.database.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.miaplicacion.constants.Constants.Companion.COLUMN_NAME_BARCODE_TYPE
import com.example.miaplicacion.constants.Constants.Companion.COLUMN_NAME_CARDS_FAV
import com.example.miaplicacion.constants.Constants.Companion.COLUMN_NAME_ID
import com.example.miaplicacion.constants.Constants.Companion.DATABASE_NAME
import com.example.miaplicacion.constants.Constants.Companion.COLUMN_NAME_DATE
import com.example.miaplicacion.constants.Constants.Companion.COLUMN_NAME_LAST_USE_DATE
import com.example.miaplicacion.constants.Constants.Companion.COLUMN_NAME_RAW_VALUE
import com.example.miaplicacion.constants.Constants.Companion.COLUMN_NAME_STORE_NAME
import com.example.miaplicacion.constants.Constants.Companion.COLUMN_NAME_STORE_NOTES
import com.example.miaplicacion.constants.Constants.Companion.COLUMN_NAME_STORE_TYPE
import com.example.miaplicacion.constants.Constants.Companion.COLUMN_NAME_USE_TIMES
import java.security.cert.CertStore
import java.util.*

@Entity(tableName = DATABASE_NAME)
data class Cards(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = COLUMN_NAME_ID) val id: Int?,
    @ColumnInfo( name = COLUMN_NAME_DATE) val date: Date,
    @ColumnInfo( name = COLUMN_NAME_RAW_VALUE) val rawValue: String,
    @ColumnInfo( name = COLUMN_NAME_BARCODE_TYPE) val barcodeType: String,
    @ColumnInfo( name = COLUMN_NAME_STORE_NAME) val storeName: String,
    @ColumnInfo( name = COLUMN_NAME_STORE_NOTES) val storeNotes: String,
    @ColumnInfo( name = COLUMN_NAME_LAST_USE_DATE) var lastUseDate: Date,
    @ColumnInfo( name = COLUMN_NAME_USE_TIMES, defaultValue = "0") var useTimes: Int,
    @ColumnInfo( name = COLUMN_NAME_STORE_TYPE, defaultValue = "") var storeType: String,
    @ColumnInfo( name = COLUMN_NAME_CARDS_FAV, defaultValue = "false") var cardsFav: Boolean



)
