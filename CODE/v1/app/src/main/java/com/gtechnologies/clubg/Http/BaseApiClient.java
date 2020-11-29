package com.gtechnologies.clubg.Http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Developed by Fojle Rabbi Saikat on 1/9/2017.
 * Owned by Bitmakers Ltd.
 * Contact fojle.rabbi@bitmakers-bd.com
 */
public class BaseApiClient {

    public static final String WEB_BASE_URL = "http://120.50.15.45:8080/";
    private static Retrofit retrofit = null;


    public static Retrofit getWebClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(WEB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
