package com.example.mudrafinder;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mudrafinder.Retrofit.RetrofitBuilder;
import com.example.mudrafinder.Retrofit.RetrofitInterface;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
Button button;
EditText currencytobeconerted;
EditText currencyconverted;
Spinner convertedToDropdown;
Spinner convertedFromDropdown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //INITIALIZATION

        currencyconverted =  (EditText) findViewById(R.id.editTextNumberDecimal2);
        currencytobeconerted = (EditText) findViewById(R.id.editTextNumberDecimal);
        convertedFromDropdown = (Spinner) findViewById(R.id.spinner);
        convertedToDropdown = (Spinner) findViewById(R.id.spinner2);
        button = (Button) findViewById(R.id.button);

        //adding functionality to app

        String[] dropDownList = {"INR","USD", "EUR","NZD","GBP","JPY","PKR","AUD","RUB","CNY"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, dropDownList);
        convertedToDropdown.setAdapter(adapter);
        convertedFromDropdown.setAdapter(adapter);

        button.setOnClickListener(view -> {
            RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(RetrofitInterface.class);
            Call<JsonObject> call = retrofitInterface.getExchangeCurrency(convertedFromDropdown.getSelectedItem().toString());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                    JsonObject res = response.body();
                    JsonObject rates = res.getAsJsonObject("rates");
                    double currency = Double.parseDouble(currencytobeconerted.getText().toString());
                    double multiplier = Double.parseDouble(rates.get(convertedToDropdown.getSelectedItem().toString()).toString());
                    double result = currency * multiplier;
                    currencyconverted.setText(String.valueOf(result));
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        });







     }
}