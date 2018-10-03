package com.timeweueh.egub.e_gub;
import android.content.Intent;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.text.TextUtils;

import android.view.View;

import android.view.WindowManager;

import android.widget.Button;

import android.widget.EditText;

import android.widget.ProgressBar;
import android.widget.Toast;



import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Login extends AppCompatActivity {


    private EditText mEmailField;

    private EditText mPasswordField;

    private Button mloginBtn;


    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        //Membuat FullScreen

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mEmailField = (EditText) findViewById(R.id.email);

        mPasswordField = (EditText) findViewById(R.id.password);

        mloginBtn = (Button) findViewById(R.id.btn_login);


        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override

            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {

                    startActivity(new Intent(Login.this, Main3Activity.class));

                }

            }

        };


        mloginBtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                //startSignIn();
userLogin();
            }

        });

    }

    private void userLogin() {
        //first getting the values
        final String username = mEmailField.getText().toString();
        final String password = mPasswordField.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            mEmailField.setError("Please enter your username");
            mEmailField.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Please enter your password");
            mPasswordField.requestFocus();
            return;
        }

        //if everything is fine

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        User user = new User(
                                userJson.getInt("id"),
                                userJson.getString("email"),
                                userJson.getString("password"),
                                userJson.getString("status"),
                                userJson.getString("level")

                        );
                        //starting the profile activity
                        finish();

                        startActivity(new Intent(getApplicationContext(), Main3Activity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("email", username);
                params.put("password", password);
                params.put("status", "on");
                params.put("level", "umum");

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();}

    @Override

    protected void onStart() {

        super.onStart();


        mAuth.addAuthStateListener(mAuthListener);

    }


    private void startSignIn() {

        String email = mEmailField.getText().toString();

        String password = mPasswordField.getText().toString();


        if (TextUtils.isEmpty(email) | TextUtils.isEmpty(password)) {

            Toast.makeText(Login.this, "Fields are Empty", Toast.LENGTH_SHORT).show();

        } else {


            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener() {

                @Override

                public void onComplete(@NonNull Task task) {


                    if (!task.isSuccessful()) {

                        Toast.makeText(Login.this, "Login Problem", Toast.LENGTH_SHORT).show();

                    }


                }

            });

        }
    }
}