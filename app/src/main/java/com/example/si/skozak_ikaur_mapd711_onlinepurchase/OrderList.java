package com.example.si.skozak_ikaur_mapd711_onlinepurchase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.AppDatabase;
import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.CustomerSchema;
import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.DatabaseInitializer;
import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.OrderSchema;
import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.ProductSchema;

import java.util.List;

import static com.example.si.skozak_ikaur_mapd711_onlinepurchase.MainActivity.PREFS;

public class OrderList extends AppCompatActivity {

    private AppDatabase mDb;
    private List<OrderSchema> orderListDataset;
    private List<ProductSchema> productListDataset;

    private RecyclerView orderListRecycler;
    private RecyclerView.Adapter orderListAdapter;
    private RecyclerView.LayoutManager orderListLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        mDb = AppDatabase.getInMemoryDatabase(getApplicationContext());


        orderListDataset = mDb.orderModel().findAllOrders();
        productListDataset = mDb.productModel().loadAllProducts();


        orderListRecycler = (RecyclerView) findViewById(R.id.orderListRecycler);

        orderListLayoutManager = new LinearLayoutManager(this);
        orderListRecycler.setLayoutManager(orderListLayoutManager);

        orderListAdapter = new OrderListAdapter(orderListDataset, productListDataset);
        orderListRecycler.setAdapter(orderListAdapter);

    }


    private void populateDb() {
        DatabaseInitializer.populateSync(mDb);
    }

    public void onClickLogout (View view) {
        SharedPreferences sharedPrefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove("username");
        editor.apply();

        Intent i = new Intent(OrderList.this, MainActivity.class);
        startActivity(i);
    }

}
