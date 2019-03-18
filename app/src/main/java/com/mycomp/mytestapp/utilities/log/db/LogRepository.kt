package com.mycomp.mytestapp.utilities.log.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

interface LogRepository {

    @WorkerThread
    fun getAllLogs(): List<LogModel>

    @WorkerThread
    fun getAllLiveLogs(): LiveData<List<LogModel>>

    @WorkerThread
    fun upsert(logModel: LogModel): List<Long>

    @WorkerThread
    fun upsertAll(logModel: List<LogModel>): List<Long>

    @WorkerThread
    fun deleteAll(): Int

    @WorkerThread
    fun deleteAllLogsOlderThan(xDays: Int)

}