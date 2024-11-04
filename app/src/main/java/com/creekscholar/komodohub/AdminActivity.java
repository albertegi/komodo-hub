package com.creekscholar.komodohub;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {
    private EditText adminNameInput, adminEmailInput, adminPasswordInput;
    private EditText schoolNameInput, schoolAddressInput, schoolSubscriptionStatus;
    private Button addSchoolButton;
    private DatabaseConnection databaseConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);

        // Initialize UI components
        adminNameInput = findViewById(R.id.admin_name_input);
        adminEmailInput = findViewById(R.id.admin_email_input);
        adminPasswordInput = findViewById(R.id.admin_password_input);
        schoolNameInput = findViewById(R.id.school_name_input);
        schoolAddressInput = findViewById(R.id.school_address_input);
        schoolSubscriptionStatus = findViewById(R.id.school_subscription_status);
        addSchoolButton = findViewById(R.id.add_school_button);




        databaseConnection = new DatabaseConnection(this);

        addSchoolButton.setOnClickListener(v -> {
            String adminName = adminNameInput.getText().toString().trim();
            String adminEmail = adminEmailInput.getText().toString().trim();
            String adminPassword = adminPasswordInput.getText().toString().trim();
            String schoolName = schoolNameInput.getText().toString().trim();
            String schoolAddress = schoolAddressInput.getText().toString().trim();
            String subscriptionStatus = schoolSubscriptionStatus.getText().toString().trim();

            // Add the SchoolAdmin user
            long schoolAdminId = databaseConnection.addSchoolAdmin(adminName, adminEmail, adminPassword);
            if (schoolAdminId != -1) {
                // Add the school with the new SchoolAdmin ID
                long schoolId = databaseConnection.addSchool(schoolName, schoolAddress, subscriptionStatus, "Sample Payment Details", schoolAdminId);
                if (schoolId != -1) {
                    Toast.makeText(this, "School and Admin added successfully!", Toast.LENGTH_LONG).show();
                    // Start AddTeacherActivity, passing SchoolAdminID
//                    Intent intent = new Intent(AdminActivity.this, AddTeacherActivity.class);
//                    intent.putExtra("SchoolAdminID", schoolAdminId);
//                    startActivity(intent);
//                    finish();
                    // Intent to navigate back to MainActivity
                    Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                    intent.putExtra("SchoolAdminID", schoolAdminId);
                    startActivity(intent);
                    finish(); // Close AdminActivity
                } else {
                    Toast.makeText(this, "Failed to add school!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Failed to add SchoolAdmin!", Toast.LENGTH_LONG).show();
            }
        });


//        addSchoolButton.setOnClickListener(v -> {
//            String adminName = adminNameInput.getText().toString().trim();
//            String adminEmail = adminEmailInput.getText().toString().trim();
//            String adminPassword = adminPasswordInput.getText().toString().trim();
//            String schoolName = schoolNameInput.getText().toString().trim();
//            String schoolAddress = schoolAddressInput.getText().toString().trim();
//            String subscriptionStatus = schoolSubscriptionStatus.getText().toString().trim();
//
//
//            // Assuming you have the SchoolAdminID retrieved in AdminActivity
//            long schoolAdminId = /* your method to retrieve SchoolAdmin ID */;
//            Intent intent = new Intent(AdminActivity.this, AddTeacherActivity.class);
//            intent.putExtra("SchoolAdminID", schoolAdminId); // Pass SchoolAdmin ID to the next activity
//            startActivity(intent);
//
//
//            // Add the SchoolAdmin user and the school
//            long schoolAdminId = databaseConnection.addSchoolAdmin(adminName, adminEmail, adminPassword);
//            if (schoolAdminId != -1) {
//                long schoolId = databaseConnection.addSchool(schoolName, schoolAddress, subscriptionStatus, "Sample Payment Details", schoolAdminId);
//                if (schoolId != -1) {
//                    Toast.makeText(this, "School and Admin added successfully!", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(this, "Failed to add school!", Toast.LENGTH_LONG).show();
//                }
//            } else {
//                Toast.makeText(this, "Failed to add SchoolAdmin!", Toast.LENGTH_LONG).show();
//            }
//        });





    }
}