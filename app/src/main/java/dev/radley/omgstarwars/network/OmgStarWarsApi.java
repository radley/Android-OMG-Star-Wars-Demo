package dev.radley.omgstarwars.network;

import android.os.Build;

import java.io.IOException;

import dev.radley.omgstarwars.data.OmgStarWars;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit.RestAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// replaces com.swapi.sw.StarWarsApi to allow search

public class OmgStarWarsApi {

    private OmgStarWars mSwApi;
    private static OmgStarWarsApi sInstance;

    private OmgStarWarsApi() {


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Customize the request
                Request request = original.newBuilder()
                        .header("User-Agent", "swapi-android-" + Build.VERSION.RELEASE)
                        .method(original.method(), original.body())
                        .build();

                Response response = chain.proceed(request);

                // Customize or return the response
                return response;
            }
        });

        OkHttpClient client = httpClient.build();

        final Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .client(client)
                .baseUrl(APIConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mSwApi = retrofit.create(OmgStarWars.class);
    }

    public static void init() {
        sInstance = new OmgStarWarsApi();
    }

    public static OmgStarWars getApi() {
        return sInstance.mSwApi;
    }

    public void setApi(OmgStarWars starWarsApi) {
        sInstance.mSwApi = starWarsApi;
    }
}
