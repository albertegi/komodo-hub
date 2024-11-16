package com.creekscholar.komodohub;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ManageEntitiesActivity extends AppCompatActivity {
    private RecyclerView entityList;
    private Button tabTeachers, tabStudents, tabClasses, addEntityButton, editEntityButton, deleteEntityButton;
    private DatabaseConnection databaseConnection;
    private String currentTab = "Teachers"; // Tracks the current selected tab

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_entities);

        databaseConnection = new DatabaseConnection(this);
        initializeUI();

        // Initial load for Teachers
        loadEntities("Teachers");

        // Tab click listeners
        tabTeachers.setOnClickListener(v -> loadEntities("Teachers"));
        tabStudents.setOnClickListener(v -> loadEntities("Students"));
        tabClasses.setOnClickListener(v -> loadEntities("Classes"));

        // CRUD button listeners
        addEntityButton.setOnClickListener(v -> addEntity());
        editEntityButton.setOnClickListener(v -> editEntity());
        deleteEntityButton.setOnClickListener(v -> deleteEntity());
    }

    private void initializeUI() {
        tabTeachers = findViewById(R.id.tab_teachers);
        tabStudents = findViewById(R.id.tab_students);
        tabClasses = findViewById(R.id.tab_classes);
        entityList = findViewById(R.id.entity_list);
        addEntityButton = findViewById(R.id.add_entity_button);
        editEntityButton = findViewById(R.id.edit_entity_button);
        deleteEntityButton = findViewById(R.id.delete_entity_button);

        entityList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadEntities(String entityType) {
        currentTab = entityType;
        List<? extends DisplayableEntity> entities;

        switch (entityType) {
            case "Teachers":
                entities = (List<? extends DisplayableEntity>) databaseConnection.getAllTeachers();
                break;
            case "Students":
                entities = (List<? extends DisplayableEntity>) databaseConnection.getAllStudents();
                break;
            case "Classes":
                entities = (List<? extends DisplayableEntity>) databaseConnection.getAllClasses();
                break;
            default:
                entities = new ArrayList<>();
        }

        // Initialize the adapter with the context and the list of entities
        EntityAdapter adapter = new EntityAdapter(this, entities);
        entityList.setAdapter(adapter);
    }


    private void addEntity() {
        // Open dialog or activity to input data for new entity
        switch (currentTab) {
            case "Teachers":
                // Code to add teacher
                break;
            case "Students":
                // Code to add student
                break;
            case "Classes":
                // Code to add class
                break;
        }
        loadEntities(currentTab); // Refresh list
    }

    private void editEntity() {
        // Code to edit the selected entity based on currentTab
        loadEntities(currentTab); // Refresh list
    }

    private void deleteEntity() {
        // Code to delete the selected entity based on currentTab
        loadEntities(currentTab); // Refresh list
    }
}
