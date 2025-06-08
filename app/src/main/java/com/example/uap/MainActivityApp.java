package com.example.uap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.uap.adapter.PlantItemAdapter; // Akan kita buat
import com.example.uap.api.ApiClient;
import com.example.uap.api.ApiService;
import com.example.uap.model.PlantItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityApp extends AppCompatActivity {

    private static final String TAG = "MainActivityApp";
    private RecyclerView rvItems;
    private PlantItemAdapter adapter;
    private List<PlantItem> plantItemList;
    private ApiService apiService;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mAuth = FirebaseAuth.getInstance();
        apiService = ApiClient.getApiService();

        rvItems = findViewById(R.id.rv_items);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        plantItemList = new ArrayList<>();
        adapter = new PlantItemAdapter(this, plantItemList, new PlantItemAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(String itemId) {
                deletePlantItem(itemId);
            }

            @Override
            public void onDetailClick(PlantItem item) {
                Intent intent = new Intent(MainActivityApp.this, DetailItemActivity.class);
                intent.putExtra("itemId", item.getId());
                intent.putExtra("itemName", item.getNamaTanaman());
                intent.putExtra("itemPrice", item.getHarga());
                intent.putExtra("itemDescription", item.getDeskripsi());
                intent.putExtra("itemImageUrl", item.getImageUrl());
                startActivity(intent);
            }
        });
        rvItems.setAdapter(adapter);

        Button btnAddItem = findViewById(R.id.btn_add_item);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivityApp.this, AddItemActivity.class));
            }
        });

        fetchPlantItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchPlantItems();
    }

    private void fetchPlantItems() {
        apiService.getAllItems().enqueue(new Callback<List<PlantItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<PlantItem>> call, @NonNull Response<List<PlantItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    plantItemList.clear();
                    plantItemList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "Data items berhasil dimuat.");
                } else {
                    Toast.makeText(MainActivityApp.this, "Gagal memuat item: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Gagal memuat item: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PlantItem>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivityApp.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error saat memuat item: ", t);
            }
        });
    }

    private void deletePlantItem(String itemId) {
        apiService.deleteItem(itemId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivityApp.this, "Item berhasil dihapus.", Toast.LENGTH_SHORT).show();
                    fetchPlantItems();
                } else {
                    Toast.makeText(MainActivityApp.this, "Gagal menghapus item: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Gagal menghapus item: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(MainActivityApp.this, "Error saat menghapus item: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error saat menghapus item: ", t);
            }
        });
    }
}