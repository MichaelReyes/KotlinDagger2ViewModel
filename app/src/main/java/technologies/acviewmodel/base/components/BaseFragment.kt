package technologies.acviewmodel.base.components

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import javax.inject.Inject

import technologies.acviewmodel.R
import technologies.acviewmodel.application.MyApplication
import technologies.acviewmodel.screens.repos.subscreens.repo_details.view.DetailsFragment
import technologies.acviewmodel.viewmodel.ViewModelFactory

abstract class BaseFragment<V : ViewDataBinding, VM : ViewModel> : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected lateinit var binding: V

    protected lateinit var viewModel: VM

    @get:LayoutRes
    protected abstract val layoutRes: Int

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setupComponent(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return (binding).getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(getViewModel())
        onCreated(savedInstanceState)
    }

    protected abstract fun setupComponent(context: Context?)

    protected abstract fun onCreated(savedInstance: Bundle?)

    protected abstract fun getViewModel(): Class<VM>

    fun goToFragment(fragment: Fragment, containerViewId: Int) {
        activity!!.supportFragmentManager.beginTransaction()
                .replace(containerViewId, fragment)
                .addToBackStack(null).commit()
    }
}
