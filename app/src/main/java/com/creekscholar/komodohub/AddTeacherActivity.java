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
    private EditText teacherNameInput, teacherEmailInput, teacherPasswordInput;
    private DatabaseConnection databaseConnection;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_teacher);

        // Retrieve SchoolAdminID from the intent
        long schoolAdminId = getIntent().getLongExtra("SchoolAdminID", -1);

        teacherNameInput = findViewById(R.id.teacher_name_input);
        teacherEmailInput = findViewById(R.id.teacher_email_input);
        teacherPasswordInput = findViewById(R.id.teacher_password_input);
        Button submitTeacherButton = findViewById(R.id.submit_teacher_button);



        databaseConnection = new DatabaseConnection(this);

        submitTeacherButton.setOnClickListener(v -> {
            String name = teacherNameInput.getText().toString().trim();
            String email = teacherEmailInput.getText().toString().trim();
            String password = teacherPasswordInput.getText().toString().trim();

            // Retrieve schoolId using the SchoolAdminID
            long schoolId = databaseConnection.getSchoolIdByAdminId(schoolAdminId);
            if (schoolId == -1) {
                Toast.makeText(this, "Error: Unable to find school for this admin.", Toast.LENGTH_LONG).show();
                return;
            }

            // Add the teacher with the schoolId
            long result = databaseConnection.addTeacher(name, email, password, schoolId);
            if (result != -1) {
                Toast.makeText(this, "Teacher added successfully!", Toast.LENGTH_LONG).show();
                finish(); // Return to previous activity
            } else {
                Toast.makeText(this, "Failed to add teacher!", Toast.LENGTH_LONG).show();
            }
        });

    }
}