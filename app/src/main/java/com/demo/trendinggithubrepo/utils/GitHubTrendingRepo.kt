package com.demo.trendinggithubrepo.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import com.demo.trendinggithubrepo.BuildConfig
import com.demo.trendinggithubrepo.network.ApiService
import com.demo.trendinggithubrepo.network.NetworkConnectionInterceptor
import com.demo.trendinggithubrepo.repositories.HomeRepository
import com.demo.trendinggithubrepo.ui.home.GitHubTrendingRepoViewModelFactory
import com.demo.trendinggithubrepo.ui.splash.SplashActivity
import com.facebook.stetho.Stetho
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import timber.log.Timber

class GitHubTrendingRepo : Application(), KodeinAware {

    lateinit var networkReceiver: NetworkReceiver
    lateinit var intentFilterNetwork: IntentFilter
    lateinit var appContext: Context

    companion object {
        var networkAction: String = "android.net.conn.CONNECTIVITY_CHANGE"
        lateinit var applicationInstance: GitHubTrendingRepo
    }

    override fun onCreate() {
        super.onCreate()
        applicationInstance = this
        intentFilterNetwork = IntentFilter()
        intentFilterNetwork.addAction(networkAction)

        setContext(applicationContext)

        Timber.plant(Timber.DebugTree())

        if (BuildConfig.DEBUG) {
            //Timber.d("Stetho initialized")
            Stetho.initializeWithDefaults(this)
        }

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
                if (activity.javaClass.simpleName != SplashActivity::class.simpleName) {
                    activity.unregisterReceiver(networkReceiver)
                }
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                //Timber.d("Class name ${activity.javaClass.simpleName} ${SplashScreen::class.simpleName}")

                if (activity.javaClass.simpleName != SplashActivity::class.simpleName) {
                    networkReceiver = NetworkReceiver()
                }

            }

            override fun onActivityResumed(activity: Activity) {
                if (activity.javaClass.simpleName != SplashActivity::class.simpleName) {
                    activity.registerReceiver(networkReceiver, intentFilterNetwork)
                }
            }

        })
    }

    private fun setContext(context: Context) {
        appContext = context
    }

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@GitHubTrendingRepo))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { ApiService(instance()) }

        bind() from singleton { HomeRepository(instance(), instance()) }
        bind() from singleton { GitHubTrendingRepoViewModelFactory(instance()) }
    }
}
