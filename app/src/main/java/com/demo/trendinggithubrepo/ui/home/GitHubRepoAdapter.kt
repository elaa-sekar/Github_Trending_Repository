package com.demo.trendinggithubrepo.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.demo.trendinggithubrepo.R
import com.demo.trendinggithubrepo.data.api_models.GitHubRepo
import com.demo.trendinggithubrepo.databinding.AdapterRepositoriesBinding

class GitHubRepoAdapter(
    var gitHubRepoList: ArrayList<GitHubRepo>,
    var context: Context
) : RecyclerView.Adapter<GitHubRepoAdapter.GitHubRepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubRepoViewHolder {
        val binding: AdapterRepositoriesBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.adapter_repositories,
            parent,
            false
        )
        return GitHubRepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GitHubRepoViewHolder, position: Int) {
        holder.setBinding(gitHubRepoList[position])
    }

    override fun getItemCount(): Int {
        return gitHubRepoList.size
    }

    fun notifyUpdatedList(repoList: java.util.ArrayList<GitHubRepo>) {
        gitHubRepoList.clear()
        gitHubRepoList = repoList
        notifyDataSetChanged()
    }

    inner class GitHubRepoViewHolder(var binding: AdapterRepositoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setBinding(repoModel: GitHubRepo) {
            binding.model = repoModel
        }
    }
}