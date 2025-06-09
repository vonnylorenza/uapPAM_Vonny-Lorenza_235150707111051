package com.example.uap.api; // Sesuaikan dengan package Anda

import com.example.uap.model.PlantItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    String BASE_URL = "https://uappam.kuncipintu.my.id/";

    @GET("items")
    Call<List<PlantItem>> getAllItems();

    @GET("items/{id}")
    Call<PlantItem> getItemById(@Path("id") String itemId);

    @POST("items")
    Call<PlantItem> createItem(@Body PlantItem item);

    @PUT("items/{id}")
    Call<PlantItem> updateItem(@Path("id") String itemId, @Body PlantItem item);

    @DELETE("items/{id}")
    Call<Void> deleteItem(@Path("id") String itemId);
}
