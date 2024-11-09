package com.creekscholar.komodohub;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class VisitorAdapter extends RecyclerView.Adapter<VisitorAdapter.VisitorViewHolder>  {
    private List<Visitor> visitorList;


    public VisitorAdapter(List<Visitor> visitorList) {
        this.visitorList = visitorList;
    }

    public void setVisitors(List<Visitor> visitors) {
        this.visitorList = visitors;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VisitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visitor, parent, false);
        return new VisitorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorViewHolder holder, int position) {
        Visitor visitor = visitorList.get(position);
        holder.nameTextView.setText(visitor.getName());
        holder.emailTextView.setText(visitor.getEmail());
    }

    @Override
    public int getItemCount() {
        return visitorList.size();
    }

    static class VisitorViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, emailTextView;

        VisitorViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.visitorNameTextView);
            emailTextView = itemView.findViewById(R.id.visitorEmailTextView);
        }
    }
}
