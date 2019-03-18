package com.mycomp.mytestapp

import android.annotation.SuppressLint
import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mycomp.mytestapp.data.MYTESTAPPRoomDatabase
import com.mycomp.mytestapp.utilities.log.Logger
import com.mycomp.mytestapp.utilities.log.db.LogDao
import com.mycomp.mytestapp.utilities.log.db.LogRepository
import com.mycomp.mytestapp.utilities.log.db.LogRepositoryImpl
import com.mycomp.mytestapp.utilities.log.ui.LogViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

@SuppressLint("Registered")
class MYTESTAPPApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MYTESTAPPApplication))

        //Bind the Database
        bind<MYTESTAPPRoomDatabase>() with singleton { MYTESTAPPRoomDatabase(instance()) }

        bind<LogDao>() with singleton { instance<MYTESTAPPRoomDatabase>().logDao() }

        bind<LogRepository>() with singleton {
            LogRepositoryImpl(
                instance()
            )
        }

        bind() from provider { LogViewModelFactory(instance()) }
    }

    //private lateinit var logViewModel: LogViewModel

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)

        initializeLogging()
    }

    private fun initializeLogging() {

        //val logViewModelFactory:LogViewModelFactory by instance()

        //logViewModel = logViewModelFactory.create(LogViewModel::class.java)
        val logRepository: LogRepository by instance()

        Logger.setLogRepository(logRepository)

        Logger.purgeOldLogsGreaterThan(7)

    }
}