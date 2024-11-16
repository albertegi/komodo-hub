package com.creekscholar.komodohub;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeacherManagementActivity extends AppCompatActivity {

    private ListView teacherListView;
    private EditText teacherNameInput, teacherEmailInput,teacherPasswordInput, teacherDepartmentInput, teacherQualificationInput, teacherEmploymentDateInput;
    private Button addButton, updateButton, deleteButton;
    private DatabaseConnection databaseConnection;
    private List<Teacher> teacherList;
    private ArrayAdapter<Teacher> adapter;
    private long selectedTeacherId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_management);

        // Initialize UI components
        teacherListView = findViewById(R.id.teacher_list_view);
        teacherNameInput = findViewById(R.id.teacher_name_input);
        teacherEmailInput = findViewById(R.id.teacher_email_input);
        teacherPasswordInput = findViewById(R.id.teacher_password_input);
        teacherDepartmentInput = findViewById(R.id.teacher_department_input);
        teacherQualificationInput = findViewById(R.id.teacher_qualification_input);
        teacherEmploymentDateInput = findViewById(R.id.teacher_employment_date_input);

        addButton = findViewById(R.id.add_teacher_button);
        updateButton = findViewById(R.id.update_teacher_button);
        deleteButton = findViewById(R.id.delete_teacher_button);

        databaseConnection = new DatabaseConnection(this);

        // Load initial data into ListView
        loadTeacherList();

        // Handle ListView item selection for editing
        teacherListView.setOnItemClickListener((parent, view, position, id) -> {
            Teacher selectedTeacher = teacherList.get(position);
            loadTeacherDetailsForEditing(selectedTeacher);
        });

        // Add new Teacher
        addButton.setOnClickListener(v -> addTeacher());

        // Update selected Teacher
        updateButton.setOnClickListener(v -> updateTeacher());

        // Delete selected Teacher
        deleteButton.setOnClickListener(v -> deleteTeacher());
    }

    private void loadTeacherList() {
        teacherList = databaseConnection.getAllTeachers();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, teacherList);
        teacherListView.setAdapter(adapter);
    }


    private void loadTeacherDetailsForEditing(Teacher teacher) {
        selectedTeacherId = teacher.getUserId();
        teacherNameInput.setText(teacher.getName());
        teacherEmailInput.setText(teacher.getEmail());
        teacherPasswordInput.setText("");
        teacherDepartmentInput.setText(teacher.getDepartment());
        teacherQualificationInput.setText(teacher.getQualification());
        teacherEmploymentDateInput.setText(teacher.getEmploymentDate());
    }


    private void addTeacher() {
        String teacherName = teacherNameInput.getText().toString().trim();
        String teacherEmail = teacherEmailInput.getText().toString().trim();
        String teacherPassword = teacherPasswordInput.getText().toString().trim();
        String teacherDepartment = teacherDepartmentInput.getText().toString().trim();
        String teacherQualification = teacherQualificationInput.getText().toString().trim();
        String teacherEmploymentDate = teacherEmploymentDateInput.getText().toString().trim();

        // Get SchoolId from SharedPreferences
        SharedPreferences sharedPreferences = this.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String schoolIdString = sharedPreferences.getString("SchoolId", "");  // Retrieve SchoolId as String
        int schoolId = !schoolIdString.isEmpty() ? Integer.parseInt(schoolIdString) : -1;

        if (schoolId != -1) {  // Ensure SchoolId is valid
            long newTeacherId = databaseConnection.addTeacher(
                    teacherName, teacherEmail, teacherPassword,
                    teacherDepartment, teacherQualification,
                    teacherEmploymentDate, schoolId
            );
            if (newTeacherId != -1) {
                Toast.makeText(this, "Teacher added successfully!", Toast.LENGTH_SHORT).show();
                loadTeacherList();
                clearInputs();
            } else {
                Toast.makeText(this, "Failed to add Teacher", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Invalid School ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTeacher() {
        if (selectedTeacherId != -1) {
            String teacherName = teacherNameInput.getText().toString().trim();
            String teacherEmail = teacherEmailInput.getText().toString().trim();
            String teacherPassword = teacherPasswordInput.getText().toString().trim();
            String teacherDepartment = teacherDepartmentInput.getText().toString().trim();
            String teacherQualification = teacherQualificationInput.getText().toString().trim();
            String teacherDateOfEmployment = teacherEmploymentDateInput.getText().toString().trim();

            int updated = databaseConnection.updateTeacher(selectedTeacherId, teacherName, teacherEmail,teacherPassword, teacherDepartment, teacherQualification, teacherDateOfEmployment);
            if (updated > 0) {
                Toast.makeText(this, "Teacher updated successfully!", Toast.LENGTH_SHORT).show();
                loadTeacherList();
                clearInputs();
            } else {
                Toast.makeText(this, "Failed to update Teacher", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void deleteTeacher() {
        if (selectedTeacherId != -1) {
            int deleted = databaseConnection.deleteTeacher(selectedTeacherId);
            if (deleted > 0) {
                Toast.makeText(this, "Teacher deleted successfully!", Toast.LENGTH_SHORT).show();
                loadTeacherList();
                clearInputs();
            } else {
                Toast.makeText(this, "Failed to delete Teacher", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clearInputs() {
        selectedTeacherId = -1;
        teacherNameInput.setText("");
        teacherEmailInput.setText("");
        teacherDepartmentInput.setText("");
        teacherQualificationInput.setText("");
    }
}



