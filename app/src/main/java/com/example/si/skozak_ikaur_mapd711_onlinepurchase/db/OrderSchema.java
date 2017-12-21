package com.example.si.skozak_ikaur_mapd711_onlinepurchase.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;


@Entity(foreignKeys = {
        @ForeignKey(entity = CustomerSchema.class,
        parentColumns = "customerID",
        childColumns = "customer"),

        @ForeignKey(entity = ProductSchema.class,
        parentColumns = "productID",
        childColumns = "product"),

        @ForeignKey(entity = EmployeeSchema.class,
        parentColumns = "employeeID",
        childColumns = "employee")
})
@TypeConverters(DateConverter.class)
public class OrderSchema {

    @PrimaryKey
    @NonNull
    public String orderID;

    @ColumnInfo(name = "customer")
    public String customer;

    @ColumnInfo(name = "product")
    public String product;

    @ColumnInfo(name = "employee")
    public String employee;

    public int amount;

    public Date dateAdded;
    public String status;
}
