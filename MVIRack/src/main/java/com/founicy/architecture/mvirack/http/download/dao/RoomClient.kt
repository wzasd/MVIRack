package com.founicy.architecture.mvirack.http.download.dao

import androidx.room.Room
import androidx.room.migration.Migration
import com.founicy.architecture.mvirack.utils.Utils
import com.jeremyliao.liveeventbus.utils.AppUtils

object RoomClient {
    private const val DATA_BASE_NAME = "download.db"

    val dataBase: AppDataBase by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        Room
            .databaseBuilder(
                Utils.getContext(),
                AppDataBase::class.java,
                DATA_BASE_NAME
            )
            .build()
    }

    private fun createMigrations(): Array<Migration> {
        return arrayOf()
    }

}