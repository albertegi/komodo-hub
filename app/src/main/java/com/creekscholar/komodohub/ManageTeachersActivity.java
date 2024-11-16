package com.creekscholar.komodohub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ManageTeachersActivity extends AppCompatActivity {
//    private DatabaseConnection databaseConnection;
//    private ListView teacherListView;
//    private ArrayAdapter<User> teacherAdapter;
//    private List<User> teacherList;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);  // Optional, if you want to enable edge-to-edge layout
//        setContentView(R.layout.activity_manage_teachers);

//        // Initialize views
//        teacherListView = findViewById(R.id.teacher_list_view);
//        databaseConnection = new DatabaseConnection(this);
//
//        // Fetch all teachers from the database
//        teacherList = databaseConnection.getAllTeachers();
//
//        // Create an ArrayAdapter to display teacher names in the ListView
//        teacherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, teacherList);
//        teacherListView.setAdapter(teacherAdapter);
//
//        // Set up item click listener to open TeacherDetailActivity
//        teacherListView.setOnItemClickListener((parent, view, position, id) -> {
//            User selectedTeacher = teacherList.get(position);  // Get selected teacher
//            Intent intent = new Intent(ManageTeachersActivity.this, TeacherDetailActivity.class);
//            intent.putExtra("TeacherID", selectedTeacher.getUserId());  // Pass teacher ID
//            startActivity(intent);  // Start TeacherDetailActivity
//        });
//
//        // Optionally, you can implement a floating action button to add a new teacher:
//        FloatingActionButton fabAddTeacher = findViewById(R.id.fabAddTeacher);
//        fabAddTeacher.setOnClickListener(v -> openAddTeacherActivity());
//    }

    // Method to handle opening the AddTeacherActivity
//    private void openAddTeacherActivity() {
//        Intent intent = new Intent(this, AddTeacherActivity.class);
//        startActivity(intent);
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        // Refresh the list of teachers whenever the activity is resumed (e.g., after adding/updating a teacher)
//        teacherList.clear();
//        teacherList.addAll(databaseConnection.getAllTeachers());
//        teacherAdapter.notifyDataSetChanged();  // Notify the adapter to update the list
//    }
}