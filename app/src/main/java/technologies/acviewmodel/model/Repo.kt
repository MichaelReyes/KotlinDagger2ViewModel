package technologies.acviewmodel.model

import com.google.auto.value.AutoValue
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.util.Collections.copy

class Repo {

    val id: Long = 0
    val name: String? = null
    val description: String? = null
    val owner: User? = null
    @SerializedName("stargazers_count")
    val stars: Long = 0
    @SerializedName("forks_count")
    var forks: Long = 0
}
