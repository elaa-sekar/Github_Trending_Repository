package com.demo.trendinggithubrepo.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.demo.trendinggithubrepo.R
import com.demo.trendinggithubrepo.databinding.ActivityTrendingGithubRepoBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class GitHubTrendingRepoActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory by instance<GitHubTrendingRepoViewModelFactory>()
    lateinit var binding: ActivityTrendingGithubRepoBinding
    lateinit var viewModel: GitHubTrendingRepoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trending_github_repo)
        viewModel = ViewModelProvider(this, factory).get(GitHubTrendingRepoViewModel::class.java)
    }
}