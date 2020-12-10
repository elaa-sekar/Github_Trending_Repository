package com.demo.trendinggithubrepo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.demo.trendinggithubrepo.data.api_models.GitHubRepo
import com.demo.trendinggithubrepo.data.database.TrendingRepositories

fun isInternetAvailable(context: Context): Boolean {

    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    connectivityManager?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(
                        NetworkCapabilities.TRANSPORT_CELLULAR
                    ) -> true
                    else -> false
                }
            }
        } else {
            it.activeNetworkInfo?.let { networkInfo ->
                result = when (networkInfo.type) {
                    ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_MOBILE -> true
                    else -> false
                }
            }
        }
    }
    return result
}

//API Model into Database Model
fun List<GitHubRepo>.toTrendingRepositories() : List<TrendingRepositories>{

    val trendingRepositoriesList = ArrayList<TrendingRepositories>()
    for(index in this.indices){
        this[index].apply {
            val trendingRepo = TrendingRepositories(
                authorName = authorName,
                repoName = repoName,
                iconUrl = iconUrl,
                repoUrl = repoUrl,
                description = description,
                language = language,
                languageColor = languageColor,
                stars = stars,
                forks = forks,
                currentPeriodStars = currentPeriodStars,
                id = 0
            )
            trendingRepositoriesList.add(trendingRepo)
        }
    }
    return trendingRepositoriesList
}