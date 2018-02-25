package com.example.saad.toptaseapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.facebook.FacebookSdk;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button btn_Login;
    Button btn_FacebookLogin;
    EditText txt_Username,txt_Password;
    CallbackManager mCallbackManager;
    TextView txtRegister;
    String name,activity_started_by;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.setApplicationId(getResources().getString(R.string.facebook_app_id));
        FacebookSdk.sdkInitialize(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);

        Bundle b = getIntent().getExtras();
        activity_started_by = b.getString("From");

        btn_Login= findViewById(R.id.btn_Login);
        txt_Username= findViewById(R.id.txt_Username);
        txt_Password= findViewById(R.id.txt_numberOfPeople);
        txtRegister = findViewById(R.id.txtRegister);

        txtRegister.setText(Html.fromHtml("Don't have an account? <u>Sign up</u>"));
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this,Signup.class);
                Bundle b= new Bundle();
                b.putString("callerActivity","null");
                i.putExtras(b);
                startActivity(i);
            }
        });

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        btn_FacebookLogin = findViewById(R.id.btn_FacebookLogin);

        btn_FacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.v("LoginActivity", response.toString());

                                        // Application code
                                        try {
                                            String email = object.getString("email");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        try {
                                            name = object.getString("public_profile"); // 01/31/1980 format
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Log.d("FacebookLog", "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d("FacebookLog", "facebook:onCancel");

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(Login.this, error.toString(),
                                Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email= txt_Username.getText().toString();
                String password= txt_Password.getText().toString();

                if(TextUtils.isEmpty(email))
                {Toast.makeText(Login.this, "Enter Email Address!",
                        Toast.LENGTH_SHORT).show();
                return;
                }
                if(TextUtils.isEmpty(password))
                {Toast.makeText(Login.this, "Enter Password",
                        Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    name=user.getDisplayName();
                                   updateUI(name);
                                } else {

                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Login.this, "Authentication failed 1",
                                            Toast.LENGTH_SHORT).show();

                                }


                            }
                        });
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null){
            currentUser.getDisplayName();
            updateUI(name);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(final AccessToken token) {
        Log.d("FacebookLog", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FacebookLog", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(name);
                            Toast.makeText(Login.this,name,
                                    Toast.LENGTH_SHORT).show();

                            Bundle b= new Bundle();
                            b.putString("Name",user.getDisplayName());
                            b.putString("Email",user.getEmail());
                            b.putString("callerActivity","Facebook");
                            Intent i = new Intent(Login.this,Signup.class);
                            i.putExtras(b);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FacebookLog", "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this,task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void updateUI(String userName) {
        Toast.makeText(Login.this, "You're logged in",
                Toast.LENGTH_SHORT).show();

        if(activity_started_by.equals("NavigationDrawer")){

        Intent i = new Intent(Login.this,NavigationDrawer.class);
        startActivity(i);
        finish();        }
        else
        {
            Intent i = new Intent(Login.this,Receipt.class);
            startActivity(i);
            finish();
        }

    }

}
