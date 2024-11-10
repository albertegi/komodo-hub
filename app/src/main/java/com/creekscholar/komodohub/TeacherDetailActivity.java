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

public class TeacherDetailActivity extends AppCompatActivity {
    private EditText nameInput, emailInput, specializationInput, hireDateInput;
    private DatabaseConnection databaseConnection;
    private int teacherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher_detail);

        nameInput = findViewById(R.id.teacher_name_input);
        emailInput = findViewById(R.id.teacher_email_input);
        specializationInput = findViewById(R.id.teacher_specialization_input);
        hireDateInput = findViewById(R.id.teacher_hire_date_input);
        Button updateButton = findViewById(R.id.update_teacher_button);
        Button deleteButton = findViewById(R.id.delete_teacher_button);

        databaseConnection = new DatabaseConnection(this);
        teacherId = getIntent().getIntExtra("TeacherID", -1);

        loadTeacherDetails();

        updateButton.setOnClickListener(v -> updateTeacher());
        deleteButton.setOnClickListener(v -> deleteTeacher());


    }

    private void loadTeacherDetails() {
        User teacher = databaseConnection.getTeacherById(teacherId); // Fetch using method in DatabaseConnection
        if (teacher != null) {
            nameInput.setText(teacher.getName());
            emailInput.setText(teacher.getEmail());
            specializationInput.setText(teacher.getSpecialization());
            hireDateInput.setText(teacher.getHireDate());
        }
    }


    private void updateTeacher() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String specialization = specializationInput.getText().toString().trim();
        String hireDate = hireDateInput.getText().toString().trim();

        if (databaseConnection.updateTeacher(teacherId, name, email, specialization, hireDate)) {
            Toast.makeText(this, "Teacher updated successfully!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Update failed!", Toast.LENGTH_LONG).show();
        }
    }

    private void deleteTeacher() {
        if (databaseConnection.deleteTeacher(teacherId)) {
            Toast.makeText(this, "Teacher deleted successfully!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Delete failed!", Toast.LENGTH_LONG).show();
        }
    }



}