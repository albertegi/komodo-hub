package com.creekscholar.komodohub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText roleEditText;
    private Button registerButton, registerSignInBtn, signInAccount;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.registerUsername);
        emailEditText = findViewById(R.id.registerEmail);
        passwordEditText = findViewById(R.id.registerPassword);
        roleEditText = findViewById(R.id.registerRole);
        registerButton = findViewById(R.id.registerBtn);
        userDao = new UserDao(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String role = roleEditText.getText().toString().trim();

                if (userDao.addUser(username, email, password, role)) {
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        signInAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//            }
//        });

//        registerSignInBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//            }
//        });

//        usernameEditText = findViewById(R.id.email);
//        emailEditText = findViewById(R.id.registerEmail);
//        passwordEditText = findViewById(R.id.password);
//        confirmPasswordEditText = findViewById(R.id.registerConfirmPassword);
//        registerBtn = findViewById(R.id.registerButton);
//        registerSignInBtn = findViewById(R.id.registrationSignIn);

//        registerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String username = usernameEditText.getText().toString();
//                String email = emailEditText.getText().toString();
//                String password = passwordEditText.getText().toString();
//                String confirmPassword = confirmPasswordEditText.getText().toString();
//
//                //create an object of the database class (DatabaseConnection)
//                DatabaseConnection dbConn = new DatabaseConnection(getApplicationContext(), "komodohubDB", null, 1);
//                if(username.length()==0 || email.length()==0 || password.length()==0 || confirmPassword.length()==0){
//                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
//                }else{
//                    if(password.compareTo(confirmPassword)==0){
//                        if(isValid(password)){
//                            // call the register method to save the data into the database
//                            dbConn.register(username, email, password);
//                            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                        }else{
//                            Toast.makeText(getApplicationContext(), "Password must contain at least 8 characters, having letter, digits and special characters", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }else{
//                        Toast.makeText(getApplicationContext(), "Password and confirm password didn't match", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//
//            }
//        });

//        registerSignInBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//            }
//        });

    }
//    public static boolean isValid(String acceptablePassword){
//        int pw1 = 0, pw2 = 0, pw3 = 0;
//        if(acceptablePassword.length() < 8){
//            return false;
//        }else{
//            for(int p = 0; p < acceptablePassword.length(); p++){
//                if(Character.isLetter(acceptablePassword.charAt(p))){
//                    pw1 = 1;
//                }
//            }
//            for(int r = 0; r < acceptablePassword.length(); r++){
//                if(Character.isDigit(acceptablePassword.charAt(r))){
//                    pw2 = 1;
//                }
//            }
//            for(int s =0; s < acceptablePassword.length(); s++){
//                char c = acceptablePassword.charAt(s);
//                if(c > 33 && c <= 46 || c== 64){
//                    pw3 = 1;
//                }
//            }
//            if(pw1==1 && pw2==1 && pw3 ==1)
//                return true;
//            return false;
//        }
//    }



}