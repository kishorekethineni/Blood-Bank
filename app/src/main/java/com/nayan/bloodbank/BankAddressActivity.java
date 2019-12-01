package com.nayan.bloodbank;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

public class BankAddressActivity extends AppCompatActivity {

    Spinner bankadd;
    Button view_add;
    TextView cityChoice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_address);

        bankadd = (Spinner)findViewById(R.id.bankadd);
        view_add = (Button)findViewById(R.id.view_add);
        cityChoice = (TextView)findViewById(R.id.cityChoice);


        final String[] citis = new String[]{"NONE","Amritsar",
                "Barnala",
                "Bathinda",
                "Firozpur",
                "Hoshiarpur",
                "Jalandhar",
                "Kapurthala",
                "Ludhiana",
                "Chandigarh",
                "Mohali"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, citis);
        bankadd.setAdapter(adapter);



        view_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choice = bankadd.getSelectedItem().toString();
                if (choice.equals("NONE")){
                    TastyToast.makeText(getApplicationContext(),"Select City !",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
                    return;
                }
                if (choice.equals("Amritsar")) {
                    cityChoice.setText("Civil Hospital Amritsar");
                    cityChoice.setVisibility(View.VISIBLE);
                    return;
                }
                if (choice.equals("Barnala")){
                    cityChoice.setText("Civil Hospital Barnala");
                    cityChoice.setVisibility(View.VISIBLE);
                    return;
                }
                if (choice.equals("Bhatinda")){
                    cityChoice.setText("Gupta Blood Bank \n PhNo:-095016 20619");
                    cityChoice.setVisibility(View.VISIBLE);
                    return;
                }
                if(choice.equals("Firozpur")){
                    cityChoice.setText("J & K ferozpur ,\n phno:-0163 222 0741");
                    cityChoice.setVisibility(View.VISIBLE);
                    return;
                }
                if(choice.equals("Hoshiyarpur")){
                    cityChoice.setText("IMA bood bank hoshiyarpur \n Phno:-0188 222 2244");
                    cityChoice.setVisibility(View.VISIBLE);
                    return;
                }
                if(choice.equals("Jalandhar")){
                    cityChoice.setText("Ohri hospital jalandhar\n phno:-095924 31323");
                    cityChoice.setVisibility(View.VISIBLE);
                    return;
                }
                if(choice.equals("Kapurthala")){
                    cityChoice.setText("Dr lal path labs kapurthala \n Phno:-086992 09030");
                    cityChoice.setVisibility(View.VISIBLE);
                    return;
                }
                if(choice.equals("Ludhiana")){
                    cityChoice.setText("Red Cross Blood Bank \n Phno:-0161 244 1257");
                    cityChoice.setVisibility(View.VISIBLE);
                    return;
                }
                if(choice.equals("Chandigarh")){
                    cityChoice.setText("Rotary and blood bank Society Chandigarh \n Phno:-0172 269 6057");
                    cityChoice.setVisibility(View.VISIBLE);
                    return;
                }

                else {
                    cityChoice.setVisibility(View.INVISIBLE);
                    TastyToast.makeText(getApplicationContext(),"Searching for Data... !",TastyToast.LENGTH_LONG,TastyToast.INFO).show();
                    TastyToast.makeText(getApplicationContext(),"No Data Found !",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
                    return;
                }
            }
        });

    }
}
