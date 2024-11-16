package com.creekscholar.komodohub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.Context;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {


    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton, createAccoutButton;
    private UserDao userDAO;

    private EditText textView3;

    public static final String PREFERENCES = "UserPrefs";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.loginEmail);
        passwordEditText = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        userDAO = new UserDao(this);

//        userDao = new UserDao(this);
//        emailEditText = findViewById(R.id.email);
//        passwordEditText = findViewById(R.id.password);
//        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

//                if (userDAO.authenticateUser(email, password)) {
//                    // Get the user's role
//                    String role = userDAO.getUserRole(email);
//                    int userId = userDAO.getUserId(email);
//                    long schoolId = userDAO.getSchoolIdByUserId(userId);
//
//                    // Save the role in SharedPreferences
//                    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("UserRole", role);
//                    editor.putString("UserEmail", email); // For future use
//                    if(role == "SchoolAdmin"){
//                        editor.putString("SchoolId", schoolId + ""); // For future use
//                    }
//                    editor.apply();
//
//
//
//                    // Redirect to Main Activity or Dashboard
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    //finish();
//                } else {
//                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
//                }

                if (userDAO.authenticateUser(email, password)) {
                    // Get the user's role
                    String role = userDAO.getUserRole(email);
                    int userId = userDAO.getUserId(email);
                    long schoolId = userDAO.getSchoolIdByUserId(userId);

                    // Save role and SchoolId in SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("UserRole", role);
                    editor.putString("UserEmail", email); // For future use
                    if("SchoolAdmin".equals(role)){  // Use equals for string comparison
                        editor.putString("SchoolId", String.valueOf(schoolId)); // Convert long to String
                    }
                    editor.apply();

                    // Redirect to Main Activity or Dashboard
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
}