package com.bonial.newsapp.database

import android.content.Context
import androidx.room.*

@Database(entities = [DataToSave::class], version = 1, exportSchema = false)
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

//    override fun clearAllTables() {
//    }
}

@Entity(tableName = "table")
data class DataToSave(@PrimaryKey var key: String, @ColumnInfo(name = "value") var value: String)

@Dao
interface DaoActions {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: DataToSave)

    @Query("SELECT * FROM `table` WHERE [key] = :key")
    fun get(key: String): DataToSave?

}