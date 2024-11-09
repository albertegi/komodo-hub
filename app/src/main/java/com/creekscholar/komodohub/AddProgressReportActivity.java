package com.creekscholar.komodohub;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddProgressReportActivity extends AppCompatActivity {


    private EditText gradeInput, feedbackInput;
    private Spinner studentSpinner, classSpinner;
    private Button submitButton;
    private DatabaseConnection databaseConnection;
    private int teacherID = 1; // Assume teacherID is logged in or passed from previous activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_progress_report);

        // Initialize views
        gradeInput = findViewById(R.id.gradeInput);
        feedbackInput = findViewById(R.id.feedbackInput);
        studentSpinner = findViewById(R.id.studentSpinner);
        classSpinner = findViewById(R.id.classSpinner);
        submitButton = findViewById(R.id.submitButton);

        // Initialize Database Connection
        databaseConnection = new DatabaseConnection(this);

        // Populate spinners with data (for example, students and classes)
        populateSpinners();

        // Listener for submitting the progress report
        submitButton.setOnClickListener(v -> submitProgressReport());

    }

    private void populateSpinners() {
        // Assume we are loading data for students and classes from the database
        // For simplicity, we'll use static data
        ArrayAdapter<String> studentAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new String[]{"Student 1", "Student 2", "Student 3"});
        studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studentSpinner.setAdapter(studentAdapter);

        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new String[]{"Class A", "Class B", "Class C"});
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(classAdapter);
    }


    private void submitProgressReport() {
        String grade = gradeInput.getText().toString().trim();
        String feedback = feedbackInput.getText().toString().trim();
        int studentID = studentSpinner.getSelectedItemPosition() + 1; // Assuming student ID is selected from the spinner
        int classID = classSpinner.getSelectedItemPosition() + 1; // Assuming class ID is selected from the spinner

        if (grade.isEmpty() || feedback.isEmpty()) {
            Toast.makeText(this, "Grade and feedback are required", Toast.LENGTH_SHORT).show();
            return;
        }

        String submissionDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Insert the progress report into the database
        long result = databaseConnection.addProgressReport(grade, feedback, submissionDate, studentID, teacherID, classID);
        if (result != -1) {
            Toast.makeText(this, "Progress report added successfully!", Toast.LENGTH_LONG).show();
            finish(); // Close the activity
        } else {
            Toast.makeText(this, "Failed to add progress report!", Toast.LENGTH_LONG).show();
        }
    }

}