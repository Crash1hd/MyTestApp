package com.mycomp.mytestapp.utilities.log.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.coroutines.CoroutineContext

class LogRepositoryImpl(private val logDao: LogDao): LogRepository, CoroutineScope {
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job

//Future Use?
//    fun cancelCoroutines() {
//        job.cancel()
//    }

    override fun getAllLogs(): List<LogModel> = runBlocking {
        withContext(Dispatchers.IO) {
            return@withContext logDao.getAllLogs()
        }
    }

    override fun getAllLiveLogs(): LiveData<List<LogModel>> = runBlocking {
        withContext(Dispatchers.IO) {
            return@withContext logDao.getAllLiveLogs()
        }
    }

    override fun upsert(logModel: LogModel): List<Long> = runBlocking {
        withContext(Dispatchers.IO) {
            return@withContext logDao.upsert(logModel)
        }
    }

    override fun upsertAll(logModel: List<LogModel>): List<Long> = runBlocking {
        withContext(Dispatchers.IO) {
            return@withContext logDao.upsertAll(logModel)
        }
    }

    @WorkerThread
    override fun deleteAllLogsOlderThan(xDays: Int) {
        launch(IO) {
            //val deletedLogs = logDao.deleteAllLogsOlderThan(xDays)
            //Logger.i("Logger", "Purging $deletedLogs logs > $xDays days old")
        }
    }

    @WorkerThread
    override fun deleteAll(): Int = runBlocking {
        withContext(Dispatchers.IO) {
            return@withContext logDao.deleteAll()
        }
    }

    // Creates a Singleton (Not needed when using kodein) (Left in for testing ability)
    companion object {
        @Volatile
        private var INSTANCE: LogRepositoryImpl? = null

        fun getInstance(logDao: LogDao): LogRepositoryImpl = runBlocking {
            withContext(Dispatchers.Default) {
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                        ?: LogRepositoryImpl(logDao).also { INSTANCE = it }
                }
            }
        }
    }
}