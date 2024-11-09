package com.creekscholar.komodohub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
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
import java.util.ArrayList;

import androidx.annotation.Nullable;

public class DatabaseConnection extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "komodo_hub.db";
    private static final int DATABASE_VERSION = 3;


    // Table and column names for Users table
    public static final String TABLE_USERS = "Users";
    public static final String COLUMN_USER_ID = "UserID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_ROLE = "Role";
    public static final String COLUMN_PROFILE_PICTURE = "ProfilePicture";
    public static final String COLUMN_USER_SCHOOL_ID = "UserSchoolID";  // Renamed to avoid conflict

    // Table and column names for Schools table
    public static final String TABLE_SCHOOLS = "Schools";
    public static final String COLUMN_SCHOOL_ID = "SchoolID";
    public static final String COLUMN_SCHOOL_NAME = "SchoolName";
    public static final String COLUMN_ADDRESS = "Address";
    public static final String COLUMN_SUBSCRIPTION_STATUS = "SubscriptionStatus";
    public static final String COLUMN_PAYMENT_DETAILS = "PaymentDetails";
    public static final String COLUMN_SCHOOL_ADMIN_ID = "SchoolAdminID";


    // Table and column names for Teachers table
    public static final String TABLE_TEACHERS = "Teachers";
    public static final String COLUMN_TEACHER_ID = "TeacherID";
    public static final String COLUMN_TEACHER_USER_ID = "TeacherUserID"; // Foreign key referencing Users
    public static final String COLUMN_SPECIALIZATION = "Specialization";
    public static final String COLUMN_HIRE_DATE = "HireDate";


    // Table and column names for Students table
    public static final String TABLE_STUDENTS = "Students";
    public static final String COLUMN_STUDENT_ID = "StudentID";
    public static final String COLUMN_STUDENT_USER_ID = "StudentUserID"; // Foreign key referencing Users
    public static final String COLUMN_GRADE_LEVEL = "GradeLevel";
    public static final String COLUMN_ENROLLED_PROGRAM = "EnrolledProgram";
    public static final String COLUMN_CLASS_ID = "ClassID";

    // Table and column names for Classes table
    public static final String TABLE_CLASSES = "Classes";
    public static final String COLUMN_CLASSES_CLASS_ID = "ClassID";         // Primary key for Classes table
    public static final String COLUMN_CLASSES_CLASS_NAME = "ClassName";
    public static final String COLUMN_CLASSES_SUBJECT = "Subject";
    public static final String COLUMN_CLASSES_TEACHER_ID = "TeacherID"; // Foreign key referencing Users (Teacher)
    public static final String COLUMN_CLASSES_SCHOOL_ID = "ClassSchoolID";   // Foreign key referencing Schools// Foreign key referencing Schools

    // Constants for SightingReports table
    public static final String TABLE_SIGHTING_REPORTS = "SightingReports";
    public static final String COLUMN_SIGHTING_ID = "SightingID";
    public static final String COLUMN_SPECIES_NAME = "SpeciesName";
    public static final String COLUMN_DESCRIPTION = "Description";
    public static final String COLUMN_PHOTO = "Photo";
    public static final String COLUMN_LOCATION = "Location";
    public static final String COLUMN_REPORTED_DATE = "ReportedDate";
    public static final String COLUMN_STATUS = "Status";
    public static final String COLUMN_SIGHTING_STUDENT_ID = "StudentID";

    // Table: Content
    public static final String TABLE_CONTENT = "Content";
    public static final String COLUMN_CONTENT_ID = "ContentID";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_CONTENT_DESCRIPTION = "Description";
    public static final String COLUMN_TYPE = "Type";
    public static final String COLUMN_FILE_PATH = "FilePath";
    public static final String COLUMN_PUBLISH_DATE = "PublishDate";
    public static final String COLUMN_CONTENT_CLASS_ID = "ClassID";
    public static final String COLUMN_CONTENT_TEACHER_ID = "TeacherID";

    // SQL statement to create the Content table
    private static final String CREATE_TABLE_CONTENT = "CREATE TABLE " + TABLE_CONTENT + " ("
            + COLUMN_CONTENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TITLE + " TEXT NOT NULL, "
            + COLUMN_DESCRIPTION + " TEXT, "
            + COLUMN_TYPE + " TEXT CHECK (" + COLUMN_TYPE + " IN ('Video', 'Article', 'Document')) NOT NULL, "
            + COLUMN_FILE_PATH + " TEXT, "
            + COLUMN_PUBLISH_DATE + " TEXT NOT NULL, "
            + COLUMN_CONTENT_CLASS_ID + " INTEGER, "
            + COLUMN_CONTENT_TEACHER_ID + " INTEGER, "
            + "FOREIGN KEY (" + COLUMN_CLASS_ID + ") REFERENCES Classes(ClassID) ON DELETE CASCADE, "
            + "FOREIGN KEY (" + COLUMN_TEACHER_ID + ") REFERENCES Users(UserID) ON DELETE CASCADE"
            + ");";



    // SQL statement for creating Users table with school ID reference
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, "
            + COLUMN_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_ROLE + " TEXT NOT NULL CHECK (Role IN ('SystemAdmin', 'SchoolAdmin', 'Teacher', 'Student')), "
            + COLUMN_PROFILE_PICTURE + " TEXT, "
            + COLUMN_USER_SCHOOL_ID + " INTEGER, "  // Updated reference name
            + "FOREIGN KEY(" + COLUMN_USER_SCHOOL_ID + ") REFERENCES " + TABLE_SCHOOLS + "(" + COLUMN_SCHOOL_ID + ") ON DELETE CASCADE"
            + ");";



    // Insert a default SystemAdmin user
    private static final String INSERT_SYSTEM_ADMIN = "INSERT INTO " + TABLE_USERS + " ("
            + COLUMN_NAME + ", "
            + COLUMN_EMAIL + ", "
            + COLUMN_PASSWORD + ", "
            + COLUMN_ROLE + ", "
            + COLUMN_PROFILE_PICTURE + ") VALUES ("
            + "'Albert Egi', "               // Name
            + "'albertegi90@gmail.com', "      // Email
            + "'password123', "              // Password
            + "'SystemAdmin', "              // Role
            + "NULL);";                      // Profile Picture



    // SQL statement for creating Schools table
    private static final String CREATE_TABLE_SCHOOLS = "CREATE TABLE " + TABLE_SCHOOLS + "("
            + COLUMN_SCHOOL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_SCHOOL_NAME + " TEXT NOT NULL UNIQUE, "
            + COLUMN_ADDRESS + " TEXT NOT NULL, "
            + COLUMN_SUBSCRIPTION_STATUS + " TEXT NOT NULL CHECK (SubscriptionStatus IN ('Active', 'Inactive')), "
            + COLUMN_PAYMENT_DETAILS + " TEXT, "
            + COLUMN_SCHOOL_ADMIN_ID + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_SCHOOL_ADMIN_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ") ON DELETE CASCADE"
            + ");";


    // SQL statement for creating teachers table
    private static final String CREATE_TABLE_TEACHERS = "CREATE TABLE " + TABLE_TEACHERS + "("
            + COLUMN_TEACHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TEACHER_USER_ID + " INTEGER NOT NULL, " // Foreign key column for Users table
            + COLUMN_SPECIALIZATION + " TEXT, "
            + COLUMN_HIRE_DATE + " TEXT, "
            + "FOREIGN KEY(" + COLUMN_TEACHER_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ") ON DELETE CASCADE"
            + ");";

    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE " + TABLE_STUDENTS + " ("
            + COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_STUDENT_USER_ID + " INTEGER NOT NULL, "
            + COLUMN_GRADE_LEVEL + " TEXT, "
            + COLUMN_ENROLLED_PROGRAM + " TEXT, "
            + COLUMN_CLASS_ID + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_STUDENT_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ") ON DELETE CASCADE, "
            + "FOREIGN KEY(" + COLUMN_CLASS_ID + ") REFERENCES " + TABLE_CLASSES + "(" + COLUMN_CLASS_ID + ") ON DELETE SET NULL"
            + ");";

    private static final String CREATE_TABLE_CLASSES = "CREATE TABLE " + TABLE_CLASSES + " ("
            + COLUMN_CLASSES_CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CLASSES_CLASS_NAME + " TEXT NOT NULL, "
            + COLUMN_CLASSES_SUBJECT + " TEXT NOT NULL, "
            + COLUMN_CLASSES_TEACHER_ID + " INTEGER, "
            + COLUMN_CLASSES_SCHOOL_ID + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_CLASSES_TEACHER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ") ON DELETE CASCADE, "
            + "FOREIGN KEY(" + COLUMN_CLASSES_SCHOOL_ID + ") REFERENCES " + TABLE_SCHOOLS + "(" + COLUMN_SCHOOL_ID + ") ON DELETE CASCADE"
            + ");";



    private static final String CREATE_TABLE_SIGHTING_REPORTS = "CREATE TABLE " + TABLE_SIGHTING_REPORTS + " ("
            + COLUMN_SIGHTING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_SPECIES_NAME + " TEXT NOT NULL, "
            + COLUMN_DESCRIPTION + " TEXT, "
            + COLUMN_PHOTO + " TEXT, "
            + COLUMN_LOCATION + " TEXT, "
            + COLUMN_REPORTED_DATE + " TEXT NOT NULL, "
            + COLUMN_STATUS + " TEXT CHECK (" + COLUMN_STATUS + " IN ('Pending', 'Approved', 'Rejected')) DEFAULT 'Pending', "
            + COLUMN_SIGHTING_STUDENT_ID + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_SIGHTING_STUDENT_ID + ") REFERENCES " + TABLE_STUDENTS + "(" + COLUMN_STUDENT_ID + ") ON DELETE CASCADE"
            + ");";

    // Table name for ProgressReports
    public static final String TABLE_PROGRESS_REPORTS = "ProgressReports";

    // Column names for ProgressReports table
    public static final String COLUMN_REPORT_ID = "ReportID";
    public static final String COLUMN_GRADE = "Grade";
    public static final String COLUMN_FEEDBACK = "Feedback";
    public static final String COLUMN_SUBMISSION_DATE = "SubmissionDate";
    public static final String COLUMN_PROGRESS_REPORT_STUDENT_ID = "StudentID";
    public static final String COLUMN_PROGRESS_REPORT_TEACHER_ID = "TeacherID";
    public static final String COLUMN_PROGRESS_REPORT_CLASS_ID = "ClassID";

    private static final String CREATE_TABLE_PROGRESS_REPORTS = "CREATE TABLE " + TABLE_PROGRESS_REPORTS + " ("
            + COLUMN_REPORT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_GRADE + " TEXT, "
            + COLUMN_FEEDBACK + " TEXT, "
            + COLUMN_SUBMISSION_DATE + " TEXT NOT NULL, "
            + COLUMN_STUDENT_ID + " INTEGER, "
            + COLUMN_TEACHER_ID + " INTEGER, "
            + COLUMN_CLASS_ID + " INTEGER, "
            + "FOREIGN KEY (" + COLUMN_PROGRESS_REPORT_STUDENT_ID + ") REFERENCES Students(StudentID) ON DELETE CASCADE, "
            + "FOREIGN KEY (" + COLUMN_PROGRESS_REPORT_TEACHER_ID + ") REFERENCES Users(UserID) ON DELETE CASCADE, "
            + "FOREIGN KEY (" + COLUMN_PROGRESS_REPORT_CLASS_ID + ") REFERENCES Classes(ClassID) ON DELETE CASCADE"
            + ");";







    // SQL statement to create Classes table
//    private static final String CREATE_TABLE_CLASSES = "CREATE TABLE " + TABLE_CLASSES + " ("
//            + COLUMN_CLASSES_CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + COLUMN_CLASSES_CLASS_NAME + " TEXT NOT NULL, "
//            + COLUMN_CLASSES_SUBJECT + " TEXT NOT NULL, "
//            + COLUMN_CLASSES_TEACHER_ID + " INTEGER, "
//            + COLUMN_CLASSES_SCHOOL_ID + " INTEGER, "
//            + "FOREIGN KEY(" + COLUMN_CLASSES_TEACHER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ") ON DELETE CASCADE, "
//            + "FOREIGN KEY(" + COLUMN_CLASSES_SCHOOL_ID + ") REFERENCES " + TABLE_SCHOOLS + "(" + COLUMN_SCHOOL_ID + ") ON DELETE CASCADE"
//            + ");";




    public DatabaseConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_SCHOOLS);
        db.execSQL(CREATE_TABLE_TEACHERS);
        db.execSQL(CREATE_TABLE_CLASSES);
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_SIGHTING_REPORTS);   // New SightingReports table
        db.execSQL(CREATE_TABLE_CONTENT);  // Create the Content table
        db.execSQL(CREATE_TABLE_PROGRESS_REPORTS);
        Log.d("DatabaseConnection", "Users table created");
        db.execSQL(INSERT_SYSTEM_ADMIN);
        Log.d("DatabaseConnection", "SystemAdmin seeded into Users table");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHOOLS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHERS); // Include the new table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SIGHTING_REPORTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRESS_REPORTS);


        // Recreate tables
//        onCreate(db);
//        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);
    }

    // method for user login
    public boolean checkUserCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE Email = ? AND Password = ?", new String[]{email, password});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // method for adding a user
    public long addUser(String name, String email, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Email", email);
        values.put("Password", password);
        values.put("Role", role);
        return db.insert("Users", null, values);
    }

    // Method to add a new SchoolAdmin user
    public long addSchoolAdmin(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ROLE, "SchoolAdmin");
        values.put(COLUMN_PROFILE_PICTURE, (String) null);

        return db.insert(TABLE_USERS, null, values);
    }

    // Method to retrieve a SchoolAdmin user ID by email
    public long getSchoolAdminIdByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_ID};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        long userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
        }
        cursor.close();
        return userId;
    }

    // Method to add a new school linked to a SchoolAdmin user
    public long addSchool(String schoolName, String address, String subscriptionStatus, String paymentDetails, long schoolAdminId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCHOOL_NAME, schoolName);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_SUBSCRIPTION_STATUS, subscriptionStatus);
        values.put(COLUMN_PAYMENT_DETAILS, paymentDetails);
        values.put(COLUMN_SCHOOL_ADMIN_ID, schoolAdminId);

        return db.insert(TABLE_SCHOOLS, null, values);
    }

    // Add Teacher
    public long addTeacher(String name, String email, String password, String specialization, String hireDate, long schoolId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long teacherUserId = -1;

        // Step 1: Insert basic user information in Users table
        ContentValues userValues = new ContentValues();
        userValues.put(COLUMN_NAME, name);
        userValues.put(COLUMN_EMAIL, email);
        userValues.put(COLUMN_PASSWORD, password); // Secure password
        userValues.put(COLUMN_ROLE, "Teacher");
        userValues.put(COLUMN_USER_SCHOOL_ID, schoolId);

        // Insert user into Users table and get generated UserID
        try {
            teacherUserId = db.insert(TABLE_USERS, null, userValues);
            if (teacherUserId == -1) {
                return -1; // Failed to insert user
            }

            // Step 2: Insert teacher-specific details in Teachers table
            ContentValues teacherValues = new ContentValues();
            teacherValues.put(COLUMN_TEACHER_USER_ID, teacherUserId);
            teacherValues.put(COLUMN_SPECIALIZATION, specialization);
            teacherValues.put(COLUMN_HIRE_DATE, hireDate);

            long teacherResult = db.insert(TABLE_TEACHERS, null, teacherValues);
            if (teacherResult == -1) {
                db.delete(TABLE_USERS, COLUMN_USER_ID + "=?", new String[]{String.valueOf(teacherUserId)});
                return -1; // Failed to insert teacher-specific info, rollback
            }
            return teacherUserId; // Successfully inserted teacher
        } finally {
            db.close();
        }
    }

    // Add Student
    public long addStudent(String name, String email, String password, String gradeLevel, String enrolledProgram, long classId, long schoolId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long studentUserId = -1;

        // Step 1: Insert basic user information in Users table
        ContentValues userValues = new ContentValues();
        userValues.put(COLUMN_NAME, name);
        userValues.put(COLUMN_EMAIL, email);
        userValues.put(COLUMN_PASSWORD, password); // Secure password
        userValues.put(COLUMN_ROLE, "Student");
        userValues.put(COLUMN_USER_SCHOOL_ID, schoolId);

        // Insert user into Users table and get generated UserID
        try {
            studentUserId = db.insert(TABLE_USERS, null, userValues);
            if (studentUserId == -1) {
                return -1; // Failed to insert user
            }

            // Step 2: Insert student-specific details in Students table
            ContentValues studentValues = new ContentValues();
            studentValues.put(COLUMN_STUDENT_USER_ID, studentUserId);
            studentValues.put(COLUMN_GRADE_LEVEL, gradeLevel);
            studentValues.put(COLUMN_ENROLLED_PROGRAM, enrolledProgram);
            studentValues.put(COLUMN_CLASS_ID, classId);

            long studentResult = db.insert(TABLE_STUDENTS, null, studentValues);
            if (studentResult == -1) {
                // Rollback: delete user entry if student-specific info fails
                db.delete(TABLE_USERS, COLUMN_USER_ID + "=?", new String[]{String.valueOf(studentUserId)});
                return -1;
            }

            return studentUserId; // Successfully inserted student
        } finally {
            db.close();
        }
    }




    // Method to retrieve schoolId based on the SchoolAdminID
    public long getSchoolIdByAdminId(long schoolAdminId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SCHOOLS, new String[]{COLUMN_SCHOOL_ID},
                COLUMN_SCHOOL_ADMIN_ID + " = ?", new String[]{String.valueOf(schoolAdminId)},
                null, null, null);

        long schoolId = -1;
        if (cursor.moveToFirst()) {
            schoolId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_SCHOOL_ID));
        }
        cursor.close();
        return schoolId;
    }




    // DatabaseConnection.java

    // Gets the list of teacher names

    public List<String> getTeacherNames() {
        List<String> teacherNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_NAME},
                COLUMN_ROLE + " = ?", new String[]{"Teacher"},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                teacherNames.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return teacherNames;
    }

    // Gets the list of teacher IDs
    public List<Long> getTeacherIds() {
        List<Long> teacherIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_USER_ID},
                COLUMN_ROLE + " = ?", new String[]{"Teacher"},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                teacherIds.add(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return teacherIds;
    }

    // Gets the list of school names
    public List<String> getSchoolNames() {
        List<String> schoolNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SCHOOLS,
                new String[]{COLUMN_SCHOOL_NAME},  // Assuming COLUMN_NAME holds school names
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                schoolNames.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SCHOOL_NAME)));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return schoolNames;
    }


    // Gets the list of school IDs
    public List<Long> getSchoolIds() {
        List<Long> schoolIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SCHOOLS,
                new String[]{COLUMN_SCHOOL_ID},
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                schoolIds.add(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_SCHOOL_ID)));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return schoolIds;
    }

    // Adds a class with given details
    public long addClass(String className, String subject, long teacherId, long schoolId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLASSES_CLASS_NAME, className);
        values.put(COLUMN_CLASSES_SUBJECT, subject);
        values.put(COLUMN_TEACHER_ID, teacherId);
        values.put(COLUMN_CLASSES_SCHOOL_ID, schoolId);

        return db.insert(TABLE_CLASSES, null, values);
    }

    public long addSightingReport(String speciesName, String description, String photo, String location, String reportedDate, String status, long studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SPECIES_NAME, speciesName);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_PHOTO, photo);
        values.put(COLUMN_LOCATION, location);
        values.put(COLUMN_REPORTED_DATE, reportedDate);
        values.put(COLUMN_STATUS, status);
        values.put(COLUMN_SIGHTING_STUDENT_ID, studentId);

        long result = db.insert(TABLE_SIGHTING_REPORTS, null, values);
        db.close();
        return result;
    }

    // Adds new content to the Content table
    public long addContent(String title, String description, String type, String filePath, String publishDate, long classId, long teacherId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_FILE_PATH, filePath);
        values.put(COLUMN_PUBLISH_DATE, publishDate);
        values.put(COLUMN_CONTENT_CLASS_ID, classId);
        values.put(COLUMN_CONTENT_TEACHER_ID, teacherId);

        return db.insert(TABLE_CONTENT, null, values);
    }


    public long addProgressReport(String grade, String feedback, String submissionDate, int studentID, int teacherID, int classID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Grade", grade);
        values.put("Feedback", feedback);
        values.put("SubmissionDate", submissionDate);
        values.put("StudentID", studentID);
        values.put("TeacherID", teacherID);
        values.put("ClassID", classID);

        return db.insert("ProgressReports", null, values); // Insert into ProgressReports table
    }



//    public long addContent(String title, String description, String type, String filePath, String publishDate, int classID, int teacherID) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("Title", title);
//        values.put("Description", description);
//        values.put("Type", type);
//        values.put("FilePath", filePath);
//        values.put("PublishDate", publishDate);
//        values.put("ClassID", classID);
//        values.put("TeacherID", teacherID);
//
//        return db.insert("Content", null, values);
//    }



    // Retrieves content based on the class ID
//    public List<Content> getContentByClassId(long classId) {
//        List<Content> contentList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_CONTENT, null, COLUMN_CLASS_ID + " = ?",
//                new String[]{String.valueOf(classId)}, null, null, COLUMN_PUBLISH_DATE + " DESC");
//
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                Content content = new Content();
//                content.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CONTENT_ID)));
//                content.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
//                content.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
//                content.setType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)));
//                content.setFilePath(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FILE_PATH)));
//                content.setPublishDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PUBLISH_DATE)));
//                content.setClassId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CLASS_ID)));
//                content.setTeacherId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_TEACHER_ID)));
//
//                contentList.add(content);
//            } while (cursor.moveToNext());
//
//            cursor.close();
//        }
//
//        return contentList;
//    }

    // Retrieves content created by a specific teacher
//    public List<Content> getContentByTeacherId(long teacherId) {
//        List<Content> contentList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_CONTENT, null, COLUMN_TEACHER_ID + " = ?",
//                new String[]{String.valueOf(teacherId)}, null, null, COLUMN_PUBLISH_DATE + " DESC");
//
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                Content content = new Content();
//                content.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CONTENT_ID)));
//                content.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
//                content.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
//                content.setType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)));
//                content.setFilePath(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FILE_PATH)));
//                content.setPublishDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PUBLISH_DATE)));
//                content.setClassId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CLASS_ID)));
//                content.setTeacherId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_TEACHER_ID)));
//
//                contentList.add(content);
//            } while (cursor.moveToNext());
//
//            cursor.close();
//        }
//
//        return contentList;
//    }







}
