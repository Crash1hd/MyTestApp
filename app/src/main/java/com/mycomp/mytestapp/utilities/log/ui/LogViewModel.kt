package com.mycomp.mytestapp.utilities.log.ui

import androidx.lifecycle.ViewModel
import com.mycomp.mytestapp.utilities.lazyDeferred
import com.mycomp.mytestapp.utilities.log.db.LogModel
import com.mycomp.mytestapp.utilities.log.db.LogRepository
import kotlinx.coroutines.runBlocking

class LogViewModel(private val logRepository: LogRepository): ViewModel() {

    val getAllLogs by lazyDeferred {
        logRepository.getAllLogs()
    }

    val getAllLiveLogs by lazyDeferred {
        logRepository.getAllLiveLogs()
    }

    fun upsert(logModel: LogModel):List<Long> = runBlocking {
        logRepository.upsert(logModel)
    }

    //fun deleteAllLogsOlderThan(xDays: Int) = logRepository.deleteAllLogsOlderThan(xDays)
}