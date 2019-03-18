package com.mycomp.mytestapp.utilities.log.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Immutable model class for a Logger Entry.
 * https://developer.android.com/training/data-storage/room/defining-data
 *
 * logID                Primary Key of LogID
 * timestamp            Timestamp of the Logger reported
 * @param type          Type of Logger being saved (I,D,E...)
 * @param tag           Name of tag as string
 * @param msg           The Logger string itself
 * @param deviceID      The ID of the Device reporting the Logger
 * @param userID        The ID of the user using the device at time of reporting the Logger
 * @param latitude      The last known latitude location of the Logger reported
 * @param longitude     The last known longitude location of the Logger reported
 */
@Entity(tableName = "Logs")
data class LogModel (
    @NonNull
    @PrimaryKey(autoGenerate = true) var logID: Long = 0,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "tag") var tag: String,
    @ColumnInfo(name = "msg") var msg: String,
    @ColumnInfo(name = "tr") var tr: String?,
    @ColumnInfo(name = "device_id") var deviceID: Int,
    @ColumnInfo(name = "user_id") var userID: Int,
    @ColumnInfo(name = "latitude") var latitude: Double,
    @ColumnInfo(name = "longitude") var longitude: Double
) {
    //@ColumnInfo(name = "created_date")
    //@TypeConverters(Converters::class)
    //var createDate: LocalDateTime? = LocalDateTime.now()
    //override fun toString(): String {
        //return "$logID: [$tag] $msg @ $createDate\n"
    //}
}