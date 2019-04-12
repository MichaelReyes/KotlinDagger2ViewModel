package technologies.acviewmodel.screens.repos.subscreens.repo_list.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import technologies.acviewmodel.R
import technologies.acviewmodel.application.MyApplication
import technologies.acviewmodel.base.components.BaseFragment
import technologies.acviewmodel.databinding.ScreenListBinding
import technologies.acviewmodel.screens.repos.subscreens.repo_details.view.DetailsFragment
import technologies.acviewmodel.screens.repos.viewmodel.ListViewModel
import technologies.acviewmodel.screens.repos.subscreens.repo_list.adapter.RepoListAdapter
import technologies.acviewmodel.screens.repos.subscreens.repo_list.adapter.RepoSelectedListener
import technologies.acviewmodel.screens.repos.viewmodel.SelectedRepoViewModel
import technologies.acviewmodel.model.Repo
import technologies.acviewmodel.viewmodel.ViewModelFactory

/**
 * Created by TBLTechNerds on 11/29/18.
 */
class ListFragment : BaseFragment<ScreenListBinding, ListViewModel>(), RepoSelectedListener {

    override fun getViewModel(): Class<ListViewModel> {
        return ListViewModel::class.java
    }

    override val layoutRes: Int
        get() = R.layout.screen_list

    override fun setupComponent(context: Context?) {
        MyApplication.getApplicationComponent(context!!)!!.inject(this)

    }

    override fun onCreated(savedInstance: Bundle?) {
        initializeViews()
        observeViewModel()
    }

    private fun initializeViews() {
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL))
        binding.recyclerView.adapter = RepoListAdapter(viewModel, this, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onRepoSelected(repo: Repo) {
        val selectedRepoViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(SelectedRepoViewModel::class.java)

        selectedRepoViewModel.setSelectedRepo(repo)

        goToFragment(DetailsFragment(), R.id.screen_container)
    }

    private fun observeViewModel() {
        viewModel.getRepos().observe(this, Observer { repos ->
            if (repos != null) {
                binding.recyclerView.visibility = View.VISIBLE
            }
        })

        viewModel.error.observe(this, Observer { isError ->
            if (isError!!) {
                binding.tvError.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
                binding.tvError.setText(R.string.api_error_repos)
            } else {
                binding.tvError.visibility = View.GONE
                binding.tvError.text = null
            }
        })

        viewModel.getLoading().observe(this, Observer { isLoading ->

            binding.loadingView.visibility = if (isLoading!!) View.VISIBLE else View.GONE
            if (isLoading) {
                binding.tvError.visibility = View.GONE
                binding.recyclerView.visibility = View.GONE
            }
        })
    }


}
