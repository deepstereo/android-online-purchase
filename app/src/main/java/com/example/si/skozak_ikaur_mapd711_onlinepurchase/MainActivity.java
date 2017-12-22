package com.example.si.skozak_ikaur_mapd711_onlinepurchase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.AppDatabase;
import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.CustomerSchema;
import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.DatabaseInitializer;
import com.example.si.skozak_ikaur_mapd711_onlinepurchase.db.EmployeeSchema;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppDatabase mDb;

    public static final String PREFS = "com.example.skozak.PREFS";


    boolean customerSelected = true;
    EditText username;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = AppDatabase.getInMemoryDatabase(getApplicationContext());

        username = findViewById(R.id.loginField);
        password = findViewById(R.id.passwordField);

        if (mDb.customerModel().loadAllCustomers().size() == 0) {
            populateDb();
        } else {
            System.out.println("DB populated");
        }

    }


    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    private void populateDb() {
        DatabaseInitializer.populateSync(mDb);
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

    // validate username and password and open next activity

    public void logIn(View view) {

        String enteredUsername = username.getText().toString();
        String enteredPassword = password.getText().toString();

        if (TextUtils.isEmpty(enteredUsername) || TextUtils.isEmpty(enteredPassword)) {
            cantBeLeftBlank();
        } else {

            SharedPreferences sharedPrefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();

            if (!customerSelected) {

                EmployeeSchema employee = mDb.employeeModel().loadEmployeeByUsername(enteredUsername);
                if (employee == null) {
                    notFound();
                } else {
                    if (Objects.equals(enteredUsername, employee.username) && Objects.equals(enteredPassword, employee.password)) {

                        editor.putString("username", enteredUsername);
                        editor.apply();

                        Intent employeeLogin = new Intent(MainActivity.this, OrderList.class);
                        startActivity(employeeLogin);
                    } else {
                        incorrectDetails();
                    }
                }
            } else {
                CustomerSchema customer = mDb.customerModel().loadCustomerByUsername(enteredUsername);

                if (customer == null) {
                    notFound();
                } else {
                    if (Objects.equals(enteredUsername, customer.username) && Objects.equals(enteredPassword, customer.password)) {

                        editor.putString("username", enteredUsername);
                        editor.apply();

                        Intent customerLogin = new Intent(MainActivity.this, PlaceOrderActivity.class);
                        startActivity(customerLogin);
                    } else {
                        incorrectDetails();
                    }
                }
            }
        }
    }


    // helper functions to display errors

    public void cantBeLeftBlank () {
        Context context = getApplicationContext();
        CharSequence text = "Please enter both username and password";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    public void incorrectDetails () {
        Context context = getApplicationContext();
        CharSequence text = "Wrong username or password!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void notFound () {
        Context context = getApplicationContext();
        CharSequence text = "Not found";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }



}
