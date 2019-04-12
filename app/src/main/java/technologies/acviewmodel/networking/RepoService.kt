package technologies.acviewmodel.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import technologies.acviewmodel.model.Repo

interface RepoService {

    @get:GET("orgs/Google/repos")
    val repositories: Call<List<Repo>>

    @GET("repos/{owner}/{name}")
    fun getRepo(@Path("owner") repoOwner: String, @Path("name") repoName: String): Call<Repo>

}
