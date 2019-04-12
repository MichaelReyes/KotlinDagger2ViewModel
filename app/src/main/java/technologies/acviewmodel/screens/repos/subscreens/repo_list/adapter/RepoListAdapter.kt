package technologies.acviewmodel.screens.repos.subscreens.repo_list.adapter

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import technologies.acviewmodel.R
import technologies.acviewmodel.databinding.ViewRepoListItemBinding
import technologies.acviewmodel.screens.repos.viewmodel.ListViewModel
import technologies.acviewmodel.model.Repo

class RepoListAdapter(viewModel: ListViewModel, lifecycleOwner: LifecycleOwner, private val repoSelectedListener: RepoSelectedListener) : RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>() {

    private val data = ArrayList<Repo>()

    init {

        viewModel.getRepos().observe(lifecycleOwner, Observer { repos ->
            if (repos == null) {
                data.clear()
                notifyDataSetChanged()
            }else {
                val diffResult = DiffUtil.calculateDiff(RepoDiffCallback(data, repos!!))
                data.clear()
                data.addAll(repos!!)
                diffResult.dispatchUpdatesTo(this)
            }
        })
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RepoViewHolder {
        val binding = DataBindingUtil.inflate<ViewRepoListItemBinding>(LayoutInflater.from(viewGroup.context), R.layout.view_repo_list_item, viewGroup, false)
        return RepoViewHolder(binding.root, repoSelectedListener)
    }

    override fun onBindViewHolder(repoViewHolder: RepoViewHolder, i: Int) {
        repoViewHolder.bind(data[i])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return data[position].id
    }

    class RepoViewHolder(itemView: View, repoSelectedListener: RepoSelectedListener) : RecyclerView.ViewHolder(itemView) {

        private var repo: Repo? = null
        private val binding: ViewRepoListItemBinding? = DataBindingUtil.bind(itemView)

        init {

            itemView.setOnClickListener { v ->
                if (repo != null) {
                    repoSelectedListener.onRepoSelected(repo!!)
                }
            }
        }

        fun bind(repo: Repo) {
            this.repo = repo
            binding!!.tvRepoName.text = repo.name
            binding.tvRepoDescription.text = repo.description
            binding.tvForks.text = repo.forks.toString()
            binding.tvStars.text = repo.stars.toString()
        }

    }

}
