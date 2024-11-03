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

                if (userDAO.authenticateUser(email, password)) {
                    // Get the user's role
                    String role = userDAO.getUserRole(email);

                    // Save the role in SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("UserRole", role);
                    editor.putString("UserEmail", email); // For future use
                    editor.apply();


                    // Redirect to Main Activity or Dashboard
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    //finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        textView3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
//            }
//        });

//        createAccoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
//            }
//        });




//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String email = emailEditText.getText().toString();
//                String password = passwordEditText.getText().toString();
//                String role = userDao.authenticateUser(email, password);
//
//                if(role != null){
//                    // Direct user based on role
//                    Toast.makeText(LoginActivity.this, "Its successful", Toast.LENGTH_SHORT).show();
//                    if(email.equals("albertegi@gmail.com") && password.equals("password123") && role.equals("SystemAdmin")){
//                        Toast.makeText(LoginActivity.this, "fetching from the db", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
//                    }
//                }
//
//            }
//        });

//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = emailEditText.getText().toString();
//                String password = passwordEditText.getText().toString();
//                String role = userDao.authenticateUser(email, password);
//
//                if (role != null) {
//                    // Direct user based on role
//                    if (role.equals("SystemAdmin")) {
//                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
////                    } else if (role.equals("Teacher")) {
////                        startActivity(new Intent(LoginActivity.this, TeacherHomeActivity.class));
////                    } else if (role.equals("Student")) {
////                        startActivity(new Intent(LoginActivity.this, StudentHomeActivity.class));
////                    } else if (role.equals("SchoolAdmin")) {
////                        startActivity(new Intent(LoginActivity.this, SchoolAdminHomeActivity.class));
////                    }
//                } else {
//                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        usernameEditText = findViewById(R.id.registerUsername);
//        passwordEditText = findViewById(R.id.registerPassword);
//        loginBtn = findViewById(R.id.loginButton);
//        createAccountBtn = findViewById(R.id.registrationSignIn);

//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String username = usernameEditText.getText().toString();
//                String password = passwordEditText.getText().toString();
//
//                //create an object of the database class (DatabaseConnection)
//                DatabaseConnection dbConn = new DatabaseConnection(getApplicationContext(), "komodohubDB", null, 1);
//
//                if(username.length()==0 || password.length()==0){
//                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
//                }else{
//                    if(dbConn.login(username, password) == 1){
//                        Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
//                        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("username",username);
//                        // to save our data with key and value
//                        editor.apply();
//                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
//                    }else{
//                        Toast.makeText(getApplicationContext(), "Invalid Username and Password", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            }
//        });

//        createAccountBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
//            }
//        });




    }
}