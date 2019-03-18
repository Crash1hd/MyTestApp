package com.mycomp.mytestapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mycomp.mytestapp.utilities.Converters
import com.mycomp.mytestapp.utilities.log.db.LogDao
import com.mycomp.mytestapp.utilities.log.db.LogModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Database Creation
 * https://developer.android.com/training/data-storage/room
 */
@Database(entities = [LogModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class MYTESTAPPRoomDatabase : RoomDatabase() {
    //Database to be initialised here
    abstract fun logDao(): LogDao

    companion object {
        /***
         * Volatile: Writes to this field are immediately made visible to other threads.
         */
        @Volatile
        private var instance: MYTESTAPPRoomDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) =
            MYTESTAPPRoomDatabase.instance ?: synchronized(MYTESTAPPRoomDatabase.LOCK) {
                MYTESTAPPRoomDatabase.instance
                    ?: MYTESTAPPRoomDatabase.buildDatabase(context).also { MYTESTAPPRoomDatabase.instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MYTESTAPPRoomDatabase::class.java,
                "MY_Test_Database.db"
            ).addCallback(MYPROGDatabaseCallback()).build()

        fun getMemoryInstance(context: Context): MYTESTAPPRoomDatabase {
            return Room.inMemoryDatabaseBuilder(context, MYTESTAPPRoomDatabase::class.java).build()
        }
    }

    //This Wipes the database and pre populates it
    private class MYPROGDatabaseCallback : RoomDatabase.Callback() {
        private var parentJob = Job()
        private val coroutineContext: CoroutineContext
            get() = parentJob + Dispatchers.Main

        private val scope: CoroutineScope = CoroutineScope(coroutineContext)

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            instance?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.logDao())
                }
            }
        }

        private fun populateDatabase(logDao: LogDao) {
            //logDao.deleteAll()
            logDao.resetIndex()

            var tmpLog = LogModel(1, "d", "Test", "Hello", null, 0, 0, 0.0, 0.0)
            logDao.upsert(tmpLog)
            tmpLog = LogModel(2, "d", "Test", "World!", null, 0, 0, 0.0, 0.0)
            logDao.upsert(tmpLog)
            println("Populated Database")
        }
    }
}