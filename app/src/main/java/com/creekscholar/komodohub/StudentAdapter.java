package com.creekscholar.komodohub;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private List<Student> students;
    private Context context;

    public StudentAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.entity_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = students.get(position);
        holder.bind(student);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    // ViewHolder class for each teacher item
    static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView entityName;
        TextView entityDescription;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            entityName = itemView.findViewById(R.id.entity_name);
            entityDescription = itemView.findViewById(R.id.entity_description);
        }

        // Bind method to set the data
        public void bind(Student student) {
            entityName.setText(student.getName());
            entityDescription.setText(student.getDescription());
        }
    }
}

//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import java.util.List;

//public class StudentAdapter extends ArrayAdapter<User> {

//    public StudentAdapter(Context context, List<User> students) {
//        super(context, 0, students);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
//        }
//
//        User student = getItem(position);
//        TextView studentNameView = convertView.findViewById(android.R.id.text1);
//        studentNameView.setText(student.getName());
//
//        return convertView;
//    }
//}
