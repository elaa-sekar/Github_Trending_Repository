package com.demo.trendinggithubrepo.ui.home

import android.R.color
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.demo.trendinggithubrepo.R
import com.demo.trendinggithubrepo.data.api_models.GitHubRepo
import com.demo.trendinggithubrepo.data.database.TrendingRepositories
import com.demo.trendinggithubrepo.databinding.AdapterRepositoriesBinding


class GitHubRepoAdapter(
    var gitHubRepoList: ArrayList<TrendingRepositories>,
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
        holder.apply {
            setBinding(gitHubRepoList[position])
            updateLanguageColor(gitHubRepoList[position].languageColor!!, binding)
        }
    }


    private fun updateLanguageColor(languageColor: String, binding: AdapterRepositoriesBinding) {
        val drawable = ContextCompat.getDrawable(context, R.drawable.shape_circle)
        val color = Color.parseColor(languageColor)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable!!.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else drawable!!.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        binding.ivColorDot.background = drawable
    }

    override fun getItemCount(): Int {
        return gitHubRepoList.size
    }

    fun notifyUpdatedList(repoList: ArrayList<TrendingRepositories>) {
        gitHubRepoList.clear()
        gitHubRepoList = repoList
        notifyDataSetChanged()
    }

    inner class GitHubRepoViewHolder(var binding: AdapterRepositoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setBinding(repoModel: TrendingRepositories) {
            binding.model = repoModel
        }
    }
}