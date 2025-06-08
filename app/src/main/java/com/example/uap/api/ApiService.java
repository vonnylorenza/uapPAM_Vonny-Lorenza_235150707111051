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

    // Asumsi endpoint Postman
    String BASE_URL = "https://uappam.kuncipintu.my.id/"; // GANTI DENGAN BASE URL API ANDA YANG SEBENARNYA!

    @GET("items") // Ganti dengan endpoint GET all items Anda
    Call<List<PlantItem>> getAllItems();

    @GET("items/{id}") // Ganti dengan endpoint GET item by ID Anda
    Call<PlantItem> getItemById(@Path("id") String itemId);

    @POST("items") // Ganti dengan endpoint POST (create) Anda
    Call<PlantItem> createItem(@Body PlantItem item);

    @PUT("items/{id}") // Ganti dengan endpoint PUT (update) Anda
    Call<PlantItem> updateItem(@Path("id") String itemId, @Body PlantItem item);

    @DELETE("items/{id}") // Ganti dengan endpoint DELETE Anda
    Call<Void> deleteItem(@Path("id") String itemId);
}
