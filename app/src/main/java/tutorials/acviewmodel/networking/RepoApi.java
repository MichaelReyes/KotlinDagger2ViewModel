package tutorials.acviewmodel.networking;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RepoApi {

    /*
    private static final String BASE_URL = "https://api.github.com/";

    private static Retrofit retrofit;
    private static RepoService repoService;

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(new BasicAuthInterceptor("MichaelReyes", "mike12345"));

    public static RepoService getInstance() {
        if (repoService != null)
            return repoService;

        if (retrofit == null)
            initializeRetrofit();

        repoService = retrofit.create(RepoService.class);
        return repoService;
    }

    private static void initializeRetrofit() {

        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    private RepoApi() {

    }

    static class BasicAuthInterceptor implements Interceptor {

        private String credentials;

        public BasicAuthInterceptor(String user, String password) {
            this.credentials = Credentials.basic(user, password);
        }

        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            Request authenticatedRequest = request.newBuilder()
                    .header("Authorization", credentials).build();
            return chain.proceed(authenticatedRequest);
        }

    }
    */

}
