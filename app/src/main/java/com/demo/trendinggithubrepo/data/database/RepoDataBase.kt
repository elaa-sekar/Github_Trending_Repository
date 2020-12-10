package com.demo.trendinggithubrepo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TrendingRepositories::class], version = 1)
abstract class RepoDataBase : RoomDatabase() {
    abstract fun repoDao() : RepoDao
}