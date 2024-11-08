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

public class AddTeacherActivity extends AppCompatActivity {
    private EditText teacherNameInput, teacherEmailInput, teacherPasswordInput, teacherSpecializationInput, teacherHireDateInput;
    private DatabaseConnection databaseConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_teacher);


        // Initialize input fields
        teacherNameInput = findViewById(R.id.teacher_name_input);
        teacherEmailInput = findViewById(R.id.teacher_email_input);
        teacherPasswordInput = findViewById(R.id.teacher_password_input);
        teacherSpecializationInput = findViewById(R.id.teacher_specialization_input);
        teacherHireDateInput = findViewById(R.id.teacher_hire_date_input);

        Button submitTeacherButton = findViewById(R.id.submit_teacher_button);

        long schoolAdminId = getIntent().getLongExtra("SchoolAdminID", -1);
        databaseConnection = new DatabaseConnection(this);

        submitTeacherButton.setOnClickListener(v -> {
            String name = teacherNameInput.getText().toString().trim();
            String email = teacherEmailInput.getText().toString().trim();
            String password = teacherPasswordInput.getText().toString().trim();
            String specialization = teacherSpecializationInput.getText().toString().trim();
            String hireDate = teacherHireDateInput.getText().toString().trim();

            long result = databaseConnection.addTeacher(name, email, password, specialization, hireDate, schoolAdminId);
            if (result != -1) {
                Toast.makeText(this, "Teacher added successfully!", Toast.LENGTH_LONG).show();
                finish(); // Return to previous activity
            } else {
                Toast.makeText(this, "Failed to add teacher!", Toast.LENGTH_LONG).show();
            }
        });




    }
}