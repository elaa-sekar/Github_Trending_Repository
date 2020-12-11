package com.demo.trendinggithubrepo.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.trendinggithubrepo.R
import com.demo.trendinggithubrepo.data.api_models.GitHubRepo
import com.demo.trendinggithubrepo.data.database.TrendingRepositories
import com.demo.trendinggithubrepo.databinding.ActivityTrendingGithubRepoBinding
import com.demo.trendinggithubrepo.utils.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import timber.log.Timber

class GitHubTrendingRepoActivity : AppCompatActivity(), KodeinAware, GitHubRepoListener {

    override val kodein by kodein()
    private val factory by instance<GitHubTrendingRepoViewModelFactory>()
    lateinit var binding: ActivityTrendingGithubRepoBinding
    lateinit var viewModel: GitHubTrendingRepoViewModel
    var repoAdapter: GitHubRepoAdapter? = null
    lateinit var repoObserver: Observer<List<TrendingRepositories>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trending_github_repo)
        viewModel = ViewModelProvider(this, factory).get(GitHubTrendingRepoViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.listener = this
        initSwipeToRefresh()
        initRepoAdapter()
        initRepoDataObserver()
        getGitHubTrendingRepositories()
        initSearchTextWatcher()
    }

    private fun initRepoAdapter() {
        binding.rvRepositories.layoutManager = LinearLayoutManager(this@GitHubTrendingRepoActivity)
        repoAdapter = GitHubRepoAdapter(ArrayList(), this)
        binding.rvRepositories.adapter = repoAdapter
    }

    private fun initRepoDataObserver() {
        repoObserver = Observer {
            Timber.d("Repo Size ${it.size}")
            repoAdapter?.notifyUpdatedList(it as ArrayList<TrendingRepositories>)
        }
        viewModel.getRepos().observe(this, repoObserver)
    }

    private fun initSearchTextWatcher() {
        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val searchQuery = s.toString()
                if (searchQuery.isNotEmpty()) {
                    viewModel.searchRepos(searchQuery)
                }
            }
        }
        binding.etSearch.addTextChangedListener(searchTextWatcher)
    }

    private fun getGitHubTrendingRepositories() {
        viewModel.getTrendingRepoList()
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.apply {
            isRefreshing = true
            this.setColorSchemeColors(
                ContextCompat.getColor(
                    this@GitHubTrendingRepoActivity,
                    R.color.white
                )
            )
            setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(
                    this@GitHubTrendingRepoActivity,
                    R.color.blue
                )
            )
            setOnRefreshListener {
                getGitHubTrendingRepositories()
            }
        }
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun stopLoader() {
        binding.swipeRefresh.isRefreshing = false
    }
}