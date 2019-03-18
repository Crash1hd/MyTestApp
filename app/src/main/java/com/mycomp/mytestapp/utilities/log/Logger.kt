package com.mycomp.mytestapp.utilities.log

import android.util.Log
import com.mycomp.mytestapp.BuildConfig
import com.mycomp.mytestapp.utilities.log.db.LogModel
import com.mycomp.mytestapp.utilities.log.db.LogRepository

//Custom Logger object
object Logger {

    private lateinit var logRepo: LogRepository

    fun i(tag: String, msg: String, tr: Throwable? = null): Int  {
        insertIntoLogDatabase(createLogModel("i", tag, msg, tr))
        return if (BuildConfig.DEBUG) Log.i(tag, msg, tr) else 0
    }

    fun e(tag: String, msg: String, tr: Throwable? = null): Int  {
        insertIntoLogDatabase(createLogModel("e", tag, msg, tr))
        return if (BuildConfig.DEBUG) Log.e(tag, msg, tr) else 0
    }

    fun d(tag: String, msg: String, tr: Throwable? = null): Int  {
        insertIntoLogDatabase(createLogModel("d", tag, msg, tr))
        return if (BuildConfig.DEBUG) Log.d(tag, msg, tr) else 0
    }

    fun v(tag: String, msg: String, tr: Throwable? = null): Int {
        insertIntoLogDatabase(createLogModel("v", tag, msg, tr))
        return if (BuildConfig.DEBUG) Log.v(tag, msg, tr) else 0
    }

    fun w(tag: String, msg: String, tr: Throwable? = null): Int  {
        insertIntoLogDatabase(createLogModel("w", tag, msg, tr))
        return if (BuildConfig.DEBUG) Log.w(tag, msg, tr) else 0
    }

    fun purgeOldLogsGreaterThan(xDays: Int) {
        println("Purging Database")

        logRepo.deleteAllLogsOlderThan(xDays)
    }

    /***
     * This gets an instance of the log repository
     * https://blog.indoorway.com/how-to-reproduce-dagger-functions-or-develop-your-own-di-in-kotlin-b8dfd5cdea0c
     * //Not Directly Related but great article all the same (difference between singletons and non-singletons)
     * @param logRepo   The Logger Repository
     */
    fun setLogRepository(logRepo: LogRepository) {
        this.logRepo = logRepo
    }

    /***
     * Creates the LogModel
     * TODO: Add in DeviceID, userID, Latitude, Longitude
     */
    private fun createLogModel(type: String, tag: String, msg: String, tr: Throwable?) =
        LogModel(
            0,
            type,
            tag,
            msg,
            if (tr != null) tr.message + "\n" + tr.stackTrace!!.contentToString() else null,
            0,
            0,
            0.0,
            0.0
        )

    private fun insertIntoLogDatabase(log: LogModel) {
        //Insert into Logger DB
        logRepo.upsert(log)
    }
}
