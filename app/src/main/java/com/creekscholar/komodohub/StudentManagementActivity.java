package com.creekscholar.komodohub;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentManagementActivity extends AppCompatActivity {

    private EditText gradeLevelInput, enrolledProgramInput;
    private EditText theStudentNameInput, theStudentEmailInput, theStudentPasswordInput, theStudentAdmitDateInput;
    private Spinner classSpinner;
    private Button addStudentButton, updateStudentButton, deleteStudentButton;
    private ListView studentListView;
    private DatabaseConnection databaseConnection;
    private long selectedStudentId = -1;
    private List<Student> studentList;
    private List<String> classList;
    private Map<String, Long> classIdMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_management);

        // Initialize inputs and UI elements
        gradeLevelInput = findViewById(R.id.grade_level_input);
        enrolledProgramInput = findViewById(R.id.enrolled_program_input);
        theStudentNameInput = findViewById(R.id.the_student_name_input);
        theStudentEmailInput = findViewById(R.id.the_student_email_input);
        theStudentPasswordInput = findViewById(R.id.the_student_password_input);
        theStudentAdmitDateInput = findViewById(R.id.the_student_admit_date_input);
        classSpinner = findViewById(R.id.class_spinner);
        addStudentButton = findViewById(R.id.add_student_button);
        updateStudentButton = findViewById(R.id.update_student_button);
        deleteStudentButton = findViewById(R.id.delete_student_button);
        studentListView = findViewById(R.id.student_list_view);

        databaseConnection = new DatabaseConnection(this);

        // Load class and student data
        loadClassList();
        loadStudentList();

        // Set button listeners
        addStudentButton.setOnClickListener(v -> addStudent());
        updateStudentButton.setOnClickListener(v -> updateStudent());
        deleteStudentButton.setOnClickListener(v -> deleteStudent());

        // Handle student selection from the list
        studentListView.setOnItemClickListener((parent, view, position, id) -> {
            Student selectedStudent = studentList.get(position);
            loadStudentDetailsForEditing(selectedStudent);
        });
    }

    private void loadClassList() {
        classList = new ArrayList<>();
        classIdMap = new HashMap<>();

        for (Class c : databaseConnection.getAllClasses()) {
            classList.add(c.getClassName());
            classIdMap.put(c.getClassName(), c.getClassId());
        }

        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classList);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(classAdapter);
    }

    private void loadStudentList() {
        studentList = databaseConnection.getAllStudents();
        ArrayAdapter<Student> studentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        studentListView.setAdapter(studentAdapter);
    }

    private void addStudent() {
        // Retrieve input values
        String theStudentName = theStudentNameInput.getText().toString().trim();
        String theStudentEmail = theStudentEmailInput.getText().toString().trim();
        String theStudentPassword = theStudentPasswordInput.getText().toString().trim();
        String theStudentAdmitDate = theStudentAdmitDateInput.getText().toString().trim();
        String gradeLevel = gradeLevelInput.getText().toString().trim();
        String enrolledProgram = enrolledProgramInput.getText().toString().trim();
        String selectedClassName = (String) classSpinner.getSelectedItem();
        long classId = classIdMap.get(selectedClassName);

        // Add student to the database
        long newStudentId = databaseConnection.addStudent(
                theStudentName, theStudentEmail, theStudentPassword,
                theStudentAdmitDate, gradeLevel, enrolledProgram,
                classId, 1 // Replace '1' with actual school ID
        );

        if (newStudentId != -1) {
            Toast.makeText(this, "Student added successfully!", Toast.LENGTH_SHORT).show();
            loadStudentList();
            clearInputs();
        } else {
            Toast.makeText(this, "Failed to add student", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateStudent() {
        if (selectedStudentId != -1) {
            // Retrieve input values
            String theStudentName = theStudentNameInput.getText().toString().trim();
            String theStudentEmail = theStudentEmailInput.getText().toString().trim();
            String theStudentPassword = theStudentPasswordInput.getText().toString().trim();
            String theStudentAdmitDate = theStudentAdmitDateInput.getText().toString().trim();
            String gradeLevel = gradeLevelInput.getText().toString().trim();
            String enrolledProgram = enrolledProgramInput.getText().toString().trim();
            String selectedClassName = (String) classSpinner.getSelectedItem();
            long classId = classIdMap.get(selectedClassName);

            // Update student in the database
            int rowsUpdated = databaseConnection.updateStudent(
                    selectedStudentId, theStudentName, theStudentEmail,
                    theStudentPassword, theStudentAdmitDate, gradeLevel,
                    enrolledProgram, classId
            );

            if (rowsUpdated > 0) {
                Toast.makeText(this, "Student updated successfully!", Toast.LENGTH_SHORT).show();
                loadStudentList();
                clearInputs();
            } else {
                Toast.makeText(this, "Failed to update student", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteStudent() {
        if (selectedStudentId != -1) {
            int rowsDeleted = databaseConnection.deleteStudent(selectedStudentId);
            if (rowsDeleted > 0) {
                Toast.makeText(this, "Student deleted successfully!", Toast.LENGTH_SHORT).show();
                loadStudentList();
                clearInputs();
            } else {
                Toast.makeText(this, "Failed to delete student", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadStudentDetailsForEditing(Student student) {

        selectedStudentId = student.getStudentId();
        theStudentNameInput.setText(student.getName());
        theStudentEmailInput.setText(student.getEmail());
        theStudentPasswordInput.setText(""); // Do not pre-fill passwords for security
        theStudentAdmitDateInput.setText(student.getAdmitDate());
        gradeLevelInput.setText(student.getGradeLevel());
        enrolledProgramInput.setText(student.getEnrolledProgram());

        // Set Spinner to the student's class
        String className = databaseConnection.getClassNameById(student.getClassId());
        int spinnerPosition = classList.indexOf(className);
        if (spinnerPosition >= 0) {
            classSpinner.setSelection(spinnerPosition);
        }
    }

    private void clearInputs() {
        theStudentNameInput.setText("");
        theStudentEmailInput.setText("");
        theStudentPasswordInput.setText("");
        theStudentAdmitDateInput.setText("");
        gradeLevelInput.setText("");
        enrolledProgramInput.setText("");
        if (classSpinner.getAdapter().getCount() > 0) {
            classSpinner.setSelection(0);
        }
        selectedStudentId = -1;
    }
}

