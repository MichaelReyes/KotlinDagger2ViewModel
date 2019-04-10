package tutorials.acviewmodel.networking;

import android.support.annotation.NonNull;

import com.squareup.moshi.Moshi;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import tutorials.acviewmodel.model.ModelAdapterFactory;

@Module
public class NetworkModule {

    private static final String BASE_URL = "https://api.github.com/";

    @Provides
    @Singleton
    static Retrofit provideRetrofit(@NonNull OkHttpClient client, Moshi moshi) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    static RepoService provideRepoService(Retrofit retrofit) {
        return retrofit.create(RepoService.class);
    }

    @Provides
    static HttpLoggingInterceptor getLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    static BasicAuthInterceptor getAuthInterceptor() {
        return new BasicAuthInterceptor("MichaelReyes", "mike12345");
    }

    @Provides
    static OkHttpClient getHttpClient(@NonNull HttpLoggingInterceptor interceptor, @NonNull BasicAuthInterceptor authInterceptor) {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        builder.addInterceptor(interceptor);
        builder.addInterceptor(authInterceptor);

        return builder.build();
    }

    @Provides
    static Moshi getMoshiConverterFactory() {
        return new Moshi.Builder()
                .add(ModelAdapterFactory.create())
                .build();
    }

}
