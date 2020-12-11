package com.demo.trendinggithubrepo.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RepoDao {

    @Query("SELECT * FROM trending_repositories")
    fun getAllRepos(): LiveData<List<TrendingRepositories>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(trendingRepositories: List<TrendingRepositories>)

    @Query("SELECT * FROM trending_repositories WHERE author = :searchKey")
    fun findBySearchKey(searchKey: String): LiveData<List<TrendingRepositories>>

    @Query("DELETE FROM trending_repositories")
    fun deleteAll()
}