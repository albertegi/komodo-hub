package com.creekscholar.komodohub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentContentAdapter extends RecyclerView.Adapter<StudentContentAdapter.ContentViewHolder> {

    private Context context;
    private List<SightingReport> contentList;

    public StudentContentAdapter(Context context, List<SightingReport> contentList) {
        this.context = context;
        this.contentList = contentList;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the correct item layout
        View view = LayoutInflater.from(context).inflate(R.layout.sighting_reports_item_content, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        SightingReport sightingReport = contentList.get(position);

        // Bind data to views
        holder.tvSpeciesName.setText(sightingReport.getSpeciesName());
        holder.tvDescription.setText(sightingReport.getDescription());
        holder.tvLocation.setText(sightingReport.getLocation());
        holder.tvReportedDate.setText(sightingReport.getReportedDate());
        holder.tvStatus.setText(sightingReport.getStatus());
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView tvSpeciesName, tvDescription, tvLocation, tvReportedDate, tvStatus, tvStudentName;

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);

            // Correctly bind views using IDs from sighting_reports_item_content.xml
            tvSpeciesName = itemView.findViewById(R.id.tvSpeciesName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvReportedDate = itemView.findViewById(R.id.tvReportedDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
        }
    }
}
