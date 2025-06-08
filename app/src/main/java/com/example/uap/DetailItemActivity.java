package com.example.uap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.uap.api.ApiClient;
import com.example.uap.api.ApiService;
import com.example.uap.model.PlantItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailItemActivity extends AppCompatActivity {

    private static final String TAG = "DetailItemActivity";
    private TextView tvName, tvPrice, tvDescription;
    private ImageView ivImage;
    private Button btnUpdate;
    private String itemId;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);

        tvName = findViewById(R.id.tv_detail_plant_name);
        tvPrice = findViewById(R.id.tv_detail_plant_price);
        tvDescription = findViewById(R.id.tv_detail_plant_description);
        ivImage = findViewById(R.id.iv_detail_plant_image);
        btnUpdate = findViewById(R.id.btn_detail_update);

        apiService = ApiClient.getApiService();

        // Ambil data dari Intent
        if (getIntent().getExtras() != null) {
            itemId = getIntent().getStringExtra("itemId");
            String itemName = getIntent().getStringExtra("itemName");
            double itemPrice = getIntent().getDoubleExtra("itemPrice", 0.0);
            String itemDescription = getIntent().getStringExtra("itemDescription");
            String itemImageUrl = getIntent().getStringExtra("itemImageUrl");

            tvName.setText(itemName);
            tvPrice.setText(String.format("Rp %.0f", itemPrice));
            tvDescription.setText(itemDescription);

            if (itemImageUrl != null && !itemImageUrl.isEmpty()) {
                Glide.with(this)
                        .load(itemImageUrl)
                        .placeholder(R.drawable.placeholder_plant)
                        .error(R.drawable.placeholder_plant)
                        .into(ivImage);
            } else {
                ivImage.setImageResource(R.drawable.placeholder_plant);
            }
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailItemActivity.this, UpdateItemActivity.class);
                intent.putExtra("itemId", itemId);
                intent.putExtra("itemName", tvName.getText().toString());
                intent.putExtra("itemPrice", tvPrice.getText().toString().replace("Rp ", "").replace(".", "")); // Hapus format harga
                intent.putExtra("itemDescription", tvDescription.getText().toString());
                intent.putExtra("itemImageUrl", getIntent().getStringExtra("itemImageUrl")); // Kirim juga URL gambar jika ada
                startActivity(intent);
            }
        });

    }

    private void fetchItemDetail() {
        if (itemId == null) return;

        apiService.getItemById(itemId).enqueue(new Callback<PlantItem>() {
            @Override
            public void onResponse(@NonNull Call<PlantItem> call, @NonNull Response<PlantItem> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PlantItem item = response.body();
                    tvName.setText(item.getNamaTanaman());
                    tvPrice.setText(String.format("Rp %.0f", item.getHarga()));
                    tvDescription.setText(item.getDeskripsi());
                    if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
                        Glide.with(DetailItemActivity.this)
                                .load(item.getImageUrl())
                                .placeholder(R.drawable.placeholder_plant)
                                .error(R.drawable.placeholder_plant)
                                .into(ivImage);
                    } else {
                        ivImage.setImageResource(R.drawable.placeholder_plant);
                    }
                } else {
                    Toast.makeText(DetailItemActivity.this, "Gagal memuat detail item: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Gagal memuat detail item: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlantItem> call, @NonNull Throwable t) {
                Toast.makeText(DetailItemActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error saat memuat detail item: ", t);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // fetchItemDetail();
    }
}