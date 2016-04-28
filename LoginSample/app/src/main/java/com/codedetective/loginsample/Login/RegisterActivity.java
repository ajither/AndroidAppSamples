package com.codedetective.loginsample.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codedetective.loginsample.R;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private EditText editTextPhone;

    private Button buttonRegister;

    private static final String REGISTER_URL = "http://www.devworkz.org/codedetctive/login/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextName = (EditText) findViewById(R.id.etregName);
        editTextUsername = (EditText) findViewById(R.id.etregUserName);
        editTextPassword = (EditText) findViewById(R.id.etregPassword);
        editTextEmail = (EditText) findViewById(R.id.etregeMail);
        editTextPhone = (EditText) findViewById(R.id.etregPhonenumber);
        buttonRegister = (Button) findViewById(R.id.register_button);
        buttonRegister.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
    }
    private void registerUser() {
        String name = editTextName.getText().toString().trim().toLowerCase();
        String username = editTextUsername.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim().toLowerCase();
        String email = editTextEmail.getText().toString().trim().toLowerCase();
        String mobileno = editTextPhone.getText().toString();

        register(name, username, password, email,mobileno);
    }
    private void register(String name, String username, String password, String email, String mobileno) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                if(s.trim().equals("successfully registered")){
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);}
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("name", params[0]);
                data.put("username",params[1]);
                data.put("password",params[2]);
                data.put("email", params[3]);
                data.put("mobileno", params[4]);

                String result = ruc.sendPostRequest(REGISTER_URL,data);
                return  result;


            }


        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name, username, password, email, mobileno);

    }
}
