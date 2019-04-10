package tutorials.acviewmodel.viewmodel;

import android.arch.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import tutorials.acviewmodel.home.ListViewModel;
import tutorials.acviewmodel.home.SelectedRepoViewModel;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    abstract ViewModel bindListViewModel(ListViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SelectedRepoViewModel.class)
    abstract ViewModel bindSeelectedRepoViewModel(SelectedRepoViewModel viewModel);
}
