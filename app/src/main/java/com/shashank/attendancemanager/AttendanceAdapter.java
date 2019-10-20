package com.shashank.attendancemanager;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {
    private Context context;
    private List<StudentsData> studentsList;
    private OnItemClickListener mListener;
    private FirebaseDatabase database;
    private static final String TAG = "AttendanceAdapter";

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public AttendanceAdapter(Context context, ArrayList<StudentsData> studentsList) {
        this.context = context;
        this.studentsList = studentsList;
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.test, null);
        AttendanceViewHolder holder = new AttendanceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {
        StudentsData student = studentsList.get(position);
        holder.lecturesAttended.setText(student.getmLecturesAttended());
        holder.totalLectures.setText(student.getmTotalLectures());
        holder.percentAttendance.setText(student.getmAttendancePercent());
        holder.mStudentName.setText(student.getmName());
    }


    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder {
        TextView lecturesAttended, totalLectures, mStudentName, percentAttendance;



        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            lecturesAttended = itemView.findViewById(R.id.tv_lectures_attended_count);
            totalLectures = itemView.findViewById(R.id.tv_total_lectures_count);
            mStudentName = itemView.findViewById(R.id.tv_student_attendance_name);
            percentAttendance = itemView.findViewById(R.id.tv_attendance_percent);
            ImageButton buttonTick = itemView.findViewById(R.id.button_tick);
            ImageButton buttonCross = itemView.findViewById(R.id.button_cross);
            buttonTick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = Integer.parseInt(lecturesAttended.getText().toString()) + 1;
                    int count2 = Integer.parseInt(totalLectures.getText().toString()) + 1;
                    lecturesAttended.setText(Integer.toString(count));
                    totalLectures.setText(Integer.toString(count2));
                }
            });

            buttonCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count2 = Integer.parseInt(totalLectures.getText().toString()) + 1;
                    totalLectures.setText(Integer.toString(count2));
                }
            });

            if (totalLectures.getText().toString() != null)
            {
                lecturesAttended.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (Integer.parseInt(lecturesAttended.getText().toString()) != 0 && Integer.parseInt(totalLectures.getText().toString()) != 0) {
                            double number = ((100 * Double.parseDouble(lecturesAttended.getText().toString().trim())) / (Double.parseDouble(totalLectures.getText().toString().trim())));
                            String defaulter = String.format("%.2f",number);
                            percentAttendance.setText(defaulter + "%");
                        } else {
                            percentAttendance.setText("");
                        }
                    }
                });
            }
            if (lecturesAttended.getText().toString() != null) {
                totalLectures.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (!((lecturesAttended.getText().toString().trim()).isEmpty() || totalLectures.getText().toString().trim().isEmpty())) {
                            double number = ((100 * Double.parseDouble(lecturesAttended.getText().toString().trim())) / (Double.parseDouble(totalLectures.getText().toString().trim())));
                            String defaulter = String.format("%.2f", number);
                            percentAttendance.setText(defaulter + "%");
                        } else {
                            percentAttendance.setText("");
                        }
                    }
                });
            }
        }
    }
}
