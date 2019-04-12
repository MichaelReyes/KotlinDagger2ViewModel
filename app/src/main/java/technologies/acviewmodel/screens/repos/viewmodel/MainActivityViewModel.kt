package technologies.acviewmodel.screens.repos.viewmodel

import android.arch.lifecycle.ViewModel
import technologies.acviewmodel.networking.RepoService
import javax.inject.Inject

class MainActivityViewModel @Inject
constructor(private val repoService: RepoService) : ViewModel()
