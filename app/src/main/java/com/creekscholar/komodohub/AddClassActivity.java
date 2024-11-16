package com.creekscholar.komodohub;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AddClassActivity extends AppCompatActivity {
    private EditText classNameInput, subjectInput;
    private Spinner teacherSpinner, schoolSpinner;
    private Button submitClassButton;

    private DatabaseConnection databaseConnection;
    private long selectedTeacherId = -1;
    private long selectedSchoolId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_class);


//        // Initialize UI elements
//        classNameInput = findViewById(R.id.class_name_input);
//        subjectInput = findViewById(R.id.subject_input);
//        teacherSpinner = findViewById(R.id.teacher_spinner);
//        schoolSpinner = findViewById(R.id.school_spinner);
//        submitClassButton = findViewById(R.id.submit_class_button);
//
//        // Initialize database connection
//        databaseConnection = new DatabaseConnection(this);
//
//        // Load teachers and schools for the spinners
//        loadTeachers();
//        loadSchools();
//
//        // Set up the submit button
//        submitClassButton.setOnClickListener(v -> addClass());
    }

//    private void loadTeachers() {
//        List<String> teacherNames = databaseConnection.getTeacherNames(); // Get teacher names
//        List<Long> teacherIds = databaseConnection.getTeacherIds(); // Get teacher IDs
//
//        ArrayAdapter<String> teacherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teacherNames);
//        teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        teacherSpinner.setAdapter(teacherAdapter);
//
//        teacherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selectedTeacherId = teacherIds.get(position); // Map selected teacher name to teacher ID
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                selectedTeacherId = -1;
//            }
//        });
//    }

    // Method to load schools from the database and populate the schoolSpinner
//    private void loadSchools() {
//        List<String> schoolNames = databaseConnection.getSchoolNames(); // Get school names
//        List<Long> schoolIds = databaseConnection.getSchoolIds(); // Get school IDs
//
//        ArrayAdapter<String> schoolAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, schoolNames);
//        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        schoolSpinner.setAdapter(schoolAdapter);
//
//        schoolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selectedSchoolId = schoolIds.get(position); // Map selected school name to school ID
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                selectedSchoolId = -1;
//            }
//        });
//    }


    // Method to add a new class to the database
//    private void addClass() {
//        String className = classNameInput.getText().toString().trim();
//        String subject = subjectInput.getText().toString().trim();
//
//        // Input validation
//        if (className.isEmpty() || subject.isEmpty() || selectedTeacherId == -1 || selectedSchoolId == -1) {
//            Toast.makeText(this, "Please fill out all fields and select valid options.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Insert the class into the database
//        long result = databaseConnection.addClass(className, subject, selectedTeacherId, selectedSchoolId);
//
//        if (result != -1) {
//            Toast.makeText(this, "Class added successfully!", Toast.LENGTH_LONG).show();
//            finish(); // Close activity
//        } else {
//            Toast.makeText(this, "Failed to add class.", Toast.LENGTH_LONG).show();
//        }
//    }

}