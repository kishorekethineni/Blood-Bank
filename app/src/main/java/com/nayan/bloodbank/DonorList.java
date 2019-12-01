package com.nayan.bloodbank;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

import static com.nayan.bloodbank.MainActivity.database;

public class DonorList extends AppCompatActivity {
    String city;
    String group;
    ArrayList<String> donorList;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    public static ArrayList<Donor> donorInfo;
    Button buttonMap;
    TextView adharTxt;

    private boolean connection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();


        Bundle extras = getIntent().getExtras();
        city = extras.getString("city");
        group = extras.getString("group");
        Log.i("NAME",city);
        Log.i("NAME",group);

        donorList = new ArrayList<>();
        donorInfo = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list_donor);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, donorList);
        listView.setAdapter(arrayAdapter);
        adharTxt = (TextView)findViewById(R.id.adharTxt);
        buttonMap = (Button) findViewById(R.id.Button_mapShow);
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DonorList.this, MapsActivity.class));
            }
        });



        DatabaseReference myRef = database.getReference("donors");
        myRef.child(city).child(group).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(uid.equals("vXLXqw4pUjahg3Vi1GQi2EuUzSd2")){
                adharTxt.setVisibility(View.VISIBLE);
                Donor donor = dataSnapshot.getValue(Donor.class);
                donorInfo.add(donor);
                String donorInfo = donor.name + "     \n" + donor.mobileNumber + "     \n" +donor.adhar;
                donorList.add(donorInfo);
                arrayAdapter.notifyDataSetChanged();
                }else {
                    adharTxt.setVisibility(View.INVISIBLE);
                    Donor donor = dataSnapshot.getValue(Donor.class);
                    donorInfo.add(donor);
                    String donorInfo = donor.name + "     \n" + donor.mobileNumber;
                    donorList.add(donorInfo);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(connection()){
            TastyToast.makeText(getApplicationContext(),"Please Wait...",TastyToast.LENGTH_LONG,TastyToast.INFO).show();
        }else {
            TastyToast.makeText(getApplicationContext(),"Internet Connection may not Available !",TastyToast.LENGTH_LONG,TastyToast.WARNING).show();
        }

    }
}
