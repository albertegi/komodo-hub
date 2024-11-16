package com.creekscholar.komodohub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ManageSchoolAdminActivity extends AppCompatActivity {
    private ListView schoolAdminListView;
    private EditText adminNameInput, adminEmailInput, adminPasswordInput, schoolNameInput, schoolAddressInput, subscriptionStatusInput, paymentDetailsInput;
    private Button addButton, updateButton, deleteButton;
    private DatabaseConnection databaseConnection;
    private List<SchoolAdmin> schoolAdminList;
    private ArrayAdapter<SchoolAdmin> adapter;
    private long selectedAdminId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_school_admin);

        // Initialize UI components
        schoolAdminListView = findViewById(R.id.school_admin_list_view);
        adminNameInput = findViewById(R.id.admin_name_input);
        adminEmailInput = findViewById(R.id.admin_email_input);
        adminPasswordInput = findViewById(R.id.admin_password_input);
        schoolNameInput = findViewById(R.id.school_name_input);
        schoolAddressInput = findViewById(R.id.school_address_input);
        subscriptionStatusInput = findViewById(R.id.subscription_status_input);
        paymentDetailsInput = findViewById(R.id.payment_details_input);

        addButton = findViewById(R.id.add_button);
        updateButton = findViewById(R.id.update_button);
        deleteButton = findViewById(R.id.delete_button);

        databaseConnection = new DatabaseConnection(this);

        // Load initial data into ListView
        loadSchoolAdminList();

        // Handle ListView item selection for editing
        schoolAdminListView.setOnItemClickListener((parent, view, position, id) -> {
            SchoolAdmin selectedAdmin = schoolAdminList.get(position);
            loadAdminDetailsForEditing(selectedAdmin);
        });

        // Add new School Admin and School
        addButton.setOnClickListener(v -> addSchoolAdmin());

        // Update selected School Admin and School
        updateButton.setOnClickListener(v -> updateSchoolAdmin());

        // Delete selected School Admin and School
        deleteButton.setOnClickListener(v -> deleteSchoolAdmin());
    }

    private void loadSchoolAdminList() {
        schoolAdminList = databaseConnection.getAllSchoolAdmins();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, schoolAdminList);
        schoolAdminListView.setAdapter(adapter);
    }

    private void loadAdminDetailsForEditing(SchoolAdmin admin) {
        selectedAdminId = admin.getUserId();
        adminNameInput.setText(admin.getName());
        adminEmailInput.setText(admin.getEmail());
        adminPasswordInput.setText(admin.getPassword());
        schoolNameInput.setText(admin.getSchoolName());
        schoolAddressInput.setText(admin.getSchoolAddress());
        subscriptionStatusInput.setText(admin.getSubscriptionStatus());
        paymentDetailsInput.setText(admin.getPaymentDetails());
    }

    private void addSchoolAdmin() {
        String adminName = adminNameInput.getText().toString().trim();
        String adminEmail = adminEmailInput.getText().toString().trim();
        String password = adminPasswordInput.getText().toString().trim();
        String schoolName = schoolNameInput.getText().toString().trim();
        String schoolAddress = schoolAddressInput.getText().toString().trim();
        String subscriptionStatus = subscriptionStatusInput.getText().toString().trim();
        String paymentDetails = paymentDetailsInput.getText().toString().trim();

        long newAdminId = databaseConnection.addSchoolAdmin(adminName, adminEmail, password);  // Use default password
        if (newAdminId != -1) {
            long schoolId = databaseConnection.addSchool(schoolName, schoolAddress, subscriptionStatus, paymentDetails, newAdminId);
            if(schoolId != -1) {
                Toast.makeText(this, "School Admin and School added successfully!", Toast.LENGTH_SHORT).show();
                loadSchoolAdminList();
                clearInputs();
            }else {
                Toast.makeText(this, "Failed to add school!", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Failed to add SchoolAdmin!", Toast.LENGTH_LONG).show();
        }

    }



    private void updateSchoolAdmin() {
        if (selectedAdminId != -1) {
            String adminName = adminNameInput.getText().toString().trim();
            String adminEmail = adminEmailInput.getText().toString().trim();
            String schoolName = schoolNameInput.getText().toString().trim();
            String schoolAddress = schoolAddressInput.getText().toString().trim();
            String subscriptionStatus = subscriptionStatusInput.getText().toString().trim();
            String paymentDetails = paymentDetailsInput.getText().toString().trim();

            databaseConnection.updateSchoolAdmin(selectedAdminId, adminName, adminEmail, schoolName, schoolAddress, subscriptionStatus, paymentDetails);
            Toast.makeText(this, "School Admin and School updated successfully!", Toast.LENGTH_SHORT).show();
            loadSchoolAdminList();
            clearInputs();
        }
    }

    private void deleteSchoolAdmin() {
        if (selectedAdminId != -1) {
            databaseConnection.deleteSchoolAdmin(selectedAdminId);
            Toast.makeText(this, "School Admin and School deleted successfully!", Toast.LENGTH_SHORT).show();
            loadSchoolAdminList();
            clearInputs();
        }
    }

    private void clearInputs() {
        selectedAdminId = -1;
        adminNameInput.setText("");
        adminEmailInput.setText("");
        schoolNameInput.setText("");
        schoolAddressInput.setText("");
        subscriptionStatusInput.setText("");
        paymentDetailsInput.setText("");
    }
}
