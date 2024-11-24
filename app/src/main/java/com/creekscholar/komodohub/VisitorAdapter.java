package com.creekscholar.komodohub;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class VisitorAdapter extends RecyclerView.Adapter<VisitorAdapter.VisitorViewHolder> {
    private Context context;
    private List<SightingReport> sightingReportList;

    public VisitorAdapter(Context context, List<SightingReport> sightingReportList) {
        this.context = context;
        this.sightingReportList = sightingReportList;
    }

    @NonNull
    @Override
    public VisitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_visitor, parent, false);
        return new VisitorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorViewHolder holder, int position) {
        SightingReport report = sightingReportList.get(position);

        holder.tvSpeciesName.setText(report.getSpeciesName());
        holder.tvDescription.setText(report.getDescription());
        holder.tvLocation.setText(report.getLocation());
        holder.tvReportedDate.setText(report.getReportedDate());

        // Load photo from local storage or URI
        if (report.getPhoto() != null && !report.getPhoto().isEmpty()) {
            File imgFile = new File(report.getPhoto());

            if (imgFile.exists()) {
                // Load bitmap from file
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.ivPhoto.setImageBitmap(bitmap);
            } else {
                // Load from URI if it's not a file path
                try {
                    Uri imageUri = Uri.parse(report.getPhoto());
                    holder.ivPhoto.setImageURI(imageUri);
                } catch (Exception e) {
                    holder.ivPhoto.setImageResource(R.drawable.placeholder_image); // Default placeholder
                }
            }
        } else {
            holder.ivPhoto.setImageResource(R.drawable.placeholder_image); // Default placeholder
        }
    }

    @Override
    public int getItemCount() {
        return sightingReportList.size();
    }

    public static class VisitorViewHolder extends RecyclerView.ViewHolder {
        TextView tvSpeciesName, tvDescription, tvLocation, tvReportedDate;
        ImageView ivPhoto;

        public VisitorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSpeciesName = itemView.findViewById(R.id.tvSpeciesName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvReportedDate = itemView.findViewById(R.id.tvReportedDate);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
        }
    }
}

//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.List;
//
//public class VisitorAdapter extends RecyclerView.Adapter<VisitorAdapter.VisitorViewHolder> {
//    private Context context;
//    private List<SightingReport> sightingReportList;
//
//    public VisitorAdapter(Context context, List<SightingReport> sightingReportList) {
//        this.context = context;
//        this.sightingReportList = sightingReportList;
//    }
//
//    @NonNull
//    @Override
//    public VisitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_visitor, parent, false);
//        return new VisitorViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull VisitorViewHolder holder, int position) {
//        SightingReport report = sightingReportList.get(position);
//
//        holder.tvSpeciesName.setText(report.getSpeciesName());
//        holder.tvDescription.setText(report.getDescription());
//        holder.tvLocation.setText(report.getLocation());
//        holder.tvReportedDate.setText(report.getReportedDate());
//
//        // Load photo using Glide
//        if (report.getPhoto() != null && !report.getPhoto().isEmpty()) {
//            Glide.with(context)
//                    .load(report.getPhoto())
//                    .into(holder.ivPhoto);
//        } else {
//            holder.ivPhoto.setImageResource(R.drawable.placeholder_image); // Default placeholder
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return sightingReportList.size();
//    }
//
//    public static class VisitorViewHolder extends RecyclerView.ViewHolder {
//        TextView tvSpeciesName, tvDescription, tvLocation, tvReportedDate;
//        ImageView ivPhoto;
//
//        public VisitorViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tvSpeciesName = itemView.findViewById(R.id.tvSpeciesName);
//            tvDescription = itemView.findViewById(R.id.tvDescription);
//            tvLocation = itemView.findViewById(R.id.tvLocation);
//            tvReportedDate = itemView.findViewById(R.id.tvReportedDate);
//            ivPhoto = itemView.findViewById(R.id.ivPhoto);
//        }
//    }
//}
