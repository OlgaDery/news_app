package com.bonial.newsapp.database

import android.content.Context
import androidx.room.*
import com.bonial.newsapp.model.NewsItem

@Database(entities = [NewsItem::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun daoActions(): DaoActions

    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "database.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }
}

@Dao
interface DaoActions {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: List<NewsItem?>?)

    @Query("SELECT * FROM `table`")
    fun getAll(): List<NewsItem?>?

    @Query("DELETE FROM `table`")
    fun deleteAll()

}