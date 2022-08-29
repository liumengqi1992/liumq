package com.deepblue.liblog.database

import android.arch.persistence.room.*


@Dao
interface MqttMsgDao {

    @get:Query("SELECT * FROM mqttmsg ORDER BY mid ")
    val all: List<MqttMsg>


    @Query("SELECT * FROM mqttmsg ORDER BY mid LIMIT 1")
    fun getFirst(): MqttMsg


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mqtt: MqttMsg): Long?


    @Delete
    fun delete(mqtt: MqttMsg): Int
}