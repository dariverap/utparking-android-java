package com.example.finalproyect;

import android.util.JsonReader;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    static final String BASE_URL = "http://192.168.101.6:8080";

    Retrofit retrofit;

    public RetrofitClient() { }

    public Retrofit getRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        return retrofit;
    }


}
