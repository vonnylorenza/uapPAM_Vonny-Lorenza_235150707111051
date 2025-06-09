package com.example.uap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uap.api.ApiClient;
import com.example.uap.api.ApiService;
import com.example.uap.model.PlantItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "AddItemActivity";
    private EditText etName, etPrice, etDescription;
    private Button btnAdd;
    private ImageView ivPlantImage;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        etName = findViewById(R.id.et_add_plant_name);
        etPrice = findViewById(R.id.et_add_plant_price);
        etDescription = findViewById(R.id.et_add_plant_description);
        btnAdd = findViewById(R.id.btn_add_plant);
        ivPlantImage = findViewById(R.id.iv_add_plant_image);

        apiService = ApiClient.getApiService();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPlantItem();
            }
        });

    }

    private void createPlantItem() {
        String name = etName.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (name.isEmpty() || priceStr.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Semua kolom harus diisi.", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = 0;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Harga harus berupa angka.", Toast.LENGTH_SHORT).show();
            return;
        }

        String imageUrl = "https://placehold.co/150x150/4CAF50/FFFFFF?text=Plant";

        PlantItem newPlant = new PlantItem(name, price, description, imageUrl);

        apiService.createItem(newPlant).enqueue(new Callback<PlantItem>() {
            @Override
            public void onResponse(@NonNull Call<PlantItem> call, @NonNull Response<PlantItem> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(AddItemActivity.this, "Item berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddItemActivity.this, "Gagal menambahkan item: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Gagal menambahkan item: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlantItem> call, @NonNull Throwable t) {
                Toast.makeText(AddItemActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error saat menambahkan item: ", t);
            }
        });
    }
}