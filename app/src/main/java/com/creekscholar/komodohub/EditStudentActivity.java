package com.creekscholar.komodohub;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditStudentActivity extends AppCompatActivity {
//    private EditText nameInput, emailInput, gradeInput, programInput;
//    private DatabaseConnection databaseConnection;
//    private long studentId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_edit_student);
//
//        // Initialize UI components
//        nameInput = findViewById(R.id.student_name_input);
//        emailInput = findViewById(R.id.student_email_input);
//        gradeInput = findViewById(R.id.student_grade_input);
//        programInput = findViewById(R.id.student_program_input);
//
//        Button updateButton = findViewById(R.id.update_student_button);
//        Button deleteButton = findViewById(R.id.delete_student_button);
//
//        // Retrieve student ID passed from previous activity
//        studentId = getIntent().getLongExtra("studentId", -1);
//        databaseConnection = new DatabaseConnection(this);
//
//        // Load student details
//        loadStudentDetails();
//
//        // Update student on button click
//        updateButton.setOnClickListener(v -> {
//            String name = nameInput.getText().toString().trim();
//            String email = emailInput.getText().toString().trim();
//            String gradeLevel = gradeInput.getText().toString().trim();
//            String enrolledProgram = programInput.getText().toString().trim();
//
//            boolean updated = databaseConnection.updateStudent(studentId, name, email, gradeLevel, enrolledProgram);
//            if (updated) {
//                Toast.makeText(this, "Student updated successfully!", Toast.LENGTH_LONG).show();
//                finish();
//            } else {
//                Toast.makeText(this, "Failed to update student!", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        // Delete student on button click
////        deleteButton.setOnClickListener(v -> {
////            boolean deleted = databaseConnection.deleteStudent(studentId);
////            if (deleted) {
////                Toast.makeText(this, "Student deleted successfully!", Toast.LENGTH_LONG).show();
////                finish();
////            } else {
////                Toast.makeText(this, "Failed to delete student!", Toast.LENGTH_LONG).show();
////            }
////        });
//
//    }
//
//    private void loadStudentDetails() {
//        User student = databaseConnection.getStudentById(studentId);
//        if (student != null) {
//            nameInput.setText(student.getName());
//            emailInput.setText(student.getEmail());
//            gradeInput.setText(student.getGradeLevel());
//            programInput.setText(student.getEnrolledProgram());
//        }
//    }
//



}