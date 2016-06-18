package com.honeycrisp.fave.http;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Creates and configures an HTTP adapter for web APIs.
 *
 * Created by Ryan Taylor on 5/7/15.
 */
public class HttpAdapter {

    static final String TAG = HttpAdapter.class.getSimpleName();

    private static final HttpLoggingInterceptor.Level LOG_LEVEL = HttpLoggingInterceptor.Level.BODY;
    private static OkHttpClient httpClient;

    private static AccessTokenProvider accessTokenProvider;

    /**
     * Initializes HttpAdapter's Retrofit instance, and creates and returns the specified API interface.
     *
     * @param apiClass the API to create for the caller.
     * @param <A> generic type representing an API interface.
     *
     * @return the API requested by the caller
     */
    public static <A> A createApi(String baseUrl, String apiKey, @NonNull Class<A> apiClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createHttpClient(apiKey))
                .build();
        return retrofit.create(apiClass);
    }

    /**
     * Get the AccessTokenProvider to be used by HttpAdapter to get the access token.
     *
     * This method is for the Login process to get an {@link AccessTokenProvider} from HttpAdapter. Note
     * that only the login process should use this method.
     */
    public static AccessTokenProvider getAccessTokenProvider() {
        return accessTokenProvider;
    }

    /**
     * Set the AccessTokenProvider to be used by HttpAdapter to get the access token.
     *
     * This method is for the Login process to set an {@link AccessTokenProvider} for HttpAdapter. Note
     * that only the login process should use this method.
     */
    public static void setAccessTokenProvider(AccessTokenProvider accessTokenProvider) {
        HttpAdapter.accessTokenProvider = accessTokenProvider;
    }

    /**
     * Configure and return a custom HTTP client.
     *
     * @return an OkHttp Client
     */
    public static OkHttpClient createHttpClient(String apiKey) {
        if (httpClient == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(LOG_LEVEL);

            httpClient = new OkHttpClient.Builder()
                    .addInterceptor(new RequestInterceptor(apiKey))
                    .addInterceptor(loggingInterceptor)
                    .build();
        }
        return httpClient;
    }

    public static OkHttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * Construct a JSON response converter to be used on all responses made by HttpAdapter.
     * Also attaches any necessary TypeAdapterFactories and TypeAdapters to the converter.
     *
     * @return a GSON response converter.
     */
    private static Gson getResponseConverter(List<Pair<Class, Object>> typeAdapterPairs) {
        GsonBuilder gsonBuilder = new GsonBuilder();

        if (typeAdapterPairs != null) {
            for (Pair<Class, Object> typeAdapterPair : typeAdapterPairs) {
                gsonBuilder.registerTypeAdapter(typeAdapterPair.first, typeAdapterPair.second);
            }
        }

        return gsonBuilder.create();
    }

    public interface AccessTokenProvider {

        String getAccessTokenAsString() throws Exception;
    }

    /**
     * A request interceptor that adds any necessary header elements to each request made with
     * this client.
     */
    private static class RequestInterceptor implements Interceptor {

        private String apiKey;

        public RequestInterceptor(String apiKey) {
            this.apiKey = apiKey;
        }

        @Override
        public synchronized Response intercept(Chain chain) throws IOException {
            Request.Builder requestBuilder = chain.request().newBuilder();

            if (accessTokenProvider != null) {
                try {
                    String accessToken = accessTokenProvider.getAccessTokenAsString();
                    if (!TextUtils.isEmpty(accessToken)) {
                        requestBuilder.addHeader("X-Access-Token", accessToken);
                    }
                    else {
                        Log.e(TAG, "No access token to add to: " + requestBuilder);
                    }
                }
                catch (Exception exception) {
                    Log.e(TAG, "RequestInterceptor.intercept(): error getting access token: ", exception);
                }
            }

            return chain.proceed(requestBuilder.build());
        }
    }
}
