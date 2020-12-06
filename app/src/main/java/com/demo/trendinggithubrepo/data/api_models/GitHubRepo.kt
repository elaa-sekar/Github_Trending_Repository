package com.demo.trendinggithubrepo.data.api_models

import com.google.gson.annotations.SerializedName

data class GitHubRepo(
    @SerializedName("author") var authorName: String?,
    @SerializedName("name") var repoName: String?,
    @SerializedName("avatar") var iconUrl: String?,
    @SerializedName("url") var repoUrl: String?,
    @SerializedName("description") var description: String?,
    @SerializedName("language") var language: String?,
    @SerializedName("languageColor") var languageColor: String?,
    @SerializedName("stars") var stars: Int?,
    @SerializedName("forks") var forks: Int?,
    @SerializedName("currentPeriodStars") var currentPeriodStars: Int?,
    @SerializedName("builtBy") var contributorsList: List<Contributor>

)