package com.example.uap.api;

import com.example.uap.model.PlantItem;
import com.example.uap.model.PlantItem;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<PlantItem> data;

    public String getMessage() {
        return message;
    }

    public List<PlantItem> getData() {
        return data;
    }
}