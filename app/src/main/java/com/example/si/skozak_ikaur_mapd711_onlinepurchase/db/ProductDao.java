package com.example.si.skozak_ikaur_mapd711_onlinepurchase.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface ProductDao {

    @Query("select * from ProductSchema")
    List<ProductSchema> loadAllProducts();

    @Query("select * from ProductSchema where productID = :id")
    ProductSchema loadProductById(int id);

    @Query("select * from ProductSchema where productName = :productName")
    List<ProductSchema> findProductByName(String productName);

    @Insert(onConflict = IGNORE)
    void insertProduct(ProductSchema product);

    @Delete
    void deleteProduct(ProductSchema product);


    @Query("DELETE FROM ProductSchema")
    void deleteAll();
}
