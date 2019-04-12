package technologies.acviewmodel.base.components

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

import javax.inject.Inject

import technologies.acviewmodel.R
import technologies.acviewmodel.screens.repos.subscreens.repo_list.view.ListFragment
import technologies.acviewmodel.viewmodel.ViewModelFactory

abstract class BaseActivity<V : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected lateinit var binding: V

    protected lateinit var viewModel: VM

    @get:LayoutRes
    protected abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        setupComponent(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel())
        onCreated(savedInstanceState)
    }

    protected abstract fun onCreated(instance: Bundle?)

    protected abstract fun getViewModel(): Class<VM>

    protected abstract fun setupComponent(activity: Activity)

    fun goToFragment(fragment: Fragment, containerViewId: Int) {
        supportFragmentManager.beginTransaction()
                .add(containerViewId, fragment)
                .commit()
    }
}
