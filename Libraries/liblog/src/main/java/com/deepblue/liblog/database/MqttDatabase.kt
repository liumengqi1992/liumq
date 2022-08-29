package com.deepblue.liblog.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context


@Database(entities = [MqttMsg::class], version = 1)
abstract class MqttDatabase : RoomDatabase(){

    abstract fun getMqttDao(): MqttMsgDao

    companion object {
        val DB_NAME = "MqttLog.db"
        private var INSTANCE: MqttDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MqttDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, MqttDatabase::class.java, DB_NAME).allowMainThreadQueries().build()
            }
            return INSTANCE!!
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }

}
