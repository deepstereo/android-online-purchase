package com.example.si.skozak_ikaur_mapd711_onlinepurchase.db;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class EmployeeSchema {

    @PrimaryKey
    @NonNull
    public String employeeID;

    public String username;
    public String password;
    public String firstName;
    public String lastName;
}
