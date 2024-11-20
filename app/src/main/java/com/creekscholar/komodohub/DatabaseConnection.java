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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

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

    // SQL statement to create the Users table
    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " (" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, " +
            COLUMN_PASSWORD + " TEXT NOT NULL, " +
            COLUMN_ROLE + " TEXT CHECK (" + COLUMN_ROLE + " IN ('SystemAdmin', 'SchoolAdmin', 'Teacher', 'Student')) NOT NULL, " +
            COLUMN_PROFILE_PICTURE + " TEXT, " +
            COLUMN_USER_SCHOOL_ID + " INTEGER, " +
            "FOREIGN KEY (" + COLUMN_USER_SCHOOL_ID + ") REFERENCES Schools(SchoolID) ON DELETE CASCADE" +
            ");";

    // Table name and Column names for Teacher
    public static final String TABLE_TEACHERS = "Teachers";
    public static final String COLUMN_TEACHER_ID = "TeacherID";
    public static final String COLUMN_TEACHER_USER_ID = "UserID";  // Foreign key to Users table
    public static final String COLUMN_DEPARTMENT = "Department";
    public static final String COLUMN_QUALIFICATION = "Qualification";
    public static final String COLUMN_EMPLOYMENT_DATE = "EmploymentDate";

    // SQL statement to create the Teachers table
    public static final String CREATE_TABLE_TEACHERS = "CREATE TABLE " + TABLE_TEACHERS + " (" +
            COLUMN_TEACHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TEACHER_USER_ID + " INTEGER NOT NULL, " +
            COLUMN_DEPARTMENT + " TEXT, " +
            COLUMN_QUALIFICATION + " TEXT, " +
            COLUMN_EMPLOYMENT_DATE + " TEXT, " +
            "FOREIGN KEY (" + COLUMN_TEACHER_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ") ON DELETE CASCADE" +
            ");";





    // Table and column names for Schools table
    public static final String TABLE_SCHOOLS = "Schools";
    public static final String COLUMN_SCHOOL_ID = "SchoolID";
    public static final String COLUMN_SCHOOL_NAME = "SchoolName";
    public static final String COLUMN_ADDRESS = "Address";
    public static final String COLUMN_SUBSCRIPTION_STATUS = "SubscriptionStatus";
    public static final String COLUMN_PAYMENT_DETAILS = "PaymentDetails";
    public static final String COLUMN_SCHOOL_ADMIN_ID = "SchoolAdminID";


    // Table and column names for Students table
    public static final String TABLE_STUDENTS = "Students";
    public static final String COLUMN_STUDENT_ID = "StudentID";
    public static final String COLUMN_STUDENT_USER_ID = "StudentUserID"; // Foreign key referencing Users
    public static final String COLUMN_STUDENT_ADMIT_DATE = "AdmitDate";
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

    // Table and column names
    public static final String TABLE_ASSESSMENTS = "Assessments";
    public static final String COLUMN_ASSESSMENT_ID = "AssessmentID";
    public static final String COLUMN_ASSESSMENT_STUDENT_ID = "StudentID";
    public static final String COLUMN_ASSESSMENT_DETAILS = "AssessmentDetails";
    public static final String COLUMN_ASSESSMENT_DATE = "AssessmentDate";

    String CREATE_TABLE_ASSESSMENTS = "CREATE TABLE " + TABLE_ASSESSMENTS + " ("
            + COLUMN_ASSESSMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ASSESSMENT_STUDENT_ID + " INTEGER NOT NULL, "
            + COLUMN_ASSESSMENT_DETAILS + " TEXT NOT NULL, "
            + COLUMN_ASSESSMENT_DATE + " TEXT NOT NULL, "
            + "FOREIGN KEY(" + COLUMN_ASSESSMENT_STUDENT_ID + ") REFERENCES Students(StudentID) ON DELETE CASCADE"
            + ");";

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


    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE " + TABLE_STUDENTS + " ("
            + COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_STUDENT_USER_ID + " INTEGER NOT NULL, "
            + COLUMN_STUDENT_ADMIT_DATE + " TEXT, "
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
        db.execSQL(CREATE_TABLE_ASSESSMENTS);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSESSMENTS);


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




    public List<String> getGeneralInformation() {
        List<String> generalInfoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT Title, Description FROM Content WHERE Type = 'Article' OR Type = 'Video'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            // Check column indexes to avoid "value must be >=0" error
            int titleIndex = cursor.getColumnIndex("Title");
            int descriptionIndex = cursor.getColumnIndex("Description");

            if (titleIndex == -1 || descriptionIndex == -1) {
                // Columns do not exist, return empty list or handle error appropriately
                cursor.close();
                throw new IllegalStateException("Title or Description column not found in Content table.");
            }

            do {
                String title = cursor.getString(titleIndex);
                String description = cursor.getString(descriptionIndex);
                generalInfoList.add(title + ": " + description);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return generalInfoList;
    }


    // Retrieve all School Admins and Schools
    public List<SchoolAdmin> getAllSchoolAdmins() {
        List<SchoolAdmin> admins = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to fetch all users with role "SchoolAdmin"
        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{COLUMN_USER_ID, COLUMN_NAME, COLUMN_EMAIL}, // Only select relevant columns
                COLUMN_ROLE + "=?",
                new String[]{"SchoolAdmin"},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Initialize and populate SchoolAdmin object with user details
                SchoolAdmin admin = new SchoolAdmin();
                admin.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)));
                admin.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                admin.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)));

                // Query the Schools table to fetch school details for the SchoolAdmin
                Cursor schoolCursor = db.query(
                        TABLE_SCHOOLS,
                        new String[]{COLUMN_SCHOOL_NAME, COLUMN_ADDRESS, COLUMN_SUBSCRIPTION_STATUS,COLUMN_PAYMENT_DETAILS},
                        COLUMN_SCHOOL_ADMIN_ID + "=?",
                        new String[]{String.valueOf(admin.getUserId())},
                        null, null, null
                );

                if (schoolCursor != null && schoolCursor.moveToFirst()) {
                    // Populate school details for the admin
                    admin.setSchoolName(schoolCursor.getString(schoolCursor.getColumnIndexOrThrow(COLUMN_SCHOOL_NAME)));
                    admin.setSchoolAddress(schoolCursor.getString(schoolCursor.getColumnIndexOrThrow(COLUMN_ADDRESS)));
                    admin.setSubscriptionStatus(schoolCursor.getString(schoolCursor.getColumnIndexOrThrow(COLUMN_SUBSCRIPTION_STATUS)));
                    admin.setPaymentDetails(schoolCursor.getString(schoolCursor.getColumnIndexOrThrow(COLUMN_PAYMENT_DETAILS)));
                }

                if (schoolCursor != null) {
                    schoolCursor.close();
                }

                // Add the populated SchoolAdmin object to the list
                admins.add(admin);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return admins;
    }


    public boolean updateSchoolAdmin(long schoolAdminId, String adminName, String adminEmail, String schoolName, String schoolAddress, String subscriptionStatus, String paymentDetails) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Step 1: Update the SchoolAdmin data in the Users table
        ContentValues adminValues = new ContentValues();
        adminValues.put(COLUMN_NAME, adminName);
        adminValues.put(COLUMN_EMAIL, adminEmail);

        int adminUpdateResult = db.update(TABLE_USERS, adminValues, COLUMN_USER_ID + "=? AND " + COLUMN_ROLE + "=?",
                new String[]{String.valueOf(schoolAdminId), "SchoolAdmin"});

        // Step 2: Update the school data in the Schools table
        ContentValues schoolValues = new ContentValues();
        schoolValues.put(COLUMN_SCHOOL_NAME, schoolName);
        schoolValues.put(COLUMN_ADDRESS, schoolAddress);
        schoolValues.put(COLUMN_SUBSCRIPTION_STATUS, subscriptionStatus);
        schoolValues.put(COLUMN_PAYMENT_DETAILS, paymentDetails);

        int schoolUpdateResult = db.update(TABLE_SCHOOLS, schoolValues, COLUMN_SCHOOL_ADMIN_ID + "=?",
                new String[]{String.valueOf(schoolAdminId)});

        db.close();

        return adminUpdateResult > 0 && schoolUpdateResult > 0;
    }

    public boolean deleteSchoolAdmin(long schoolAdminId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Step 1: Delete the associated school record from the Schools table
        int schoolDeleteResult = db.delete(TABLE_SCHOOLS, COLUMN_SCHOOL_ADMIN_ID + "=?", new String[]{String.valueOf(schoolAdminId)});

        // Step 2: Delete the SchoolAdmin record from the Users table
        int adminDeleteResult = db.delete(TABLE_USERS, COLUMN_USER_ID + "=? AND " + COLUMN_ROLE + "=?",
                new String[]{String.valueOf(schoolAdminId), "SchoolAdmin"});

        db.close();

        return schoolDeleteResult > 0 && adminDeleteResult > 0;
    }



    // Add Class method
    public long addClass(String className, String subject, int teacherId, int schoolId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLASSES_CLASS_NAME, className);
        values.put(COLUMN_CLASSES_SUBJECT, subject);
        values.put(COLUMN_CLASSES_TEACHER_ID, teacherId);
        values.put(COLUMN_CLASSES_SCHOOL_ID, schoolId);

        long classId = db.insert(TABLE_CLASSES, null, values);
        db.close();
        return classId;
    }



    public List<Class> getAllClasses() {
        List<Class> classes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CLASSES, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Class classItem = new Class();
                classItem.setClassId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CLASSES_CLASS_ID)));
                classItem.setClassName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLASSES_CLASS_NAME)));
                classItem.setSubject(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLASSES_SUBJECT)));
                classItem.setTeacherId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CLASSES_TEACHER_ID)));
                classes.add(classItem);
            } while (cursor.moveToNext());
        }

        if (cursor != null) cursor.close();
        db.close();
        return classes;
    }

    public int updateClass(long classId, String className, String subject, int teacherId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLASSES_CLASS_NAME, className);
        values.put(COLUMN_CLASSES_SUBJECT, subject);
        values.put(COLUMN_CLASSES_TEACHER_ID, teacherId);

        int rowsAffected = db.update(TABLE_CLASSES, values, COLUMN_CLASSES_CLASS_ID + " = ?", new String[]{String.valueOf(classId)});
        db.close();
        return rowsAffected;
    }

    public int deleteClass(long classId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_CLASSES, COLUMN_CLASSES_CLASS_ID + " = ?", new String[]{String.valueOf(classId)});
        db.close();
        return rowsAffected;
    }



    // Add Teacher
    public long addTeacher(String name, String email, String password, String department, String qualification, String employmentDate, long schoolId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long teacherUserId = -1;

        ContentValues userValues = new ContentValues();
        userValues.put(COLUMN_NAME, name);
        userValues.put(COLUMN_EMAIL, email);
        userValues.put(COLUMN_PASSWORD, password);
        userValues.put(COLUMN_ROLE, "Teacher");  // Role is directly "Teacher"
        userValues.put(COLUMN_USER_SCHOOL_ID, schoolId);


        try {
            // Insert into Users table
            teacherUserId = db.insert(TABLE_USERS, null, userValues);
            if (teacherUserId == -1) {
                return -1;
            }

            // Insert teacher-specific details into Teachers table
            ContentValues teacherValues = new ContentValues();
            teacherValues.put(COLUMN_TEACHER_USER_ID, teacherUserId);
            teacherValues.put(COLUMN_DEPARTMENT, department);
            teacherValues.put(COLUMN_QUALIFICATION, qualification);
            teacherValues.put(COLUMN_EMPLOYMENT_DATE, employmentDate);

            long teacherResult = db.insert(TABLE_TEACHERS, null, teacherValues);
            if (teacherResult == -1) {
                db.delete(TABLE_USERS, COLUMN_USER_ID + "=?", new String[]{String.valueOf(teacherUserId)});
                return -1;
            }
            return teacherUserId;
        } finally {
            db.close();
        }
    }

    // Retrieve all Teachers and their details
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to fetch all users with role "Teacher"
        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{COLUMN_USER_ID, COLUMN_NAME, COLUMN_EMAIL}, // Only select relevant columns
                COLUMN_ROLE + "=?",
                new String[]{"Teacher"},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Initialize and populate Teacher object with user details
                Teacher teacher = new Teacher();
                teacher.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)));
                teacher.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                teacher.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)));

                // Query the Teachers table to fetch teacher-specific details
                Cursor teacherCursor = db.query(
                        TABLE_TEACHERS,
                        new String[]{COLUMN_DEPARTMENT, COLUMN_QUALIFICATION},
                        COLUMN_TEACHER_USER_ID + "=?",
                        new String[]{String.valueOf(teacher.getUserId())},
                        null, null, null
                );

                if (teacherCursor != null && teacherCursor.moveToFirst()) {
                    // Populate teacher-specific details
                    teacher.setDepartment(teacherCursor.getString(teacherCursor.getColumnIndexOrThrow(COLUMN_DEPARTMENT)));
                    teacher.setQualification(teacherCursor.getString(teacherCursor.getColumnIndexOrThrow(COLUMN_QUALIFICATION)));
                }

                if (teacherCursor != null) {
                    teacherCursor.close();
                }

                // Add the populated Teacher object to the list
                teachers.add(teacher);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return teachers;
    }



    // Update Teacher
    public int updateTeacher(long teacherUserId, String name, String email, String password, String department, String qualification, String employmentDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = 0;

        // Update user information in Users table
        ContentValues userValues = new ContentValues();
        userValues.put(COLUMN_NAME, name);
        userValues.put(COLUMN_EMAIL, email);
        userValues.put(COLUMN_PASSWORD, password);

        rowsAffected = db.update(TABLE_USERS, userValues, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(teacherUserId)});

        if (rowsAffected > 0) {
            // Update teacher-specific details in Teachers table
            ContentValues teacherValues = new ContentValues();
            teacherValues.put(COLUMN_DEPARTMENT, department);
            teacherValues.put(COLUMN_QUALIFICATION, qualification);
            teacherValues.put(COLUMN_PAYMENT_DETAILS, employmentDate);

            rowsAffected = db.update(TABLE_TEACHERS, teacherValues, COLUMN_TEACHER_USER_ID + " = ?", new String[]{String.valueOf(teacherUserId)});
        }

        db.close();
        return rowsAffected;
    }

    // Delete Teacher
    public int deleteTeacher(long teacherUserId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = 0;

        // Delete teacher-specific details from Teachers table
        rowsAffected = db.delete(TABLE_TEACHERS, COLUMN_TEACHER_USER_ID + " = ?", new String[]{String.valueOf(teacherUserId)});

        if (rowsAffected > 0) {
            // Delete user record from Users table
            rowsAffected = db.delete(TABLE_USERS, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(teacherUserId)});
        }

        db.close();
        return rowsAffected;
    }

    // Get Teacher by ID
    public Teacher getTeacherById(long teacherUserId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Teacher teacher = null;

        // SQL query to fetch teacher by UserID
        String query = "SELECT * FROM " + TABLE_USERS + " u JOIN " + TABLE_TEACHERS + " t ON u.UserID = t.TeacherUserID WHERE u.UserID = ?";

        // Execute the query
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(teacherUserId)});

        // Log the column names to verify they are as expected
        String[] columnNames = cursor.getColumnNames();
        Log.d("Cursor Columns", Arrays.toString(columnNames));  // Log all column names to check

        // Check if cursor is not null and contains data
        if (cursor != null && cursor.moveToFirst()) {
            // Log the column indices to verify
            int userIdIndex = cursor.getColumnIndex(COLUMN_USER_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
            int departmentIndex = cursor.getColumnIndex(COLUMN_DEPARTMENT);
            int qualificationIndex = cursor.getColumnIndex(COLUMN_QUALIFICATION);

            Log.d("Column Indices", "UserID: " + userIdIndex + ", Name: " + nameIndex + ", Email: " + emailIndex);

            // Check if indices are valid
            if (userIdIndex >= 0 && nameIndex >= 0 && emailIndex >= 0 && departmentIndex >= 0 && qualificationIndex >= 0) {
                teacher = new Teacher();
                teacher.setUserId(cursor.getInt(userIdIndex));  // Use the column index directly
                teacher.setName(cursor.getString(nameIndex));
                teacher.setEmail(cursor.getString(emailIndex));
                teacher.setDepartment(cursor.getString(departmentIndex));
                teacher.setQualification(cursor.getString(qualificationIndex));
            } else {
                Log.e("Error", "One or more column indices are invalid. Check the query and database schema.");
            }

            cursor.close();
        }

        db.close();
        return teacher;
    }

//     Add Teacher
//    public long addTeacher(String name, String email, String password, String department, String qualification, String employmentDate, long schoolId) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        long teacherUserId = -1;
//
//        ContentValues userValues = new ContentValues();
//        userValues.put(COLUMN_NAME, name);
//        userValues.put(COLUMN_EMAIL, email);
//        userValues.put(COLUMN_PASSWORD, password);
//        userValues.put(COLUMN_ROLE, "Teacher");  // Role is directly "Teacher"
//        userValues.put(COLUMN_USER_SCHOOL_ID, schoolId);
//
//
//        try {
//            // Insert into Users table
//            teacherUserId = db.insert(TABLE_USERS, null, userValues);
//            if (teacherUserId == -1) {
//                return -1;
//            }
//
//            // Insert teacher-specific details into Teachers table
//            ContentValues teacherValues = new ContentValues();
//            teacherValues.put(COLUMN_TEACHER_USER_ID, teacherUserId);
//            teacherValues.put(COLUMN_DEPARTMENT, department);
//            teacherValues.put(COLUMN_QUALIFICATION, qualification);
//            teacherValues.put(COLUMN_EMPLOYMENT_DATE, employmentDate);
//
//            long teacherResult = db.insert(TABLE_TEACHERS, null, teacherValues);
//            if (teacherResult == -1) {
//                db.delete(TABLE_USERS, COLUMN_USER_ID + "=?", new String[]{String.valueOf(teacherUserId)});
//                return -1;
//            }
//            return teacherUserId;
//        } finally {
//            db.close();
//        }
//    }


    public long addStudent(String name, String email, String password, String dateOfAdmission, String gradeLevel, String enrolledProgram, long classId, long schoolId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long studentUserId = -1;

        ContentValues userValues = new ContentValues();
        userValues.put(COLUMN_NAME, name);
        userValues.put(COLUMN_EMAIL, email);
        userValues.put(COLUMN_PASSWORD, password);
        userValues.put(COLUMN_ROLE, "Student"); // Role is directly "Student"
        userValues.put(COLUMN_USER_SCHOOL_ID, schoolId);

        try {
            // Insert into Users table
            studentUserId = db.insert(TABLE_USERS, null, userValues);
            if (studentUserId == -1) {
                return -1; // Failed to insert into Users
            }

            // Insert student-specific details into Students table
            ContentValues studentValues = new ContentValues();
            studentValues.put(COLUMN_STUDENT_USER_ID, studentUserId);
            studentValues.put(COLUMN_STUDENT_ADMIT_DATE, dateOfAdmission);
            studentValues.put(COLUMN_GRADE_LEVEL, gradeLevel);
            studentValues.put(COLUMN_ENROLLED_PROGRAM, enrolledProgram);
            studentValues.put(COLUMN_CLASS_ID, classId);

            long studentResult = db.insert(TABLE_STUDENTS, null, studentValues);
            if (studentResult == -1) {
                // Rollback user entry if student-specific insert fails
                db.delete(TABLE_USERS, COLUMN_USER_ID + "=?", new String[]{String.valueOf(studentUserId)});
                return -1;
            }

            return studentUserId; // Return the user ID of the newly added student
        } finally {
            db.close();
        }
    }

    public int updateStudent(long studentId, String name, String email, String password, String dateOfAdmission, String gradeLevel, String enrolledProgram, long classId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsUpdated = 0;

        try {
            // Update Users table
            ContentValues userValues = new ContentValues();
            userValues.put(COLUMN_NAME, name);
            userValues.put(COLUMN_EMAIL, email);
            userValues.put(COLUMN_PASSWORD, password);

            rowsUpdated += db.update(TABLE_USERS, userValues, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(studentId)});

            // Update Students table
            ContentValues studentValues = new ContentValues();
            studentValues.put(COLUMN_STUDENT_ADMIT_DATE, dateOfAdmission);
            studentValues.put(COLUMN_GRADE_LEVEL, gradeLevel);
            studentValues.put(COLUMN_ENROLLED_PROGRAM, enrolledProgram);
            studentValues.put(COLUMN_CLASS_ID, classId);

            rowsUpdated += db.update(TABLE_STUDENTS, studentValues, COLUMN_STUDENT_USER_ID + " = ?", new String[]{String.valueOf(studentId)});

            return rowsUpdated;
        } finally {
            db.close();
        }
    }

    public int deleteStudent(long studentId) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // Delete from Users table (cascades to Students due to ON DELETE CASCADE)
            return db.delete(TABLE_USERS, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(studentId)});
        } finally {
            db.close();
        }
    }

    public String getClassNameById(long classId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String className = null;

                //COLUMN_CLASS_ID
        String query = "SELECT " + COLUMN_CLASSES_CLASS_NAME + " FROM " + TABLE_CLASSES + " WHERE " + COLUMN_CLASSES_CLASS_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(classId)});

        if (cursor.moveToFirst()) {
            className = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLASSES_CLASS_NAME));
        }
        cursor.close();
        db.close();

        return className;
    }



    public List<Student> getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Student> students = new ArrayList<>();

        String query = "SELECT u." + COLUMN_USER_ID + ", u." + COLUMN_NAME + ", u." + COLUMN_EMAIL + ", u." + COLUMN_PASSWORD +
                ", s." + COLUMN_STUDENT_ADMIT_DATE + ", s." + COLUMN_GRADE_LEVEL + ", s." + COLUMN_ENROLLED_PROGRAM +
                ", s." + COLUMN_CLASS_ID +
                " FROM " + TABLE_USERS + " u " +
                " INNER JOIN " + TABLE_STUDENTS + " s " +
                " ON u." + COLUMN_USER_ID + " = s." + COLUMN_STUDENT_USER_ID;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                        "Student", // Role is always "Student"
                        null, // Profile picture not fetched
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)), // Student ID is same as User ID
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_ADMIT_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GRADE_LEVEL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENROLLED_PROGRAM)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CLASS_ID))
                );
                students.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return students;
    }
//
//    public Student getStudentById(long studentId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Student student = null;
//
//        String query = "SELECT u." + COLUMN_USER_ID + ", u." + COLUMN_NAME + ", u." + COLUMN_EMAIL + ", u." + COLUMN_PASSWORD +
//                ", s." + COLUMN_STUDENT_ADMIT_DATE + ", s." + COLUMN_GRADE_LEVEL + ", s." + COLUMN_ENROLLED_PROGRAM +
//                ", s." + COLUMN_CLASS_ID +
//                " FROM " + TABLE_USERS + " u " +
//                " INNER JOIN " + TABLE_STUDENTS + " s " +
//                " ON u." + COLUMN_USER_ID + " = s." + COLUMN_STUDENT_USER_ID +
//                " WHERE u." + COLUMN_USER_ID + " = ?";
//
//        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(studentId)});
//
//        if (cursor.moveToFirst()) {
//            student = new Student(
//                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
//                    "Student",
//                    null,
//                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_ADMIT_DATE)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GRADE_LEVEL)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENROLLED_PROGRAM)),
//                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CLASS_ID))
//            );
//        }
//        cursor.close();
//        db.close();
//        return student;
//    }




    public Cursor getPublishedContent(int classId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(
                    "SELECT Title, Description, FilePath FROM " + TABLE_CONTENT + " WHERE ClassID = ?",
                    new String[]{String.valueOf(classId)}
            );
        } catch (Exception e) {
            Log.e("DB_QUERY_ERROR", "Error fetching published content for ClassID " + classId, e);
        }

        return cursor; // Return null if an exception occurs
    }





    public Cursor getStudentPublishedContent() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SpeciesName, Description, Location, ReportedDate, Status FROM SightingReports";
        return db.rawQuery(query, null);
    }



















}
