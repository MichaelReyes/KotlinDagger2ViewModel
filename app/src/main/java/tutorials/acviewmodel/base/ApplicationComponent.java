package tutorials.acviewmodel.base;

import javax.inject.Singleton;

import dagger.Component;
import tutorials.acviewmodel.details.DetailsFragment;
import tutorials.acviewmodel.home.ListFragment;
import tutorials.acviewmodel.networking.NetworkModule;
import tutorials.acviewmodel.viewmodel.ViewModelModule;

@Singleton
@Component(modules = {
        NetworkModule.class,
        ViewModelModule.class,
})
public interface ApplicationComponent {

    void inject(ListFragment listFragment);

    void inject(DetailsFragment detailsFragment);

}
