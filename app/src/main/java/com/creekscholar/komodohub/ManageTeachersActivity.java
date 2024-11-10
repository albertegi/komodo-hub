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

import java.util.List;

public class ManageTeachersActivity extends AppCompatActivity {
    private DatabaseConnection databaseConnection;
    private ListView teacherListView;
    private ArrayAdapter<User> teacherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_teachers);

        teacherListView = findViewById(R.id.teacher_list_view);
        databaseConnection = new DatabaseConnection(this);

        List<User> teacherList = databaseConnection.getAllTeachers();
        teacherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, teacherList);
        teacherListView.setAdapter(teacherAdapter);

        // Click event to go to TeacherDetailActivity
        teacherListView.setOnItemClickListener((parent, view, position, id) -> {
            User selectedTeacher = teacherList.get(position);
            Intent intent = new Intent(ManageTeachersActivity.this, TeacherDetailActivity.class);
            intent.putExtra("TeacherID", selectedTeacher.getUserId());
            startActivity(intent);
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list after returning from TeacherDetailActivity
        teacherAdapter.clear();
        teacherAdapter.addAll(databaseConnection.getAllTeachers());
        teacherAdapter.notifyDataSetChanged();
    }
}