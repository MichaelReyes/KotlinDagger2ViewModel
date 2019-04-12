package technologies.acviewmodel.networking

import com.squareup.moshi.Moshi

import java.util.concurrent.TimeUnit

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
//import technologies.acviewmodel.model.ModelAdapterFactory

@Module
object NetworkModule {

    private val BASE_URL = "https://api.github.com/"

    internal val loggingInterceptor: HttpLoggingInterceptor
        @Provides
        get() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    internal val authInterceptor: BasicAuthInterceptor
        @Provides
        get() = BasicAuthInterceptor("MichaelReyes", "mike12345")

    /*
    internal val moshiConverterFactory: Moshi
        @Provides
        get() = Moshi.Builder()
                .add(ModelAdapterFactory.create())
                .build()
    */

    @Provides
    @Singleton
    internal fun provideRetrofit(client: OkHttpClient/*, moshi: Moshi*/): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                //.addConverterFactory(MoshiConverterFactory.create(moshi))
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideRepoService(retrofit: Retrofit): RepoService {
        return retrofit.create(RepoService::class.java)
    }

    @Provides
    internal fun getHttpClient(interceptor: HttpLoggingInterceptor, authInterceptor: BasicAuthInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)

        builder.addInterceptor(interceptor)
        builder.addInterceptor(authInterceptor)

        return builder.build()
    }

}
