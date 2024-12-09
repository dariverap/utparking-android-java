package com.example.finalproyect;

import android.util.JsonReader;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.Cleaner;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    static final String BASE_URL = "https://utparking-api.onrender.com";

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
