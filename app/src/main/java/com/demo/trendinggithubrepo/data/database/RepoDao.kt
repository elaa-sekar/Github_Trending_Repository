package com.demo.trendinggithubrepo.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RepoDao {

    @Query("SELECT * FROM trending_repositories")
    fun getAllRepos(): LiveData<List<TrendingRepositories>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(trendingRepositories: List<TrendingRepositories>): LiveData<List<TrendingRepositories>>

    @Query("SELECT * FROM trending_repositories WHERE author LIKE :searchKey OR name LIKE :searchKey OR description LIKE :searchKey OR language LIKE :searchKey")
    fun findBySearchKey(searchKey: String): LiveData<List<TrendingRepositories>>

}