package com.demo.trendinggithubrepo.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.trendinggithubrepo.R
import com.demo.trendinggithubrepo.data.api_models.GitHubRepo
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trending_github_repo)
        viewModel = ViewModelProvider(this, factory).get(GitHubTrendingRepoViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.listener = this
        binding.rvRepositories.layoutManager = LinearLayoutManager(this@GitHubTrendingRepoActivity)
        initSwipeToRefresh()
        initSearchTextWatcher()
        getGitHubTrendingRepositories("")
    }

    private fun initSearchTextWatcher() {
        val searchTextWatcher = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val searchQuery = s.toString()
                if(searchQuery.isNotEmpty()){
                    getGitHubTrendingRepositories(searchQuery)
                }
            }
        }
        binding.etSearch.addTextChangedListener(searchTextWatcher)
    }

    private fun getGitHubTrendingRepositories(searchQuery: String) {
        viewModel.getTrendingRepoList(searchQuery)
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
                getGitHubTrendingRepositories("")
            }
        }
    }

    override fun updateRepoAdapter(repoList: ArrayList<GitHubRepo>) {
        Timber.d("Repo Size ${repoList.size}")
        if (repoAdapter == null) {
            repoAdapter = GitHubRepoAdapter(repoList, this)
            binding.rvRepositories.adapter = repoAdapter
        } else repoAdapter?.notifyUpdatedList(repoList)
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun stopLoader() {
        binding.swipeRefresh.isRefreshing = false
    }


}