package com.creekscholar.komodohub;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TeacherDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher_dashboard);

        findViewById(R.id.btnAssessStudents).setOnClickListener(v -> {
            startActivity(new Intent(this, AssessStudentsActivity.class));
        });

        findViewById(R.id.btnGenerateReports).setOnClickListener(v -> {
            startActivity(new Intent(this, GenerateReportsActivity.class));
        });

        findViewById(R.id.btnMessaging).setOnClickListener(v -> {
            startActivity(new Intent(this, MessagingActivity.class));
        });

        findViewById(R.id.btnManageContent).setOnClickListener(v -> {
            startActivity(new Intent(this, ManageStudentContentActivity.class));
        });

//        findViewById(R.id.btnViewStudentContent).setOnClickListener(v -> {
//            startActivity(new Intent(this, ViewContentActivity.class));
//        });
        findViewById(R.id.btnViewStudentContent).setOnClickListener(v ->{
            startActivity(new Intent(this, ViewStudentContentActivity.class));
        });

    }
}