package com.creekscholar.komodohub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ManageStudentsActivity extends AppCompatActivity {
//    private ListView studentListView;
//    private DatabaseConnection databaseConnection;
//    private List<User> studentList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_manage_students);
//
//        studentListView = findViewById(R.id.student_list_view);
//        databaseConnection = new DatabaseConnection(this);
//
//        // Fetch and display the list of students
//        studentList = databaseConnection.getAllStudents(); // Assumes a getAllStudents() method exists
//        StudentAdapter adapter = new StudentAdapter(this, studentList);
//        studentListView.setAdapter(adapter);
//
//        // Set item click listener to open the student details screen
//        studentListView.setOnItemClickListener((parent, view, position, id) -> {
//            User selectedStudent = studentList.get(position);
//            Intent intent = new Intent(this, EditStudentActivity.class);
//            intent.putExtra("studentId", selectedStudent.getUserId());
//            startActivity(intent);
//        });
//    }
}