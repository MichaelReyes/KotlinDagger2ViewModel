package technologies.acviewmodel.application

import javax.inject.Singleton

import dagger.Component
import technologies.acviewmodel.screens.repos.subscreens.repo_details.view.DetailsFragment
import technologies.acviewmodel.screens.repos.subscreens.repo_list.view.ListFragment
import technologies.acviewmodel.networking.NetworkModule
import technologies.acviewmodel.screens.repos.view.MainActivity
import technologies.acviewmodel.viewmodel.ViewModelModule

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(listFragment: ListFragment)

    fun inject(detailsFragment: DetailsFragment)

}
