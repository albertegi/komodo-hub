package com.creekscholar.komodohub;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EntityAdapter extends RecyclerView.Adapter<EntityAdapter.EntityViewHolder> {
    private List<? extends DisplayableEntity> entities;
    private Context context;

    public EntityAdapter(Context context, List<? extends DisplayableEntity> entities) {
        this.context = context;
        this.entities = entities;
    }

    @NonNull
    @Override
    public EntityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.entity_item, parent, false);
        return new EntityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntityViewHolder holder, int position) {
        DisplayableEntity entity = entities.get(position);
        holder.bind(entity);
    }

    @Override
    public int getItemCount() {
        return entities.size();
    }

    static class EntityViewHolder extends RecyclerView.ViewHolder {
        TextView entityName;
        TextView entityDescription;

        public EntityViewHolder(@NonNull View itemView) {
            super(itemView);
            entityName = itemView.findViewById(R.id.entity_name);
            entityDescription = itemView.findViewById(R.id.entity_description);
        }

        public void bind(DisplayableEntity entity) {
            entityName.setText(entity.getName());
            entityDescription.setText(entity.getDescription());
        }
    }
}


