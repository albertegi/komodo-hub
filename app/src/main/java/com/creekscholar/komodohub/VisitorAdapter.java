//package com.creekscholar.komodohub;
//
//import android.content.Context;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import java.util.List;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import java.util.List;
//
//public class VisitorAdapter extends ArrayAdapter<String> {
//    public VisitorAdapter(Context context, List<String> generalInfo) {
//        super(context, 0, generalInfo);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Inflate the custom layout if needed
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_visitor, parent, false);
//        }
//
//        // Get the data item for this position
//        String info = getItem(position);
//
//        // Look up view for data population
//        TextView infoTextView = convertView.findViewById(R.id.infoTextView);
//
//        // Populate the data into the template view
//        infoTextView.setText(info);
//
//        // Return the completed view to render on screen
//        return convertView;
//    }
//
//
//}
