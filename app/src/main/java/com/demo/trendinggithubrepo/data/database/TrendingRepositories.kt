package com.demo.trendinggithubrepo.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "trending_repositories")
data class TrendingRepositories(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "author") val authorName: String?,
    @ColumnInfo(name = "name") val repoName: String?,
    @ColumnInfo(name = "avatar") val iconUrl: String?,
    @ColumnInfo(name = "url") val repoUrl: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "language") val language: String?,
    @ColumnInfo(name = "language_color") val languageColor: String?,
    @ColumnInfo(name = "stars") val stars: Int?,
    @ColumnInfo(name = "forks") val forks: Int?,
    @ColumnInfo(name = "current_period_stars") val currentPeriodStars: Int?
) : Serializable