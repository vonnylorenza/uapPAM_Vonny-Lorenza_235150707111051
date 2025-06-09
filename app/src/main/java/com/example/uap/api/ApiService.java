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

    @GET("plant")
    Call<List<PlantItem>> getAllItems();

    @GET("plant/{id}")
    Call<PlantItem> getItemById(@Path("id") String itemId);

    @POST("plant")
    Call<PlantItem> createItem(@Body PlantItem item);

    @PUT("plant/{id}")
    Call<PlantItem> updateItem(@Path("id") String itemId, @Body PlantItem item);

    @DELETE("plant/{id}")
    Call<Void> deleteItem(@Path("id") String itemId);
}
