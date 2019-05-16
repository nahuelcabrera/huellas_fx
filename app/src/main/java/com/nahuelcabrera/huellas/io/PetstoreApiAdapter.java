package com.nahuelcabrera.huellas.io;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PetstoreApiAdapter {

    private static PetstoreApiService PETSTORE_API_SERVICE;

    public static PetstoreApiService getPetstoreApiService(){

        //Create interceptor to log headers

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(loggingInterceptor);

        //
        String baseUrl = "https://petstore.swagger.io/";

        if(PETSTORE_API_SERVICE == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            PETSTORE_API_SERVICE = retrofit.create(PetstoreApiService.class);

        }

        return PETSTORE_API_SERVICE;
    }

}
