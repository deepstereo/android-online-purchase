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
import java.util.Objects;

import static com.example.si.skozak_ikaur_mapd711_onlinepurchase.MainActivity.PREFS;

public class PlaceOrderActivity extends AppCompatActivity {

    public static final String ORDER = "com.example.myfirstapp.ORDER";

    private AppDatabase mDb;
    private List<ProductSchema> productDataset;

    private RecyclerView productRecycler;
    private RecyclerView.Adapter productAdapter;
    private RecyclerView.LayoutManager productLayoutManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        mDb = AppDatabase.getInMemoryDatabase(getApplicationContext());
        productDataset = mDb.productModel().loadAllProducts();

        productRecycler = (RecyclerView) findViewById(R.id.product_recycler);

        productLayoutManager = new LinearLayoutManager(this);
        productRecycler.setLayoutManager(productLayoutManager);

        productAdapter = new ProductAdapter(productDataset);
        productRecycler.setAdapter(productAdapter);

    }



    public void onClickNext (View view) {

        String thisOrderId = " ";


        for (int i = 0; i < productDataset.size(); i++) {

            View item = productRecycler.getChildAt(i);
            EditText enteredAmount = (EditText) item.findViewById(R.id.product_amount);
            TextView productTitle = (TextView) item.findViewById(R.id.product_name);
            String selectedProduct = productTitle.getText().toString();
            String amount = enteredAmount.getText().toString();

            SharedPreferences sharedPrefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
            String username = sharedPrefs.getString("username", "username");

            OrderSchema order = new OrderSchema();
            order.customer = mDb.customerModel().loadCustomerByUsername(username).customerID;
            order.product = mDb.productModel().findProductByName(selectedProduct).productID;

            order.amount = Integer.valueOf(amount);

            order.status = "in-process";
            order.orderID = String.valueOf(mDb.orderModel().findAllOrders().size() + 1);
            thisOrderId = order.orderID;

            mDb.orderModel().insertOrder(order);
        }

        Intent confirm = new Intent(PlaceOrderActivity.this, CustomerDetails.class);
        confirm.putExtra(ORDER, thisOrderId);
        startActivity(confirm);
    }




    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}



