package com.nayan.bloodbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;

import static android.location.LocationManager.GPS_PROVIDER;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static FirebaseDatabase database;


    private FirebaseAuth mAuth;

    CardView cv1,cv2,cv3,cv4,cv5,cv6;
    public static String donorId="no";
    SharedPreferences sharedPreferences;

    public static Double lat=0.0;
    public static Double lng=0.0;

    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1;

    GoogleApiClient mGoogleApiClient;
    LocationManager locationManager;
    LocationListener locationListener;

    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cv1=findViewById(R.id.cv1);
        cv2=findViewById(R.id.cv2);
        cv3=findViewById(R.id.cv3);
        cv4=findViewById(R.id.cv4);
        cv5=findViewById(R.id.cv5);
        cv6=findViewById(R.id.cv6);


        // Connecting to the database
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference myRef = database.getReference("donors");

        if(mAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Login2Activity.class));
        }

        FirebaseUser user = mAuth.getCurrentUser();

        /**
         * Wiring up every thing
         */
        MobileAds.initialize(this,"ca-app-pub-0205521656322088~2515935320");

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-0205521656322088/2621271677");
        interstitialAd.loadAd(new AdRequest.Builder().build());

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startActivity(new Intent(MainActivity.this, Information.class));
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });


        cv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mAuth.signOut();
            finish();
            TastyToast.makeText(getApplicationContext(),"Logged Out",TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
            startActivity(new Intent(MainActivity.this, Login2Activity.class));
            }
        });




        cv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(interstitialAd.isLoaded()){
                    interstitialAd.show();
                }
                else {
                    startActivity(new Intent(MainActivity.this, Information.class));
                }
            }
        });


        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BankAddressActivity.class));
            }
        });


        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NeedBlood.class));
            }
        });
        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, facts.class));
            }
        });


        if(donorId.toString().equals("no")){
            cv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, DonorForm.class));
                }
            });
        } else {

        }

        /**
         * Initializing variable
         */
        try{
            donorId = sharedPreferences.getString("id","no");

        } catch (Exception e){
            e.printStackTrace();
        }

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lng = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(GPS_PROVIDER)){
            Toast.makeText(MainActivity.this, "Please Turn on Location", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            MainActivity.this.startActivity(myIntent);
        }


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            /**
             * Crating a location request
             */



            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);



            return;
        }
        locationManager.requestLocationUpdates(GPS_PROVIDER, 1000, 1, locationListener);





    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
