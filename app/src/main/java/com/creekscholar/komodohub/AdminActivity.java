package com.creekscholar.komodohub;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {
    private EditText adminNameInput, adminEmailInput, adminPasswordInput;
    private EditText schoolNameInput, schoolAddressInput, schoolSubscriptionStatus;
    private Button addSchoolButton;
    private DatabaseConnection databaseConnection;


//    private CardView manageSchoolsCard;
//    private CardView manageCommunitiesCard;
//    private CardView sendEmailsCard;
//    private CardView exitCard;



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


        // Initialize database helper
        //DatabaseConnection dbConnection = new DatabaseConnection(this);

        // Initialize database helper correctly
        databaseConnection = new DatabaseConnection(this);




        addSchoolButton.setOnClickListener(v -> {
            String adminName = adminNameInput.getText().toString().trim();
            String adminEmail = adminEmailInput.getText().toString().trim();
            String adminPassword = adminPasswordInput.getText().toString().trim();
            String schoolName = schoolNameInput.getText().toString().trim();
            String schoolAddress = schoolAddressInput.getText().toString().trim();
            String subscriptionStatus = schoolSubscriptionStatus.getText().toString().trim();

            // Add the SchoolAdmin user and the school
            long schoolAdminId = databaseConnection.addSchoolAdmin(adminName, adminEmail, adminPassword);
            if (schoolAdminId != -1) {
                long schoolId = databaseConnection.addSchool(schoolName, schoolAddress, subscriptionStatus, "Sample Payment Details", schoolAdminId);
                if (schoolId != -1) {
                    Toast.makeText(this, "School and Admin added successfully!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Failed to add school!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Failed to add SchoolAdmin!", Toast.LENGTH_LONG).show();
            }
        });


    }
}