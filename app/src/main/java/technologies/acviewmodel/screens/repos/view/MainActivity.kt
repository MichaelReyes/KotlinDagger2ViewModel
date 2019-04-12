package technologies.acviewmodel.screens.repos.view

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import technologies.acviewmodel.R
import technologies.acviewmodel.application.MyApplication
import technologies.acviewmodel.base.components.BaseActivity
import technologies.acviewmodel.databinding.ActivityMainBinding
import technologies.acviewmodel.screens.repos.subscreens.repo_list.view.ListFragment
import technologies.acviewmodel.screens.repos.viewmodel.MainActivityViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    override val layoutRes: Int
        get() = R.layout.activity_main

    override fun setupComponent(activity: Activity) {
        MyApplication.getApplicationComponent(this)!!.inject(this)
    }

    override fun onCreated(instance: Bundle?) {
        if (instance == null)
            goToFragment(ListFragment(), R.id.screen_container)
    }

    override fun getViewModel(): Class<MainActivityViewModel> {
        return MainActivityViewModel::class.java
    }

}
