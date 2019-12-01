package com.nayan.bloodbank;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.sdsmdg.tastytoast.TastyToast;

import static com.nayan.bloodbank.MainActivity.database;

public class DonorForm extends AppCompatActivity {
    Spinner cityChoice;
    Spinner groupChoice;

    EditText Name;
    EditText Mobile;
    EditText et_adhar;

    Button Save;

    ProgressBar progressBar;

    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_form);

        cityChoice = (Spinner) findViewById(R.id.dropdownCity);

        String[] citis = new String[]{"NONE","Amritsar",
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
        cityChoice.setAdapter(adapter);


        groupChoice = (Spinner) findViewById(R.id.dropdownGroup);
        String[] group = new String[]{"NONE","O+","O-", "A+", "B+","A-", "B-", "AB+", "AB-"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, group);
        groupChoice.setAdapter(adapter1);

        Name = (EditText) findViewById(R.id.edt_name);
        Mobile = (EditText) findViewById(R.id.edt_mobileNumber);
        Save = (Button) findViewById(R.id.btn_saveDonor);
        et_adhar = (EditText)findViewById(R.id.et_adhar);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString();
                String city = cityChoice.getSelectedItem().toString();
                String group = groupChoice.getSelectedItem().toString();
                String mobile = Mobile.getText().toString();
                String lat = MainActivity.lat.toString();
                String lng = MainActivity.lng.toString();
                String adhar = et_adhar.getText().toString();

                if(TextUtils.isEmpty(name)){
                    TastyToast.makeText(getApplicationContext(),"Enter Full Name !",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
                    Save.startAnimation(shakeError());
                    return;
                }
                if(city.equals("NONE")){
                    TastyToast.makeText(getApplicationContext(),"Select City !",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
                    Save.startAnimation(shakeError());
                    return;
                }
                if(group.equals("NONE")){
                    TastyToast.makeText(getApplicationContext(),"Select Blood Group !",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
                    Save.startAnimation(shakeError());
                    return;
                }
                if(TextUtils.isEmpty(mobile)){
                    TastyToast.makeText(getApplicationContext(),"Enter Mobile Number !",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
                    Save.startAnimation(shakeError());
                    return;
                }
                if(mobile.length()<10){
                    TastyToast.makeText(getApplicationContext(),"Enter Valid Mobile Number!",TastyToast.LENGTH_LONG,TastyToast.WARNING).show();
                    return;
                }
                //Aadhar Verification

                if(TextUtils.isEmpty(adhar)){
                    TastyToast.makeText(getApplicationContext(),"Enter Aadhar Number !",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
                    Save.startAnimation(shakeError());
                    return;
                }

                boolean aresult = Verhoeff.validateVerhoeff(adhar);
                String fresult = String.valueOf(aresult);
                if (fresult != "true"){
                    TastyToast.makeText(getApplicationContext(),"Enter Valid Aadhar Number!",TastyToast.LENGTH_LONG,TastyToast.WARNING).show();
                    Save.startAnimation(shakeError());
                    et_adhar.getText().clear();
                    return;
                }



                Donor donor = new Donor(name,mobile,group,city,lat,lng,adhar);
                DatabaseReference myRef = database.getReference("donors");
                myRef.child(city).child(group).push().setValue(donor);

                finish();
            }
        });

    }
}
