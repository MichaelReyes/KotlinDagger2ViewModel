package technologies.acviewmodel.screens.repos.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import javax.inject.Inject

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import technologies.acviewmodel.model.Repo
import technologies.acviewmodel.networking.RepoService

class ListViewModel @Inject
constructor(private val repoService: RepoService) : ViewModel() {

    private val repos = MutableLiveData<List<Repo>>()
    private val repoLoadError = MutableLiveData<Boolean>()
    private val loading = MutableLiveData<Boolean>()

    private var repoCall: Call<List<Repo>>? = null

    val error: LiveData<Boolean>
        get() = repoLoadError

    fun getRepos(): LiveData<List<Repo>> {
        return repos
    }

    fun getLoading(): LiveData<Boolean> {
        return loading
    }

    init {
        fetchRepos()
    }

    private fun fetchRepos() {
        loading.value = true
        repoCall = repoService.repositories
        repoCall!!.enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                repoLoadError.value = false
                repos.value = response.body()
                loading.value = false
                repoCall = null
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                repoLoadError.value = true
                loading.value = false
                repoCall = null
            }
        })
    }

    override fun onCleared() {
        if (repoCall != null) {
            repoCall!!.cancel()
            repoCall = null
        }
    }
}
