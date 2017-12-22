package com.example.si.skozak_ikaur_mapd711_onlinepurchase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.AppDatabase;
import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.CustomerSchema;
import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.OrderSchema;
import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.ProductSchema;

import java.util.List;

import static com.example.si.skozak_ikaur_mapd711_onlinepurchase.MainActivity.PREFS;

public class OrderConfirmation extends AppCompatActivity {

    private AppDatabase mDb;
    private List<OrderSchema> orderDataset;
    private List<ProductSchema> productDataset;

    private RecyclerView orderRecycler;
    private RecyclerView.Adapter orderAdapter;
    private RecyclerView.LayoutManager orderLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        mDb = AppDatabase.getInMemoryDatabase(getApplicationContext());

        SharedPreferences sharedPrefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String username = sharedPrefs.getString("username", "username");
        CustomerSchema customer = mDb.customerModel().loadCustomerByUsername(username);

        orderDataset = mDb.orderModel().loadOrderByCustomerId(customer.customerID);
        productDataset = mDb.productModel().loadAllProducts();


        orderRecycler = (RecyclerView) findViewById(R.id.confirmationRecycler);

        orderLayoutManager = new LinearLayoutManager(this);
        orderRecycler.setLayoutManager(orderLayoutManager);

        orderAdapter = new OrderAdapter(orderDataset, productDataset);
        orderRecycler.setAdapter(orderAdapter);

    }

    public void onHomeClicked (View view) {

        SharedPreferences sharedPrefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove("username");
        editor.apply();

        Intent i = new Intent(OrderConfirmation.this, MainActivity.class);
        startActivity(i);
    }





}
