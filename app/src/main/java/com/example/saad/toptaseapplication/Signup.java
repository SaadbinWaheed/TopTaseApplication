package com.example.saad.toptaseapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Signup extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference OrdersRef;

    private EditText inputEmail,inputName, inputNumber,inputAddress;
    private Button  btnSignUp, btnResetPassword;
    private ProgressBar progressBar;

    String username,email,phoneNumber;
// ...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Bundle b = getIntent().getExtras();
        String callerActivity= b.getString("callerActivity");

        if(callerActivity.equals("Facebook"))
        {
            username=b.getString("Name");
            email= b.getString("Email");
        }

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        OrdersRef = FirebaseDatabase.getInstance().getReference();
        OrdersRef=OrdersRef.child("Customers_HomeDelivery");



        btnSignUp = (Button) findViewById(R.id.btn_Signup);

        inputEmail = (EditText) findViewById(R.id.txt_email);
        inputName = (EditText) findViewById(R.id.txt_name);
        inputNumber = (EditText) findViewById(R.id.txt_number);
        inputAddress = (EditText) findViewById(R.id.txt_address);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        inputEmail.setText(email);
        inputName.setText(username);


        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                final String name = inputName.getText().toString().trim();
                final String number = inputNumber.getText().toString().trim();
                final String address = inputAddress.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter Name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(number)) {
                    Toast.makeText(getApplicationContext(), "Enter Number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(getApplicationContext(), "Enter Address!", Toast.LENGTH_SHORT).show();
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);
                //create user
                mAuth.createUserWithEmailAndPassword(email, number)
                        .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(Signup.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Signup.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    HashMap<String,String> map = new HashMap<String, String>();

                                    map.put("Email",email);
                                    map.put("Name",name);
                                    map.put("Phone Number",number);
                                    map.put("Address",address);
                                    String key = OrdersRef.push().getKey();

                                    OrdersRef.child(key).setValue(map);


                                    startActivity(new Intent(Signup.this, NavigationDrawer.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }
}
