package com.demo.trendinggithubrepo.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RepoDao {

    @get:Query("SELECT * FROM trending_repositories")
    val allRepos: LiveData<List<TrendingRepositories>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(trendingRepositories: List<TrendingRepositories>)

    @Query("SELECT * FROM trending_repositories WHERE author LIKE  '%' || :searchKey || '%' OR language LIKE '%' || :searchKey || '%' OR name LIKE '%' || :searchKey || '%'")
    fun findBySearchKey(searchKey: String): List<TrendingRepositories>

    @Query("DELETE FROM trending_repositories")
    fun deleteAll()
}