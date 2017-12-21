package com.example.si.skozak_ikaur_mapd711_onlinepurchase.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface EmployeeDao {

    @Query("select * from EmployeeSchema")
    List<EmployeeSchema> loadAllEmployees();

    @Query("select * from EmployeeSchema where employeeID = :id")
    EmployeeSchema loadEmployeeById(int id);

    @Query("select * from EmployeeSchema where username = :username")
    EmployeeSchema loadEmployeeByUsername(String username);

    @Query("select * from EmployeeSchema where firstName = :firstName and lastName = :lastName")
    List<EmployeeSchema> findEmployeeByNameAndLastName(String firstName, String lastName);

    @Insert(onConflict = IGNORE)
    void insertEmployee(EmployeeSchema employee);

    @Delete
    void deleteEmployee(EmployeeSchema employee);


    @Query("DELETE FROM EmployeeSchema")
    void deleteAll();

}
