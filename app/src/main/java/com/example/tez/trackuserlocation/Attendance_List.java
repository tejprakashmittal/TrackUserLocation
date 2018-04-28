package com.example.tez.trackuserlocation;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class Attendance_List extends ArrayAdapter<Attendance_Data> {
    private Activity activity;
    private List<Attendance_Data> userList;

    Attendance_List(@NonNull Activity context, @NonNull List<Attendance_Data> objects) {
        super(context, R.layout.list_layout, objects);
        this.activity = context;
        this.userList = objects;
    }
    private static class ViewHolder {

        private TextView text_name;
        private TextView text_status;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        convertView = layoutInflater.inflate(R.layout.list_layout,parent, false);
        mViewHolder.text_name = convertView.findViewById(R.id.txt_std_name);
        mViewHolder.text_status = convertView.findViewById(R.id.txt_std_status);
        Attendance_Data atd = userList.get(position);
        mViewHolder.text_name.setText(atd.getName());
        mViewHolder.text_status.setText(atd.getStatus());
        return convertView;

    }
}
