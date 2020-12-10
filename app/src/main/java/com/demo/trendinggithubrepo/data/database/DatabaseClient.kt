package com.demo.trendinggithubrepo.data.database

import android.content.Context
import androidx.room.Room

class DatabaseClient(var context: Context) {

    //creating the app database with Room database builder
    //our app database object
    private val repoDatabase: RepoDataBase =
        Room.databaseBuilder(context, RepoDataBase::class.java, "TrendingRepo")
            .fallbackToDestructiveMigration().build()

    fun getRepoDatabase(): RepoDataBase {
        return repoDatabase
    }

    companion object {
        private var databaseClient: DatabaseClient? = null

        @Synchronized
        fun getInstance(context: Context): DatabaseClient? {
            if (databaseClient == null) {
                databaseClient = DatabaseClient(context)
            }
            return databaseClient
        }
    }
}