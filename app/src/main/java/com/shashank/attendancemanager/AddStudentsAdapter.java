package com.shashank.attendancemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddStudentsAdapter extends RecyclerView.Adapter<AddStudentsAdapter.StudentViewHolder>  {
    private ArrayList<StudentsData> msd;
    private Context context;

    public AddStudentsAdapter(ArrayList<StudentsData> msd, Context context) {
        this.msd = msd;
        this.context = context;
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder{
       public TextView studentName;
       public TextView subjectName;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName=itemView.findViewById(R.id.tv_student_name);
            subjectName=itemView.findViewById(R.id.tv_subject_name);
           // itemView.setOnClickListener((View.OnClickListener) this);
        }
    }
    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_students_cardview,parent,false);
        StudentViewHolder svh=new StudentViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        StudentsData currentItem=msd.get(position);
        holder.studentName.setText(currentItem.getmName());
        holder.subjectName.setText(currentItem.getmSubject());
    }


    public AddStudentsAdapter(ArrayList<StudentsData> sd){
        msd=sd;
    }


    @Override
    public int getItemCount() {
        return msd.size();
    }
}
