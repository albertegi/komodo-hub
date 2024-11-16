package com.creekscholar.komodohub;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {
    private List<Teacher> teachers;
    private Context context;

    public TeacherAdapter(Context context, List<Teacher> teachers) {
        this.context = context;
        this.teachers = teachers;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.entity_item, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        Teacher teacher = teachers.get(position);
        holder.bind(teacher);
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    // ViewHolder class for each teacher item
    static class TeacherViewHolder extends RecyclerView.ViewHolder {
        TextView entityName;
        TextView entityDescription;

        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            entityName = itemView.findViewById(R.id.entity_name);
            entityDescription = itemView.findViewById(R.id.entity_description);
        }

        // Bind method to set the data
        public void bind(Teacher teacher) {
            entityName.setText(teacher.getName());
            entityDescription.setText(teacher.getDescription());
        }
    }
}

