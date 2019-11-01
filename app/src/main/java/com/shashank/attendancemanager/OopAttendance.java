package com.shashank.attendancemanager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OopAttendance extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AttendanceAdapter mAdapter;
    ArrayList<StudentsData> studentsDataList=new ArrayList<>();
    private DatabaseReference databaseReference;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageButton buttonTick;
    private ImageButton buttonCross;
    private TextView lecturesAttended;
    private TextView totalLectures;
    private TextView mStudentName;
    private TextView percentAttendance;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oop_attendance);
        recyclerView=findViewById(R.id.oop_recylerview);
        progressBar = findViewById(R.id.spin_kit);
        progressBar.setIndeterminateDrawable(new FadingCircle());
        /*recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentsDataList.add(new StudentsData("Shashank Patel","2","3","66%"));
        mAdapter=new AttendanceAdapter(this,studentsDataList);
        recyclerView.setAdapter(mAdapter);*/
        databaseReference= FirebaseDatabase.getInstance().getReference("teachers").child("OOP");
       /* final ProgressDialog progressDialog=new ProgressDialog(OopAttendance.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();*/
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentsDataList.clear();
                for(DataSnapshot students : dataSnapshot.getChildren()){
                    String mName=students.child("mName").getValue(String.class);
                    String mLecturesAttended=students.child("mLecturesAttended").getValue(String.class);
                    String mTotalLectures=students.child("mTotalLectures").getValue(String.class);
                    String mAttendancePercent=students.child("mAttendancePercent").getValue(String.class);
                    if(mName.contains("ADS")){
                        String temp="ADS ";
                        mName=mName.replaceAll(temp,"");
                        temp=" ADS";
                        mName=mName.replaceAll(temp,"");
                    }
                    if(mName.contains("OOP")){
                        String temp="OOP ";
                        mName=mName.replaceAll(temp,"");
                        temp=" OOP";
                        mName=mName.replaceAll(temp,"");
                    }
                    if(mName.contains("DSGT")){
                        String temp="DSGT ";
                        mName=mName.replaceAll(temp,"");
                        temp=" DSGT";
                        mName=mName.replaceAll(temp,"");
                    }
                    if(mName.contains("DLDA")){
                        String temp="DLDA ";
                        mName=mName.replaceAll(temp,"");
                        temp=" DLDA";
                        mName=mName.replaceAll(temp,"");
                    }
                    if(mName.contains("Maths")){
                        String temp="Maths ";
                        mName=mName.replaceAll(temp,"");
                        temp=" Maths";
                        mName=mName.replaceAll(temp,"");
                    }
                    StudentsData students1=new StudentsData(mName,mLecturesAttended,mTotalLectures,mAttendancePercent);
                    studentsDataList.add(students1);
                }
                mAdapter=new AttendanceAdapter(OopAttendance.this,studentsDataList);
                mLayoutManager=new LinearLayoutManager(OopAttendance.this);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
               // progressDialog.dismiss();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
        /*ImageButton buttonTick = findViewById(R.id.button_tick);
        ImageButton buttonCross =findViewById(R.id.button_cross);
        lecturesAttended = findViewById(R.id.tv_lectures_attended_count);
        totalLectures = findViewById(R.id.tv_total_lectures_count);
        mStudentName = findViewById(R.id.tv_student_attendance_name);
        percentAttendance = findViewById(R.id.tv_attendance_percent);*/
        buttonTick = findViewById(R.id.button_tick);
    }
}
