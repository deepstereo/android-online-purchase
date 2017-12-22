package com.example.si.skozak_ikaur_mapd711_onlinepurchase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.AppDatabase;
import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.CustomerSchema;
import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.OrderSchema;
import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.ProductSchema;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

import static com.example.si.skozak_ikaur_mapd711_onlinepurchase.MainActivity.PREFS;

public class CustomerDetails extends AppCompatActivity {

    AppDatabase mDb;
    EditText customerFirstName;
    EditText customerLastName;
    EditText address;
    EditText email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        SharedPreferences sharedPrefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String username = sharedPrefs.getString("username", "username");

        mDb = AppDatabase.getInMemoryDatabase(getApplicationContext());

        CustomerSchema customer = mDb.customerModel().loadCustomerByUsername(username);
        String customerID = customer.customerID;


        List<OrderSchema> orderContents = mDb.orderModel().loadOrderByCustomerId(customerID);
        StringBuilder sb = new StringBuilder();
        for (OrderSchema order : orderContents) {

            ProductSchema product = mDb.productModel().loadProductById(order.product);
            sb.append(String.format(Locale.CANADA, "%s - %s bottles\n", product.productName, order.amount));
        }

        TextView orderDetails = (TextView)findViewById(R.id.orderDetails);
        orderDetails.setText("Hi, " + customer.firstName + "! " + "You ordered : \n" + sb);

        customerFirstName = findViewById(R.id.customerFirstName);
        customerLastName = findViewById(R.id.customerLastName);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);

        customerFirstName.setText(customer.firstName);
        customerLastName.setText(customer.lastName);
        address.setText(customer.address);
        email.setText(customer.email);


    }

    public void onConfirmClick (View view) {
        Intent i = new Intent(CustomerDetails.this, OrderConfirmation.class);
        startActivity(i);
    }






}
