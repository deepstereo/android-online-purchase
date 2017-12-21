package com.example.si.skozak_ikaur_mapd711_onlinepurchase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.AppDatabase;
import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.CustomerSchema;
import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.DatabaseInitializer;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppDatabase mDb;
    private TextView textViewTest;

    public static final String PREFS = "com.example.skozak.PREFS";


    boolean customerSelected = true;
    EditText username;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = AppDatabase.getInMemoryDatabase(getApplicationContext());

        username = (EditText)findViewById(R.id.loginField);
        password = (EditText)findViewById(R.id.passwordField);

        // textview to test database output
        textViewTest = (TextView)findViewById(R.id.textViewTest);

        populateDb();
        fetchData();

    }


    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    private void populateDb() {
        DatabaseInitializer.populateSync(mDb);
    }

    public void fetchData() {

        String customerUsername;
        CustomerSchema customer = mDb.customerModel().loadCustomerByUsername("customer1");
        customerUsername = customer.username;

        textViewTest.setText(customerUsername);
    }


    // Check which radio button selected
    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radioCustomer:
                if (checked)
                    customerSelected = true;
                break;
            case R.id.radioEmployee:
                if (checked)
                    customerSelected = false;
                break;
        }
    }

    // save username and password and open next activity

    public void logIn(View view) {


        String enteredUsername = username.getText().toString();
        String enteredPassword = password.getText().toString();

        if (Objects.equals(enteredUsername, "") || (Objects.equals(enteredPassword, ""))) {
            Context context = getApplicationContext();
            CharSequence text = "Please enter both username and password";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }


        SharedPreferences sharedPrefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        if (!customerSelected) {

            String usernameToCheck = mDb.employeeModel().loadEmployeeByUsername(enteredUsername).username;
            String passwordToCheck = mDb.employeeModel().loadEmployeeByUsername(enteredUsername).password;

            if (Objects.equals(enteredUsername, usernameToCheck) && Objects.equals(enteredPassword, passwordToCheck)) {
                Intent employeeLogin = new Intent(MainActivity.this, OrderList.class);
                startActivity(employeeLogin);
                System.out.println("is " + customerSelected);
                } else {
                Context context = getApplicationContext();
                CharSequence text = "Wrong username or password!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            } else {

            String usernameToCheck = mDb.customerModel().loadCustomerByUsername(enteredUsername).username;
            String passwordToCheck = mDb.customerModel().loadCustomerByUsername(enteredUsername).password;

            if (Objects.equals(enteredUsername, usernameToCheck) && Objects.equals(enteredPassword, passwordToCheck)) {
                Intent customerLogin = new Intent(MainActivity.this, PlaceOrderActivity.class);
                startActivity(customerLogin);
            } else {
                Context context = getApplicationContext();
                CharSequence text = "Wrong username or password!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }





}
