package technologies.acviewmodel.viewmodel

import android.arch.lifecycle.ViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import technologies.acviewmodel.screens.repos.viewmodel.ListViewModel
import technologies.acviewmodel.screens.repos.viewmodel.MainActivityViewModel
import technologies.acviewmodel.screens.repos.viewmodel.SelectedRepoViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    internal abstract fun bindListViewModel(viewModel: ListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectedRepoViewModel::class)
    internal abstract fun bindSelectedRepoViewModel(viewModel: SelectedRepoViewModel): ViewModel

}
