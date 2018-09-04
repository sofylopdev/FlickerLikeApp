package edu.galileo.android.flickerapp.api;

import java.io.IOException;

import edu.galileo.android.flickerapp.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlickerClient {

    private static final String API_KEY = BuildConfig.FLICKER_API_KEY;
    private Retrofit retrofit;
    public static final String BASE_URL = "https://api.flickr.com";


    public FlickerClient(){

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl.Builder urlBuilder = request.url().newBuilder();
                urlBuilder.addQueryParameter("api_key", API_KEY);
                urlBuilder.addQueryParameter("format", "json");
                urlBuilder.addQueryParameter("nojsoncallback", "1");

                request = request.newBuilder().url(urlBuilder.build()).build();
                return chain.proceed(request);
            }
        });

        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public FlickrService getFlickrService(){
        return this.retrofit.create(FlickrService.class);
    }

}
