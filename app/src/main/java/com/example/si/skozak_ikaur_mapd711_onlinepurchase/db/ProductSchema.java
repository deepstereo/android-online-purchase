package com.example.si.skozak_ikaur_mapd711_onlinepurchase.db;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class ProductSchema {

    @PrimaryKey
    @NonNull
    public String productID;

    public String productName;
    public int price;
    public int quantity;
    public String category;
    public int imageID;
}
