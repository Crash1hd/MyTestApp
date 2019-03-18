@file:Suppress("AndroidUnresolvedRoomSqlReference", "AndroidUnresolvedRoomSqlReference")
package com.mycomp.mytestapp.utilities.log.db

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Data Access Object for the Logs table.
 * https://developer.android.com/training/data-storage/room/accessing-data
 */
@Dao
interface LogDao {
    @Query("SELECT * FROM Logs")
    fun getAllLogs(): List<LogModel>

    @Query("SELECT * FROM Logs")
    fun getAllLiveLogs(): LiveData<List<LogModel>>

    @Query("SELECT LogID FROM Logs")
    fun getALLLogIDs(): List<Long>

//    @Query("SELECT * FROM Logs WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<LogModel>

//    @Query("SELECT * FROM Logs WHERE tag LIKE :tag ORDER BY 'desc' LIMIT 1")
//    fun findByTag(tag: String): LogModel

//    @Query("SELECT * FROM Logs WHERE logID=:logID")
//    fun findById(logID: Long): LogModel

    @Query("SELECT * FROM Logs WHERE logID=:logID")
    fun getLogByID(logID: Long): LogModel

    @Query("SELECT * FROM Logs WHERE logID=:logID")
    fun getLogByIDLive(logID: Long):LiveData<LogModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(vararg logModel: LogModel): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAll(log: List<LogModel>): List<Long>

    @Delete
    fun deleteLog(vararg log: LogModel): Int //This returns the number of logs deleted

    @Query("DELETE FROM Logs")
    fun deleteAll():Int

    //@Query("DELETE FROM Logs WHERE created_date / 1000 < strftime('%s','now','-'||:XDays||' Days')+0;")
    //fun deleteAllLogsOlderThan(XDays: Int): Int

    /***
     * Very Powerful and shows error when in use (but works)
     * This resets the SQLITE Auto Index Value to 1, only uncomment when wanting to use.
     */
    @Query("UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE name = 'Logs'")
    fun resetIndex()
}