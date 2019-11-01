package com.shashank.attendancemanager;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {
    private Context context;
    private List<StudentsData> studentsList;
    private OnItemClickListener mListener;
    private DatabaseReference database;
    private static final String TAG = "AttendanceAdapter";
    StudentsData student;
    private String defaulter;

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
    public void onBindViewHolder(@NonNull final AttendanceViewHolder holder, int position) {
        //YoYo.with(Techniques.FadeIn).playOn(holder.cardView);
        student = studentsList.get(position);
        holder.lecturesAttended.setText(student.getmLecturesAttended());
        holder.totalLectures.setText(student.getmTotalLectures());
        holder.percentAttendance.setText(student.getmAttendancePercent());
        holder.mStudentName.setText(student.getmName());
        holder.buttonTick.setTag(position);
        holder.percentAttendance.setTag(position);
        holder.lecturesAttended.setTag(position);
        holder.totalLectures.setTag(position);
        holder.buttonTick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                student=studentsList.get(pos);
                database=FirebaseDatabase.getInstance().getReference("teachers");
                final int count = Integer.parseInt(holder.lecturesAttended.getText().toString()) + 1;
                final int count2 = Integer.parseInt(holder.totalLectures.getText().toString()) + 1;
                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot teacherSnapshot : dataSnapshot.getChildren()){
                            for(DataSnapshot studentSnapshot : teacherSnapshot.getChildren()){
                                String mName=studentSnapshot.child("mName").getValue(String.class);
                                String mSubject=studentSnapshot.child("mSubject").getValue(String.class);
                                Log.d(TAG, "onDataChange: "+mName+" "+mSubject);
                                if((mName).equals(student.getmName())){
                                    Log.d(TAG, "Names: "+student.getmName());
                                    studentSnapshot.child("mLecturesAttended").getRef().setValue(Integer.toString(count));
                                    studentSnapshot.child("mTotalLectures").getRef().setValue(Integer.toString(count2));
                                    studentSnapshot.child("mAttendancePercent").getRef().setValue(defaulter+"%");
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                holder.lecturesAttended.setText(Integer.toString(count));
                holder.totalLectures.setText(Integer.toString(count2));
            }
        });
        holder.buttonCross.setTag(position);
        holder.buttonCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                student=studentsList.get(pos);
                final int count2 = Integer.parseInt(holder.totalLectures.getText().toString()) + 1;
                holder.totalLectures.setText(Integer.toString(count2));
                database=FirebaseDatabase.getInstance().getReference("teachers");
                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot teacherSnapshot : dataSnapshot.getChildren()){
                            for(DataSnapshot studentSnapshot : teacherSnapshot.getChildren()){
                                String mName=studentSnapshot.child("mName").getValue(String.class);
                                String mSubject=studentSnapshot.child("mSubject").getValue(String.class);
                                if(mName.equals(student.getmName())){
                                    studentSnapshot.child("mTotalLectures").getRef().setValue(Integer.toString(count2));
                                    //studentSnapshot.child("mAttendancePercent").getRef().setValue(defaulter+"%");
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        if (holder.totalLectures.getText().toString() != null)
        {
            holder.lecturesAttended.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int pos=(int)holder.percentAttendance.getTag();
                    student=studentsList.get(pos);
                    if (Integer.parseInt(holder.lecturesAttended.getText().toString()) != 0 && Integer.parseInt(holder.totalLectures.getText().toString()) != 0) {
                        double number = ((100 * Double.parseDouble(holder.lecturesAttended.getText().toString().trim())) / (Double.parseDouble(holder.totalLectures.getText().toString().trim())));
                        defaulter = String.format("%.2f",number);
                        database=FirebaseDatabase.getInstance().getReference("teachers");
                        database.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot teacherSnapshot : dataSnapshot.getChildren()){
                                    for(DataSnapshot studentSnapshot : teacherSnapshot.getChildren()){
                                        String mName=studentSnapshot.child("mName").getValue(String.class);
                                        String mSubject=studentSnapshot.child("mSubject").getValue(String.class);
                                        if(mName.equals(student.getmName())){
                                            studentSnapshot.child("mAttendancePercent").getRef().setValue(defaulter+"%");
                                            break;
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        //holder.percentAttendance.setText(defaulter + "%");

                    } else {
                        //holder.percentAttendance.setText("");
                    }
                }
            });
        }
        if (holder.lecturesAttended.getText().toString() != null) {
            holder.totalLectures.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int pos=(int)holder.percentAttendance.getTag();
                    student=studentsList.get(pos);
                    if (!((holder.lecturesAttended.getText().toString().trim()).isEmpty() || holder.totalLectures.getText().toString().trim().isEmpty())) {
                        double number = ((100 * Double.parseDouble(holder.lecturesAttended.getText().toString().trim())) / (Double.parseDouble(holder.totalLectures.getText().toString().trim())));
                        final String defaulter = String.format("%.2f", number);
                        database=FirebaseDatabase.getInstance().getReference("teachers");
                        database.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot teacherSnapshot : dataSnapshot.getChildren()){
                                    for(DataSnapshot studentSnapshot : teacherSnapshot.getChildren()){
                                        String mName=studentSnapshot.child("mName").getValue(String.class);
                                        String mSubject=studentSnapshot.child("mSubject").getValue(String.class);
                                        if(mName.equals(student.getmName())){
                                            studentSnapshot.child("mAttendancePercent").getRef().setValue(defaulter+"%");
                                            break;
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        //holder.percentAttendance.setText(defaulter + "%");


                    } else {
                        //holder.percentAttendance.setText("");
                    }
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder {
        TextView lecturesAttended, totalLectures, mStudentName, percentAttendance;
        ImageButton buttonTick;
        ImageButton buttonCross;
        CardView cardView;

        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.card_test);
            lecturesAttended = itemView.findViewById(R.id.tv_lectures_attended_count);
            totalLectures = itemView.findViewById(R.id.tv_total_lectures_count);
            mStudentName = itemView.findViewById(R.id.tv_student_attendance_name);
            percentAttendance = itemView.findViewById(R.id.tv_attendance_percent);
            buttonTick = itemView.findViewById(R.id.button_tick);
            buttonCross = itemView.findViewById(R.id.button_cross);
            /*buttonTick.setOnClickListener(new View.OnClickListener() {
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
            });*/

           /* if (totalLectures.getText().toString() != null)
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
                            final String defaulter = String.format("%.2f",number);
                            database=FirebaseDatabase.getInstance().getReference("teachers");
                            database.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot teacherSnapshot : dataSnapshot.getChildren()){
                                        for(DataSnapshot studentSnapshot : teacherSnapshot.getChildren()){
                                            String mName=studentSnapshot.child("mName").getValue(String.class);
                                            String mSubject=studentSnapshot.child("mSubject").getValue(String.class);
                                            if(mName.equals(student.getmName())){
                                                studentSnapshot.child("mAttendancePercent").getRef().setValue(defaulter+"+");
                                                break;
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
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
                            final String defaulter = String.format("%.2f", number);
                            database=FirebaseDatabase.getInstance().getReference("teachers");
                            database.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot teacherSnapshot : dataSnapshot.getChildren()){
                                        for(DataSnapshot studentSnapshot : teacherSnapshot.getChildren()){
                                            String mName=studentSnapshot.child("mName").getValue(String.class);
                                            String mSubject=studentSnapshot.child("mSubject").getValue(String.class);
                                            if(mName.equals(student.getmName())){
                                                studentSnapshot.child("mAttendancePercent").getRef().setValue(defaulter+"+");
                                                break;
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            percentAttendance.setText(defaulter + "%");


                        } else {
                            percentAttendance.setText("");
                        }
                    }
                });
            }*/
        }
    }
}
