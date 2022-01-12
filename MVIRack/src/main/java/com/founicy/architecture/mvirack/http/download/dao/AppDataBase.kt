package com.founicy.architecture.mvirack.http.download.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.founicy.architecture.mvirack.http.download.DownloadInfo

@Database(entities = [DownloadInfo::class], version = 1)
abstract class AppDataBase:RoomDatabase() {
    abstract fun downloadDao(): DownloadDao
}