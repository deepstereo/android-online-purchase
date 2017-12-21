package com.example.si.skozak_ikaur_mapd711_onlinepurchase.db;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters(DateConverter.class)
public interface OrderDao {

    @Query("SELECT * From OrderSchema")
    List<OrderSchema> findAllOrders();

    @Insert()
    void insertOrder(OrderSchema order);

    @Update(onConflict = REPLACE)
    void updateOrder(OrderSchema order);

    @Query("DELETE FROM OrderSchema")
    void deleteAll();


    //    @Query("SELECT Loan.id, Book.title, User.name, Loan.startTime, Loan.endTime From Loan " +
//            "INNER JOIN Book ON Loan.book_id = Book.id " +
//            "INNER JOIN User ON Loan.user_id = User.id ")
//    LiveData<List<LoanWithUserAndBook>> findAllWithUserAndBook();
//
//    @Query("SELECT Loan.id, Book.title as title, User.name as name, Loan.startTime, Loan.endTime " +
//            "FROM Book " +
//            "INNER JOIN Loan ON Loan.book_id = Book.id " +
//            "INNER JOIN User on User.id = Loan.user_id " +
//            "WHERE User.name LIKE :userName " +
//            "AND Loan.endTime > :after "
//    )
//    LiveData<List<LoanWithUserAndBook>> findLoansByNameAfter(String userName, Date after);

}
