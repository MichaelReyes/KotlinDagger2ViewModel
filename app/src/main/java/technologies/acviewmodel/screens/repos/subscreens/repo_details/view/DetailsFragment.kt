package technologies.acviewmodel.screens.repos.subscreens.repo_details.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
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
import technologies.acviewmodel.databinding.ScreenDetailsBinding
import technologies.acviewmodel.screens.repos.viewmodel.SelectedRepoViewModel
import technologies.acviewmodel.viewmodel.ViewModelFactory

class DetailsFragment : BaseFragment<ScreenDetailsBinding, SelectedRepoViewModel>() {

    override fun getViewModel(): Class<SelectedRepoViewModel> {
        return SelectedRepoViewModel::class.java
    }

    override val layoutRes: Int
        get() = R.layout.screen_details

    override fun setupComponent(context: Context?) {
        MyApplication.getApplicationComponent(context!!)!!.inject(this)
    }

    override fun onCreated(savedInstance: Bundle?) {
        viewModel.restoreFromBundle(savedInstance)
        displayRepo()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveToBundle(outState)
    }

    private fun displayRepo() {

        viewModel.getSelectedRepo().observe(this, Observer { repo ->
            binding.tvRepoName.text = repo!!.name
            binding.tvRepoDescription.text = repo!!.description
            binding.tvForks.text = repo.forks.toString()
            binding.tvStars.text = repo.stars.toString()
        })
    }

}
