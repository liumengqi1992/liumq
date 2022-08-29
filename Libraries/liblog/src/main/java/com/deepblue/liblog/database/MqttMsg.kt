package com.deepblue.liblog.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "mqttmsg", indices = [])
class MqttMsg {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mid")
    var id: Long = 0

    @ColumnInfo(name = "message")
    var message: String = ""

    @ColumnInfo(name = "time")
    var sendTime:String=""


    override fun toString(): String {
        return "mqttmsg{" +
                "mid=" + id +
                "time=" + sendTime +
                ", message='" + message + '\''.toString() +
                '}'.toString()
    }
}