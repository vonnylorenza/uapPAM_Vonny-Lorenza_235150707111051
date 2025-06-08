package com.example.uap.model; // Sesuaikan dengan package Anda

import com.google.gson.annotations.SerializedName;

public class PlantItem {

    private String id; // ID unik item, biasanya dari API
    @SerializedName("nama_tanaman") // Jika nama di JSON berbeda dari nama variabel
    private String namaTanaman;
    private double harga;
    private String deskripsi;
    @SerializedName("image_url")
    private String imageUrl; // Opsional, jika API menyediakan URL gambar

    public PlantItem(String id, String namaTanaman, double harga, String deskripsi, String imageUrl) {
        this.id = id;
        this.namaTanaman = namaTanaman;
        this.harga = harga;
        this.deskripsi = deskripsi;
        this.imageUrl = imageUrl;
    }

    // Konstruktor tanpa ID untuk POST (create)
    public PlantItem(String namaTanaman, double harga, String deskripsi, String imageUrl) {
        this.namaTanaman = namaTanaman;
        this.harga = harga;
        this.deskripsi = deskripsi;
        this.imageUrl = imageUrl;
    }

    // Getter dan Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaTanaman() {
        return namaTanaman;
    }

    public void setNamaTanaman(String namaTanaman) {
        this.namaTanaman = namaTanaman;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}