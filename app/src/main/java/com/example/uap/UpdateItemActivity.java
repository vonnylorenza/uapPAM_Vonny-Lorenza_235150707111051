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

import com.bumptech.glide.Glide;
import com.example.uap.api.ApiClient;
import com.example.uap.api.ApiService;
import com.example.uap.model.PlantItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateItemActivity extends AppCompatActivity {

    private static final String TAG = "UpdateItemActivity";
    private EditText etName, etPrice, etDescription;
    private Button btnSave;
    private ImageView ivPlantImage;
    private String itemId;
    private String originalImageUrl;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        etName = findViewById(R.id.et_update_plant_name);
        etPrice = findViewById(R.id.et_update_plant_price);
        etDescription = findViewById(R.id.et_update_plant_description);
        btnSave = findViewById(R.id.btn_update_plant);
        ivPlantImage = findViewById(R.id.iv_update_plant_image);

        apiService = ApiClient.getApiService();

        if (getIntent().getExtras() != null) {
            itemId = getIntent().getStringExtra("itemId");
            String itemName = getIntent().getStringExtra("itemName");
            String itemPrice = getIntent().getStringExtra("itemPrice");
            String itemDescription = getIntent().getStringExtra("itemDescription");
            originalImageUrl = getIntent().getStringExtra("itemImageUrl");

            etName.setText(itemName);
            etPrice.setText(itemPrice);
            etDescription.setText(itemDescription);

            if (originalImageUrl != null && !originalImageUrl.isEmpty()) {
                Glide.with(this)
                        .load(originalImageUrl)
                        .placeholder(R.drawable.placeholder_plant)
                        .error(R.drawable.placeholder_plant)
                        .into(ivPlantImage);
            } else {
                ivPlantImage.setImageResource(R.drawable.placeholder_plant);
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePlantItem();
            }
        });
    }

    private void updatePlantItem() {
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

        String imageUrl = originalImageUrl != null ? originalImageUrl : "https://placehold.co/150x150/4CAF50/FFFFFF?text=Plant";

        PlantItem updatedPlant = new PlantItem(itemId, name, price, description, imageUrl);

        apiService.updateItem(itemId, updatedPlant).enqueue(new Callback<PlantItem>() {
            @Override
            public void onResponse(@NonNull Call<PlantItem> call, @NonNull Response<PlantItem> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(UpdateItemActivity.this, "Item berhasil diperbarui!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateItemActivity.this, "Gagal memperbarui item: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Gagal memperbarui item: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlantItem> call, @NonNull Throwable t) {
                Toast.makeText(UpdateItemActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error saat memperbarui item: ", t);
            }
        });
    }
}
