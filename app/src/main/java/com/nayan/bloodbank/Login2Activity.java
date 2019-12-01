package com.nayan.bloodbank;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputEditText;
import androidx.percentlayout.widget.PercentLayoutHelper;
import androidx.percentlayout.widget.PercentRelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.eyalbira.loadingdots.LoadingDots;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;


public class Login2Activity extends AppCompatActivity implements View.OnClickListener {

    public static FirebaseDatabase database;
    private boolean isSigninScreen = true;
    TextView tvSignupInvoker;
    LinearLayout llSignup;
    TextView tvSigninInvoker;
    LinearLayout llSignin;
    Button btnSignup;
    Button btnSignin;
    LinearLayout llsignin,llsignup;
    TextInputEditText email;
    TextInputEditText password;
    TextInputEditText et_password;
    TextInputEditText et_username;
    TextInputEditText fullname;
    TextInputEditText aadhar;
    TextInputEditText mobile;
    LoadingDots dotSignin;
    LoadingDots dotSignup;
    ImageView iclog;
    ImageView user;
    Spinner cityReg,bloodReg;



    private FirebaseAuth mAuth;


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);


        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        cityReg = (Spinner)findViewById(R.id.cityReg);
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
        cityReg.setAdapter(adapter);

        bloodReg = (Spinner)findViewById(R.id.bloodReg);
        String[] group = new String[]{"None","O+","O-", "A+", "B+","A-", "B-", "AB+", "AB-"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, group);
        bloodReg.setAdapter(adapter1);


        dotSignin = (LoadingDots)findViewById(R.id.dotSignin);
        dotSignup = (LoadingDots)findViewById(R.id.dotSignup);
        iclog = (ImageView)findViewById(R.id.iclog);
        user = (ImageView)findViewById(R.id.user);



        llSignin = (LinearLayout) findViewById(R.id.llSignin);
        llSignin.setOnClickListener(this);
        //LinearLayout singnin =(LinearLayout)findViewById(R.id.signin);
        llsignup =(LinearLayout)findViewById(R.id.llSignup);
        llsignup.setOnClickListener(this);
        tvSignupInvoker = (TextView) findViewById(R.id.tvSignupInvoker);
        tvSigninInvoker = (TextView) findViewById(R.id.tvSigninInvoker);

        btnSignup= (Button) findViewById(R.id.btnSignup);
        btnSignin= (Button) findViewById(R.id.btnSignin);
        email = (TextInputEditText) findViewById(R.id.email);
        password = (TextInputEditText) findViewById(R.id.password);
        et_username = (TextInputEditText) findViewById(R.id.et_username);
        et_password = (TextInputEditText) findViewById(R.id.et_password);
        fullname = (TextInputEditText) findViewById(R.id.fullname);
        aadhar = (TextInputEditText) findViewById(R.id.aadhar);
        mobile = (TextInputEditText)findViewById(R.id.mobile);


        mAuth = FirebaseAuth.getInstance();

        llSignup = (LinearLayout) findViewById(R.id.llSignup);
        llSignin = (LinearLayout) findViewById(R.id.llSignin);



        tvSignupInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = false;
                showSignupForm();
            }
        });

        tvSigninInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = true;
                showSigninForm();

            }
        });
        showSigninForm();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right_to_left);
                if(isSigninScreen)
                    btnSignup.startAnimation(clockwise);
            }
        });


        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(Login2Activity.this, MainActivity.class));
            finish();
        }

    }


    private boolean connection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void showSignupForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llSignup.requestLayout();

        tvSignupInvoker.setVisibility(View.GONE);
        tvSigninInvoker.setVisibility(View.VISIBLE);
        Animation translate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_right_to_left);
        llSignup.startAnimation(translate);

        Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right_to_left);
        btnSignup.startAnimation(clockwise);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connection()){
                getData();
                }else {
                    TastyToast.makeText(getApplicationContext(),"Internet Connection may not Available !",TastyToast.LENGTH_LONG,TastyToast.WARNING).show();
                }
            }
        });


    }


    private void showSigninForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.85f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.15f;
        llSignup.requestLayout();

        Animation translate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_left_to_right);
        llSignin.startAnimation(translate);

        tvSignupInvoker.setVisibility(View.VISIBLE);
        tvSigninInvoker.setVisibility(View.GONE);
        Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_left_to_right);
        btnSignin.startAnimation(clockwise);


        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connection()){
                Login();
                }else {
                    TastyToast.makeText(getApplicationContext(),"Internet Connection may not Available !",TastyToast.LENGTH_LONG,TastyToast.WARNING).show();
                }
            }
        });

    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.llSignin || v.getId() ==R.id.llSignup){
            //Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
            InputMethodManager methodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            methodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }

    }

    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }

    public void onReg(){
        final String myEmail = email.getText().toString();
        final String myPass = password.getText().toString();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        //Aadhar Validation
        String uaadhar = aadhar.getText().toString();
        boolean aresult = Verhoeff.validateVerhoeff(uaadhar);
        String fresult = String.valueOf(aresult);
        if (fresult != "true"){
            TastyToast.makeText(getApplicationContext(),"Enter Valid Aadhar Number!",TastyToast.LENGTH_LONG,TastyToast.WARNING).show();
            btnSignup.startAnimation(shakeError());
            aadhar.getText().clear();
            return;
        }


        if (!myEmail.matches(emailPattern))
        {
            TastyToast.makeText(getApplicationContext(),"Enter Valid Email Address!",TastyToast.LENGTH_LONG,TastyToast.WARNING).show();
            btnSignup.startAnimation(shakeError());
            email.getText().clear();

            return;

        }

        if(TextUtils.isEmpty(myEmail)){
            TastyToast.makeText(getApplicationContext(),"Enter Email Address !",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
            return;
        }
        if(TextUtils.isEmpty(myPass)){
            TastyToast.makeText(getApplicationContext(),"Enter Password !",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
            return;
        }
        if (!myPass.matches(passwordPattern))
        {
            TastyToast.makeText(getApplicationContext(),"Password should contain at least one number,One lowercase letter, One uppercase letter and One special character.",TastyToast.LENGTH_LONG,TastyToast.WARNING).show();
            btnSignup.startAnimation(shakeError());
            password.getText().clear();
            return;
        }

        user.setVisibility(View.INVISIBLE);
        dotSignup.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(myEmail, myPass)
                .addOnCompleteListener(Login2Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("TAG", "createUserWithEmail:success");
                            dotSignup.setVisibility(View.INVISIBLE);
                            TastyToast.makeText(getApplicationContext(),"Sucess",TastyToast.LENGTH_SHORT,
                                    TastyToast.SUCCESS).show();
                            Intent i = new Intent(Login2Activity.this, MainActivity.class);
                            finish();
                            startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("TAG", "createUserWithEmail:failure", task.getException());
                            TastyToast.makeText(getApplicationContext(),"Authentication failed.",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
                        }

                        // ...
                    }
                });
    }


    public void Login(){
        String lEmail = et_username.getText().toString().trim();
        final String lPass = et_password.getText().toString();
        final String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



        if(TextUtils.isEmpty(lEmail)){
            TastyToast.makeText(getApplicationContext(),"Enter Email Address !",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
            btnSignin.startAnimation(shakeError());
            return;
        }

        if (!lEmail.matches(emailPattern))
        {
            TastyToast.makeText(getApplicationContext(),"Enter Valid Email Address!",TastyToast.LENGTH_LONG,TastyToast.WARNING).show();
            btnSignin.startAnimation(shakeError());
            et_username.getText().clear();
            return;

        }

        if(TextUtils.isEmpty(lPass)){
            TastyToast.makeText(getApplicationContext(),"Enter Password !",
                    TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
            btnSignin.startAnimation(shakeError());
            return;
        }
        if(!lPass.matches(passwordPattern)){
            TastyToast.makeText(getApplicationContext(),"Password should contain at least one number,One lowercase letter, One uppercase letter and One special character.",
                    TastyToast.LENGTH_LONG,TastyToast.WARNING).show();
            btnSignin.startAnimation(shakeError());
            et_password.getText().clear();
            return;
        }
        iclog.setVisibility(View.INVISIBLE);
        dotSignin.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(lEmail, lPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            dotSignin.setVisibility(View.INVISIBLE);
                            TastyToast.makeText(getApplicationContext(),"Sucess",TastyToast.LENGTH_SHORT,
                                    TastyToast.SUCCESS).show();
                            Intent i = new Intent(Login2Activity.this, MainActivity.class);
                            finish();
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("TAG", "signInWithEmail:failure", task.getException());
                            TastyToast.makeText(getApplicationContext(),"Authentication failed.",
                                    TastyToast.LENGTH_LONG,TastyToast.ERROR).show();

                        }

                        // ...
                    }
                });



    }

    public void getData(){
        String name = fullname.getText().toString();
        String adhar = aadhar.getText().toString();
        String Mobile = mobile.getText().toString();
        String city = cityReg.getSelectedItem().toString();
        String group = bloodReg.getSelectedItem().toString();
        String lat = MainActivity.lat.toString();
        String lng = MainActivity.lng.toString();

        if (TextUtils.isEmpty(name)){
            TastyToast.makeText(getApplicationContext(),"Enter Full Name !",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
            btnSignup.startAnimation(shakeError());
            return;
        }
        if (TextUtils.isEmpty(Mobile)){
            TastyToast.makeText(getApplicationContext(),"Enter Mobile Number !",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
            btnSignup.startAnimation(shakeError());
            return;
        }
        if(mobile.length()<10){
            TastyToast.makeText(getApplicationContext(),"Enter Valid Mobile Number!",TastyToast.LENGTH_LONG,TastyToast.WARNING).show();
            return;
        }
        if (TextUtils.isEmpty(adhar)){
            TastyToast.makeText(getApplicationContext(),"Enter Aadhar Number !",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
            btnSignup.startAnimation(shakeError());
            return;
        }
        if (city.equals("None")){
            TastyToast.makeText(getApplicationContext(),"Select City !",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
            btnSignup.startAnimation(shakeError());
            return;
        }
        if (group.equals("None")){
            TastyToast.makeText(getApplicationContext(),"Select Blood Group !",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
            btnSignup.startAnimation(shakeError());
            return;
        }

        Donor donor = new Donor(name,Mobile,group,city,lat,lng,adhar);
        DatabaseReference myRef = database.getReference("donors");
        myRef.child(city).child(group).push().setValue(donor);

        onReg();
    }


}