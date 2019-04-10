package tutorials.acviewmodel.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tutorials.acviewmodel.model.Repo;
import tutorials.acviewmodel.networking.RepoApi;
import tutorials.acviewmodel.networking.RepoService;

public class SelectedRepoViewModel extends ViewModel {

    private final MutableLiveData<Repo> selectedRepo = new MutableLiveData<>();
    private Call<Repo> repoCall;
    private final RepoService repoService;

    public LiveData<Repo> getSelectedRepo(){
        return selectedRepo;
    }

    @Inject
    public SelectedRepoViewModel(RepoService repoService){
        this.repoService = repoService;
    }

    void setSelectedRepo(Repo repo){
        selectedRepo.setValue(repo);
    }

    //<editor-fold desc="For saving and restoring data when android kills the process on out of memory exception or process death">
    public void restoreFromBundle(Bundle savedInstanceState) {
        if(selectedRepo.getValue() == null){
            //We only care about restoring if we don't have a selected Repo set already.
            if (savedInstanceState != null && savedInstanceState.containsKey("repo_details")) {
                loadRepo(savedInstanceState.getStringArray("repo_details"));
            }
        }
    }

    public void saveToBundle(Bundle outState) {
        if (selectedRepo.getValue() != null) {
            outState.putStringArray("repo_details", new String[]{
                    selectedRepo.getValue().owner().login(),
                    selectedRepo.getValue().name()
            });
        }
    }

    private void loadRepo(String[] repoDetails) {
        repoCall = repoService.getRepo(repoDetails[0], repoDetails[1]);
        repoCall.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                selectedRepo.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
                Log.e(getClass().getSimpleName(), "Error loading repo", t);
            }
        });
    }
    //</editor-fold>


    @Override
    protected void onCleared() {
        super.onCleared();
        if (repoCall != null) {
            repoCall.cancel();
            repoCall = null;
        }
    }
}
