package com.creekscholar.komodohub;

import static com.creekscholar.komodohub.DatabaseConnection.COLUMN_STUDENT_ID;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
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

public class AssessStudentsActivity extends AppCompatActivity {
    private Button btnSubmitAssessment;
    private DatabaseConnection databaseConnection;
    private EditText etStudentID;
    private EditText etAssessmentDetails;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_assess_students);


        // Find the UI elements
        EditText etStudentID = findViewById(R.id.etStudentID);
        EditText etAssessmentDetails = findViewById(R.id.etAssessmentDetails);
        Button btnSubmitAssessment = findViewById(R.id.btnSubmitAssessment);

        databaseConnection = new DatabaseConnection(this);
        db = databaseConnection.getWritableDatabase();

        // Set onClickListener for the "Submit Assessment" button
        btnSubmitAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input from user
                String studentID = etStudentID.getText().toString().trim();
                String assessmentDetails = etAssessmentDetails.getText().toString().trim();

                // Validate input fields
                if (studentID.isEmpty() || assessmentDetails.isEmpty()) {
                    Toast.makeText(AssessStudentsActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get current date (you can adjust the format as needed)
                String assessmentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                // Prepare the data to insert
                ContentValues values = new ContentValues();
                values.put(DatabaseConnection.COLUMN_STUDENT_ID, studentID); // Assuming studentID is numeric
                values.put(DatabaseConnection.COLUMN_ASSESSMENT_DETAILS, assessmentDetails);
                values.put(DatabaseConnection.COLUMN_ASSESSMENT_DATE, assessmentDate);

                // Insert the assessment into the database
                long result = db.insert(DatabaseConnection.TABLE_ASSESSMENTS, null, values);

                // Show a message based on the result
                if (result == -1) {
                    Toast.makeText(AssessStudentsActivity.this, "Failed to save assessment.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AssessStudentsActivity.this, "Assessment saved successfully.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}