package com.example.miaplicacion.database.user

import android.content.Context
import androidx.room.*
import com.example.miaplicacion.constants.Constants
import com.example.miaplicacion.database.Converters
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Cards::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 2, to = 3)]
)
@TypeConverters(Converters::class)
abstract class CardsDataBase: RoomDatabase() {

    abstract fun cardsDao(): CardsDAO

    private class CardsDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {


    }companion object {
            @Volatile
            private var INSTANCE: CardsDataBase? = null

            fun getDatabase(context: Context, scope: CoroutineScope): CardsDataBase {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        CardsDataBase::class.java,
                        Constants.DATABASE_NAME
                    )
                        .addCallback(CardsDatabaseCallback(scope))
                        .build()
                    INSTANCE = instance
                    instance
                }

            }
        }
}