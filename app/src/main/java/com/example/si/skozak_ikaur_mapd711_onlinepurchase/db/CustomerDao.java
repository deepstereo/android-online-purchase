package com.example.si.skozak_ikaur_mapd711_onlinepurchase.db;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface CustomerDao {

    @Query("select * from CustomerSchema")
    List<CustomerSchema> loadAllCustomers();

    @Query("select * from CustomerSchema where customerID = :id")
    CustomerSchema loadCustomerById(int id);

    @Query("select * from CustomerSchema where username = :username")
    CustomerSchema loadCustomerByUsername(String username);

    @Query("select * from CustomerSchema where firstName = :firstName and lastName = :lastName")
    List<CustomerSchema> findCustomerByNameAndLastName(String firstName, String lastName);

    @Insert(onConflict = IGNORE)
    void insertCustomer(CustomerSchema customer);

    @Delete
    void deleteCustomer(CustomerSchema customer);

    @Insert(onConflict = IGNORE)
    void insertOrReplaceCustomers(CustomerSchema... customers);

    @Delete
    void deleteCustomers(CustomerSchema customer1, CustomerSchema customer2);

    @Query("DELETE FROM CustomerSchema")
    void deleteAll();

}
