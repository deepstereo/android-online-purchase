package com.example.si.skozak_ikaur_mapd711_onlinepurchase.db;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.si.skozak_ikaur_mapd711_onlinepurchase.R;

import java.util.Calendar;
import java.util.Date;

public class DatabaseInitializer {

    // Simulate a blocking operation delaying each order insertion with a delay:
    private static final int DELAY_MILLIS = 500;

    public static void populateAsync(final AppDatabase db) {

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    private static void addOrder(final AppDatabase db, final String orderID,
                                final CustomerSchema customer, final ProductSchema product, final EmployeeSchema employee, int amount, Date dateAdded, String status) {
        OrderSchema order = new OrderSchema();
        order.orderID = orderID;
        order.customer = customer.customerID;
        order.product = product.productID;
        order.employee = employee.employeeID;
        order.amount = amount;
        order.dateAdded = dateAdded;
        order.status = status;
        db.orderModel().insertOrder(order);
    }

    private static ProductSchema addProduct(final AppDatabase db, final String productID, final String productName, String category, int price, int quantity, int imageID) {
        ProductSchema product = new ProductSchema();
        product.productID = productID;
        product.productName = productName;
        product.category = category;
        product.price = price;
        product.quantity = quantity;
        product.imageID = imageID;
        db.productModel().insertProduct(product);
        return product;
    }

    private static EmployeeSchema addEmployee(final AppDatabase db, final String employeeID, final String username, final String password, String firstName, String lastName) {
        EmployeeSchema employee = new EmployeeSchema();
        employee.employeeID = employeeID;
        employee.username = username;
        employee.password = password;
        employee.firstName = firstName;
        employee.lastName = lastName;
        db.employeeModel().insertEmployee(employee);
        return employee;
    }

    private static CustomerSchema addCustomer(final AppDatabase db, final String customerID, final String username, final String password, String firstName, String lastName, String address, String email) {

        CustomerSchema customer = new CustomerSchema();
        customer.customerID = customerID;
        customer.username = username;
        customer.password = password;
        customer.firstName = firstName;
        customer.lastName = lastName;
        customer.address = address;
        customer.email = email;
        db.customerModel().insertCustomer(customer);
        return customer;
    }



    private static void populateWithTestData(AppDatabase db) {

        db.customerModel().deleteAll();
        db.employeeModel().deleteAll();
        db.productModel().deleteAll();
        db.orderModel().deleteAll();


        CustomerSchema customer1 = addCustomer(db, "1", "customer1", "12345", "John", "McClane", "145 Bloor Street, Toronto", "john@test.com");
        CustomerSchema customer2 = addCustomer(db, "2", "customer2", "67890", "John", "Smith", "145 Bay Street, Toronto", "john@test.com");
        EmployeeSchema employee1 = addEmployee(db, "1", "employee1", "54321", "Hans", "Gruber");

        ProductSchema product1 = addProduct(db, "1", "Blonde Lager", "lager", 3, 100, R.drawable.blonde);
        ProductSchema product2 = addProduct(db, "2", "3 Speed Lager", "lager", 4, 100, R.drawable.threespeed);
        ProductSchema product3 = addProduct(db, "3", "Boneshaker IPA", "IPA", 5, 100, R.drawable.boneshaker);

        try {
            // Loans are added with a delay, to have time for the UI to react to changes.

            Date today = getTodayPlusDays(0);
            Date yesterday = getTodayPlusDays(-1);

            addOrder(db, "1", customer2, product1, employee1, 5, today, "in process");
            Thread.sleep(DELAY_MILLIS);
            addOrder(db, "2", customer2, product2, employee1, 5, today, "in process");
            Thread.sleep(DELAY_MILLIS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static Date getTodayPlusDays(int daysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, daysAgo);
        return calendar.getTime();
    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}