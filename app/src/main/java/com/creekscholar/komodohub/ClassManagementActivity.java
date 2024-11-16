package com.creekscholar.komodohub;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class ClassManagementActivity extends AppCompatActivity {

    private ListView classListView;
    private EditText classNameInput, classSubjectInput, classTeacherIdInput;
    private Button addButton, updateButton, deleteButton;
    private Spinner teacherSpinner; // Declare the Spinner at the class level
    private DatabaseConnection databaseConnection;
    private List<Class> classList;
    private ArrayAdapter<Class> adapter;
    private long selectedClassId = -1;

    private Map<String, Integer> teacherIdMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_management);

        // Initialize the Spinner
        teacherSpinner = findViewById(R.id.teacher_spinner);

        // Initialize UI components
        classListView = findViewById(R.id.class_list_view);
        classNameInput = findViewById(R.id.class_name_input);
        classSubjectInput = findViewById(R.id.subject_input);


        addButton = findViewById(R.id.add_class_button);
        updateButton = findViewById(R.id.update_class_button);
        deleteButton = findViewById(R.id.delete_class_button);

        databaseConnection = new DatabaseConnection(this);

        // Load initial data into ListView
        loadClassList();

        // Handle ListView item selection for editing
        classListView.setOnItemClickListener((parent, view, position, id) -> {
            Class selectedClass = classList.get(position);
            loadClassDetailsForEditing(selectedClass);
        });

        // Add new Class
        //addButton.setOnClickListener(v -> addClass());

        // Update selected Class
        updateButton.setOnClickListener(v -> updateClass());

        // Delete selected Class
        deleteButton.setOnClickListener(v -> deleteClass());


        List<Teacher> teachers = databaseConnection.getAllTeachers();
        List<String> teacherNames = new ArrayList<>();
        //Map<String, Integer> teacherIdMap = new HashMap<>();

        for (Teacher teacher : teachers) {
            teacherNames.add(teacher.getName());
            teacherIdMap.put(teacher.getName(), teacher.getUserId());
        }

        if (teacherSpinner != null) {
            // Set up the adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teacherNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            teacherSpinner.setAdapter(adapter);
        } else {
            Log.e("ClassManagementActivity", "teacherSpinner is null");
        }


        // Get SchoolID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String schoolIdString = sharedPreferences.getString("SchoolId", "");
        int schoolId = !schoolIdString.isEmpty() ? Integer.parseInt(schoolIdString) : -1;

        // When the "Add Class" button is clicked
        addButton.setOnClickListener(v -> {
            String className = classNameInput.getText().toString().trim();
            String subject = classSubjectInput.getText().toString().trim();
            String selectedTeacherName = (String) teacherSpinner.getSelectedItem();

            int teacherId = teacherIdMap.get(selectedTeacherName);  // Get the teacher's ID based on selected name

            if (schoolId != -1) {
                long newClassId = databaseConnection.addClass(className, subject, teacherId, schoolId);
                if (newClassId != -1) {
                    Toast.makeText(this, "Class added successfully!", Toast.LENGTH_SHORT).show();
                    // Refresh class list or clear inputs as needed
                } else {
                    Toast.makeText(this, "Failed to add class", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Invalid School ID", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void loadClassList() {
        classList = databaseConnection.getAllClasses();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classList);
        classListView.setAdapter(adapter);
    }

//    private void loadClassDetailsForEditing(Class classItem) {
//        selectedClassId = classItem.getClassId();
//        classNameInput.setText(classItem.getClassName());
//        classSubjectInput.setText(classItem.getSubject());
//        classTeacherIdInput.setText(String.valueOf(classItem.getTeacherId()));
//    }

//    private void loadClassDetailsForEditing(Class classItem) {
//        if (classItem == null) {
//            Log.e("ClassManagementActivity", "classItem is null, cannot load class details");
//            return;
//        }
//
//        selectedClassId = classItem.getClassId();
//
//        // Set the text for the class name and subject EditTexts
//        if (classNameInput != null) {
//            classNameInput.setText(classItem.getClassName());
//        }
//
//        if (classSubjectInput != null) {
//            classSubjectInput.setText(classItem.getSubject());
//        }
//
//        // Set the selected teacher in the Spinner
//        if (teacherSpinner != null) {
//            int teacherId = classItem.getTeacherId();
//
//            // Get the teacher's name based on the ID from the map
//            String selectedTeacherName = null;
//            for (Map.Entry<String, Integer> entry : teacherIdMap.entrySet()) {
//                if (entry.getValue() == teacherId) {
//                    selectedTeacherName = entry.getKey();
//                    break;
//                }
//            }
//
//            if (selectedTeacherName != null) {
//                // Find the position of the teacher name in the adapter and set it as selected
//                ArrayAdapter<String> adapter = (ArrayAdapter<String>) teacherSpinner.getAdapter();
//                int position = adapter.getPosition(selectedTeacherName);
//                teacherSpinner.setSelection(position);
//            }
//        }
//    }
//
private void loadClassDetailsForEditing(Class classItem) {
    selectedClassId = classItem.getClassId();
    classNameInput.setText(classItem.getClassName());
    classSubjectInput.setText(classItem.getSubject());

    // Get the teacher ID and find the corresponding name for selection in the Spinner
    int teacherId = classItem.getTeacherId();
    for (int i = 0; i < teacherSpinner.getCount(); i++) {
        if (teacherIdMap.get(teacherSpinner.getItemAtPosition(i)) == teacherId) {
            teacherSpinner.setSelection(i);  // Set the Spinner to the correct teacher
            break;
        }
    }
}






    // When the "Add Class" button is clicked
//        addClassButton.setOnClickListener(v -> {
//        String className = classNameInput.getText().toString().trim();
//        String subject = classSubjectInput.getText().toString().trim();
//        String selectedTeacherName = (String) teacherSpinner.getSelectedItem();
//
//        int teacherId = teacherIdMap.get(selectedTeacherName);  // Get the teacher's ID based on selected name
//
//        if (schoolId != -1) {
//            long newClassId = databaseConnection.addClass(className, subject, teacherId, schoolId);
//            if (newClassId != -1) {
//                Toast.makeText(this, "Class added successfully!", Toast.LENGTH_SHORT).show();
//                // Refresh class list or clear inputs as needed
//            } else {
//                Toast.makeText(this, "Failed to add class", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(this, "Invalid School ID", Toast.LENGTH_SHORT).show();
//        }
//    });


//    private void addClass() {
//        String className = classNameInput.getText().toString().trim();
//        String subject = classSubjectInput.getText().toString().trim();
//        String selectedTeacherName = (String) teacher_spinner.getSelectedItem();
//
//        int teacherId = teacherIdMap.get(selectedTeacherName);  // Get the teacher's ID based on selected name
//
//        if (schoolId != -1) {
//            long newClassId = databaseConnection.addClass(className, subject, teacherId, schoolId);
//            if (newClassId != -1) {
//                Toast.makeText(this, "Class added successfully!", Toast.LENGTH_SHORT).show();
//                // Refresh class list or clear inputs as needed
//            } else {
//                Toast.makeText(this, "Failed to add class", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(this, "Invalid School ID", Toast.LENGTH_SHORT).show();
//        }
//
//    }

//    private void addClass() {
//        String className = classNameInput.getText().toString().trim();
//        String classSubject = classSubjectInput.getText().toString().trim();
//        int teacherId = Integer.parseInt(classTeacherIdInput.getText().toString().trim());
//
//        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
//        String schoolIdString = sharedPreferences.getString("SchoolId", "");
//        int schoolId = !schoolIdString.isEmpty() ? Integer.parseInt(schoolIdString) : -1;
//
//        if (schoolId != -1) {
//            long newClassId = databaseConnection.addClass(className, classSubject, teacherId, schoolId);
//            if (newClassId != -1) {
//                Toast.makeText(this, "Class added successfully!", Toast.LENGTH_SHORT).show();
//                loadClassList();
//                clearInputs();
//            } else {
//                Toast.makeText(this, "Failed to add Class", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(this, "Invalid School ID", Toast.LENGTH_SHORT).show();
//        }
//    }

//    private void updateClass() {
//        if (selectedClassId != -1) {
//            String className = classNameInput.getText().toString().trim();
//            String classSubject = classSubjectInput.getText().toString().trim();
//            int teacherId = Integer.parseInt(classTeacherIdInput.getText().toString().trim());
//
//            int updated = databaseConnection.updateClass(selectedClassId, className, classSubject, teacherId);
//            if (updated > 0) {
//                Toast.makeText(this, "Class updated successfully!", Toast.LENGTH_SHORT).show();
//                loadClassList();
//                clearInputs();
//            } else {
//                Toast.makeText(this, "Failed to update Class", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    private void updateClass() {
        if (selectedClassId != -1) {
            // Retrieve values from input fields
            String className = classNameInput.getText().toString().trim();
            String classSubject = classSubjectInput.getText().toString().trim();

            // Get the selected teacher's name from the Spinner and then retrieve their ID from the map
            String selectedTeacherName = (String) teacherSpinner.getSelectedItem();
            int teacherId = teacherIdMap.getOrDefault(selectedTeacherName, -1);

            if (teacherId == -1) {
                Toast.makeText(this, "Invalid teacher selected.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update the class in the database
            int updated = databaseConnection.updateClass(selectedClassId, className, classSubject, teacherId);

            if (updated > 0) {
                Toast.makeText(this, "Class updated successfully!", Toast.LENGTH_SHORT).show();
                loadClassList();
                clearInputs();
            } else {
                Toast.makeText(this, "Failed to update Class", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No class selected to update", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteClass() {
        if (selectedClassId != -1) {
            // Attempt to delete the selected class
            int deleted = databaseConnection.deleteClass(selectedClassId);

            if (deleted > 0) {
                Toast.makeText(this, "Class deleted successfully!", Toast.LENGTH_SHORT).show();
                loadClassList();
                clearInputs();
                selectedClassId = -1;  // Reset selectedClassId after deletion
            } else {
                Toast.makeText(this, "Failed to delete Class", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No class selected to delete", Toast.LENGTH_SHORT).show();
        }
    }



//    private void deleteClass() {
//        if (selectedClassId != -1) {
//            int deleted = databaseConnection.deleteClass(selectedClassId);
//            if (deleted > 0) {
//                Toast.makeText(this, "Class deleted successfully!", Toast.LENGTH_SHORT).show();
//                loadClassList();
//                clearInputs();
//            } else {
//                Toast.makeText(this, "Failed to delete Class", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    private void clearInputs() {
//        selectedClassId = -1;
//        classNameInput.setText("");
//        classSubjectInput.setText("");
//        classTeacherIdInput.setText("");
//    }

    private void clearInputs() {
        // Clear text fields
        if (classNameInput != null) {
            classNameInput.setText("");
        }
        if (classSubjectInput != null) {
            classSubjectInput.setText("");
        }

        // Reset the spinner to its default state (first item selected)
        if (teacherSpinner != null && teacherSpinner.getAdapter() != null && teacherSpinner.getAdapter().getCount() > 0) {
            teacherSpinner.setSelection(0);  // Set to first item in Spinner
        }

        // Reset the selectedClassId for future operations
        selectedClassId = -1;
    }

}
