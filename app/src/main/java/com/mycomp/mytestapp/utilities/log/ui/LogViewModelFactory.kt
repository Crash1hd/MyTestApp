package com.mycomp.mytestapp.utilities.log.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mycomp.mytestapp.utilities.log.db.LogRepository

// The same repository that's needed for LogsViewModel
// is also passed to the factory
class LogViewModelFactory(private val logRepository: LogRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LogViewModel(logRepository) as T
    }
}