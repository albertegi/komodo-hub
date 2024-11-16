package com.creekscholar.komodohub;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView welcomeTextView;
    private LinearLayout adminLayout;
    private LinearLayout schoolAdmLayout;
    private LinearLayout teacherLayout;
    private LinearLayout studentLayout;
    private LinearLayout visitorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        welcomeTextView = findViewById(R.id.welcomeText);
        adminLayout = findViewById(R.id.adminLayout);
        schoolAdmLayout = findViewById(R.id.schoolAdminLayout);
        teacherLayout = findViewById(R.id.teacherLayout);
        studentLayout = findViewById(R.id.studentLayout);
        visitorLayout = findViewById(R.id.visitorLayout);

        // Get the user's role from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFERENCES, Context.MODE_PRIVATE);
        String userRole = sharedPreferences.getString("UserRole", "");
        String email = sharedPreferences.getString("UserEmail","");

        // Set a welcome message based on the role
        welcomeTextView.setText("Welcome to Komodo Hub! " + email + " You are logged in as: " + userRole);


        // Hide all role-based layouts by default
        adminLayout.setVisibility(View.GONE);
        schoolAdmLayout.setVisibility(View.GONE);
        teacherLayout.setVisibility(View.GONE);
        studentLayout.setVisibility(View.GONE);
        visitorLayout.setVisibility(View.GONE);

        // Show the appropriate layout based on the user's role
        switch (userRole) {
            case "SystemAdmin":
                adminLayout.setVisibility(View.VISIBLE);
                break;
            case "SchoolAdmin":
                schoolAdmLayout.setVisibility(View.VISIBLE);
                break;
            case "Teacher":
                teacherLayout.setVisibility(View.VISIBLE);
                break;
            case "Student":
                studentLayout.setVisibility(View.VISIBLE);
                break;
            default:
                visitorLayout.setVisibility(View.VISIBLE);  // Show visitor layout if no valid role is found
        }


    }

    // Handle button clicks for different roles
    public void onAdminButtonClick(View view) {
        // Navigate to Admin related activities (e.g., School management)
        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
        startActivity(intent);
    }


    public void onSendEmailsButtonClick(View view){
        // Go to Admin related activities (Send Emails)
        Intent intent = new Intent(MainActivity.this, SendEmailsActivity.class);
        startActivity(intent);
    }

    public void exitButtonClick(View view){
        // Exit
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

//    public void onTeacherButtonClick(View view) {
//        // Navigate to Teacher related activities (e.g., Class management)
//        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(intent);
//    }


//
    public void onStudentButtonClick(View view) {
        // Navigate to Student related activities (e.g., Content, Sighting Reports)
        Intent intent = new Intent(MainActivity.this, StudentActivity.class);
        startActivity(intent);
    }
//
    public void onVisitorButtonClick(View view) {
        // For visitors, allow them to explore basic public information
        Intent intent = new Intent(MainActivity.this, VisitorActivity.class);
        startActivity(intent);
    }


    public void onAddTeacherButtonClick(View view){
        // for Adding of teachers
        Intent intent = new Intent(MainActivity.this, AddTeacherActivity.class);
        //intent.putExtra("SchoolAdminID",schoolAdminId);
        startActivity(intent);
    }

    public void onAddStudentButtonClick(View view){
        // for Adding of students
        Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
        //intent.putExtra("SchoolAdminID",schoolAdminId);
        startActivity(intent);
    }

    public void onAllocateTeacherButtonClick(View view){
        // for Adding of classes
        Intent intent = new Intent(MainActivity.this, AddClassActivity.class);
        //intent.putExtra("SchoolAdminID",schoolAdminId);
        startActivity(intent);
    }

    public void onStudentSightingReportButtonClick(View view){
        // for Adding of Sighting Reports
        Intent intent = new Intent(MainActivity.this, SightingReportsActivity.class);
        //intent.putExtra("SchoolAdminID",schoolAdminId);
        startActivity(intent);
    }

    public void onTeacherAddContentButtonClick(View view){
        // for Adding of Learning Contents
        Intent intent = new Intent(MainActivity.this, AddContentActivity.class);
        startActivity(intent);

    }

    public void onTeacherAddProgressReportButtonClick(View view){
        // for Adding of Learning Contents
        Intent intent = new Intent(MainActivity.this, AddProgressReportActivity.class);
        startActivity(intent);
    }



    public void onVisitorContentButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, VisitorDashboardActivity.class);
        startActivity(intent);
    }

    public void manageTeacherButtonClick(View view){
        Intent intent = new Intent(MainActivity.this, ManageTeachersActivity.class);
        startActivity(intent);
    }

    public void onManageStudentButtonClick(View view){
        Intent intent = new Intent(MainActivity.this, ManageStudentsActivity.class);
        startActivity(intent);
    }

    public void onManageSchClick(View view){
        Intent intent = new Intent(MainActivity.this, ManageSchoolAdminActivity.class);
        startActivity(intent);
    }

    public void entitiesManagementButtonClick(View view){
        Intent intent = new Intent(MainActivity.this, ManageEntitiesActivity.class);
        startActivity(intent);
    }

    public void onTeacherManagementButtonClick(View view){
        Intent intent = new Intent(MainActivity.this, TeacherManagementActivity.class);
        startActivity(intent);
    }

    public void onClassManagementButtonClick(View view){
        Intent intent = new Intent(MainActivity.this, ClassManagementActivity.class);
        startActivity(intent);
    }

    public void onStudentManagementButtonClick(View view){
        Intent intent = new Intent(MainActivity.this, StudentManagementActivity.class);
        startActivity(intent);
    }

    public void onTeacherManageContentButtonClick(View view){
        Intent intent = new Intent(MainActivity.this, TeacherDashboardActivity.class);
        startActivity(intent);
    }


}