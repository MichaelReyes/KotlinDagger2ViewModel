package technologies.acviewmodel.screens.repos.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.util.Log

import javax.inject.Inject

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import technologies.acviewmodel.model.Repo
import technologies.acviewmodel.networking.RepoService

class SelectedRepoViewModel @Inject
constructor(private val repoService: RepoService) : ViewModel() {

    private val selectedRepo = MutableLiveData<Repo>()
    private var repoCall: Call<Repo>? = null

    fun getSelectedRepo(): LiveData<Repo> {
        return selectedRepo
    }

    fun setSelectedRepo(repo: Repo) {
        selectedRepo.value = repo
    }

    //<editor-fold desc="For saving and restoring data when android kills the process on out of memory exception or process death">
    fun restoreFromBundle(savedInstanceState: Bundle?) {
        if (selectedRepo.value == null) {
            //We only care about restoring if we don't have a selected Repo set already.
            if (savedInstanceState != null && savedInstanceState.containsKey("repo_details")) {
                loadRepo(savedInstanceState.getStringArray("repo_details")!!)
            }
        }
    }

    fun saveToBundle(outState: Bundle) {
        if (selectedRepo.value != null) {
            outState.putStringArray("repo_details", arrayOf(selectedRepo.value!!.owner!!.login, selectedRepo.value!!.name))
        }
    }

    private fun loadRepo(repoDetails: Array<String>) {
        repoCall = repoService.getRepo(repoDetails[0], repoDetails[1])
        repoCall!!.enqueue(object : Callback<Repo> {
            override fun onResponse(call: Call<Repo>, response: Response<Repo>) {
                selectedRepo.value = response.body()
            }

            override fun onFailure(call: Call<Repo>, t: Throwable) {
                Log.e(javaClass.simpleName, "Error loading repo", t)
            }
        })
    }
    //</editor-fold>


    override fun onCleared() {
        super.onCleared()
        if (repoCall != null) {
            repoCall!!.cancel()
            repoCall = null
        }
    }
}
