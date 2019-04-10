package tutorials.acviewmodel.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tutorials.acviewmodel.model.Repo;
import tutorials.acviewmodel.networking.RepoApi;
import tutorials.acviewmodel.networking.RepoService;

public class ListViewModel extends ViewModel {

    private final MutableLiveData<List<Repo>> repos= new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError= new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading= new MutableLiveData<>();
    private final RepoService repoService;

    private Call<List<Repo>> repoCall;

    LiveData<List<Repo>> getRepos(){
        return  repos;
    }

    LiveData<Boolean> getError(){
        return repoLoadError;
    }

    LiveData<Boolean> getLoading(){
        return loading;
    }

    @Inject
    public ListViewModel(RepoService repoService){
        this.repoService = repoService;
        fetchRepos();
    }

    private void fetchRepos() {
        loading.setValue(true);
        repoCall = repoService.getRepositories();
        repoCall.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                repoLoadError.setValue(false);
                repos.setValue(response.body());
                loading.setValue(false);
                repoCall = null;
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                repoLoadError.setValue(true);
                loading.setValue(false);
                repoCall = null;
            }
        });
    }

    @Override
    protected void onCleared() {
        if (repoCall != null) {
            repoCall.cancel();
            repoCall = null;
        }
    }
}
