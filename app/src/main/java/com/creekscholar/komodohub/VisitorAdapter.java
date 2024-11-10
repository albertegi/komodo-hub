package com.creekscholar.komodohub;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class VisitorAdapter extends ArrayAdapter<String> {
    public VisitorAdapter(Context context, List<String> generalInfo) {
        super(context, 0, generalInfo);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom layout if needed
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_visitor, parent, false);
        }

        // Get the data item for this position
        String info = getItem(position);

        // Look up view for data population
        TextView infoTextView = convertView.findViewById(R.id.infoTextView);

        // Populate the data into the template view
        infoTextView.setText(info);

        // Return the completed view to render on screen
        return convertView;
    }
//    private List<Visitor> visitorList;
//
//
//    public VisitorAdapter(List<Visitor> visitorList) {
//        this.visitorList = visitorList;
//    }
//
//    public void setVisitors(List<Visitor> visitors) {
//        this.visitorList = visitors;
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public VisitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visitor, parent, false);
//        return new VisitorViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull VisitorViewHolder holder, int position) {
//        Visitor visitor = visitorList.get(position);
//        holder.nameTextView.setText(visitor.getName());
//        holder.emailTextView.setText(visitor.getEmail());
//    }
//
//    @Override
//    public int getItemCount() {
//        return visitorList.size();
//    }
//
//    static class VisitorViewHolder extends RecyclerView.ViewHolder {
//        TextView nameTextView, emailTextView;
//
//        VisitorViewHolder(@NonNull View itemView) {
//            super(itemView);
//            nameTextView = itemView.findViewById(R.id.visitorNameTextView);
//            emailTextView = itemView.findViewById(R.id.visitorEmailTextView);
//        }
//    }
}
