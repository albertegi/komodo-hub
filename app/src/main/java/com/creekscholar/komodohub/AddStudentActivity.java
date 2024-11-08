package com.creekscholar.komodohub;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddStudentActivity extends AppCompatActivity {
    private EditText studentNameInput, studentEmailInput, studentPasswordInput, studentGradeInput, enrolledProgramInput;
    private DatabaseConnection databaseConnection;
    private long schoolId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);

        // Retrieve the School ID and Class ID passed from the previous activity
        schoolId = getIntent().getLongExtra("SchoolID", -1);
        long classId = getIntent().getLongExtra("ClassID", -1);

        // Initialize UI components
        studentNameInput = findViewById(R.id.student_name_input);
        studentEmailInput = findViewById(R.id.student_email_input);
        studentPasswordInput = findViewById(R.id.student_password_input);
        studentGradeInput = findViewById(R.id.student_grade_input);
        enrolledProgramInput = findViewById(R.id.enrolled_program_input);
        Button submitStudentButton = findViewById(R.id.submit_student_button);

        databaseConnection = new DatabaseConnection(this);

      submitStudentButton.setOnClickListener(v -> {
            String name = studentNameInput.getText().toString().trim();
            String email = studentEmailInput.getText().toString().trim();
            String password = studentPasswordInput.getText().toString().trim();
            String gradeLevel = studentGradeInput.getText().toString().trim();
            String enrolledProgram = enrolledProgramInput.getText().toString().trim();

            long result = databaseConnection.addStudent(name, email, password, gradeLevel, enrolledProgram, classId, schoolId);
            if (result != -1) {
                Toast.makeText(this, "Student added successfully!", Toast.LENGTH_LONG).show();
                finish(); // Return to previous activity
            } else {
                Toast.makeText(this, "Failed to add student!", Toast.LENGTH_LONG).show();
            }
        });
    }
}