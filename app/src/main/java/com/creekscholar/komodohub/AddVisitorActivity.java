package com.creekscholar.komodohub;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddVisitorActivity extends AppCompatActivity {
    private EditText nameInput, emailInput, passwordInput, purposeInput;
    private DatabaseConnection databaseConnection;

    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_visitor);

        databaseConnection = new DatabaseConnection(this);

        //userId = getIntent().getLongExtra("UserID", -1);

        // Initialize views
        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        purposeInput = findViewById(R.id.purposeInput);

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> addVisitor());


    }

    private void addVisitor() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String purpose = purposeInput.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || purpose.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = databaseConnection.addVisitor(name, email, password, purpose);
        if (result != -1) {
            Toast.makeText(this, "Visitor added successfully", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Error adding visitor", Toast.LENGTH_LONG).show();
        }
    }
}