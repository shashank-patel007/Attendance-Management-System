package com.shashank.attendancemanager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentAddStudents extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText name;
    private EditText subject;
    private Button addData;
    private DatabaseReference databaseStudent;
    private FirebaseUser mUser;
    ArrayList<StudentsData> sd=new ArrayList<>();
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_add_students,container,false);
        recyclerView=root.findViewById(R.id.recyclerView);
        final ArrayList<StudentsData> sd=new ArrayList<>();
       /* sd.add(new StudentsData("Shashank Patel","ADS"));
        sd.add(new StudentsData("Khushi Jashnani","OOP"));
        sd.add(new StudentsData("Karan Shah","DSGT"));
        sd.add(new StudentsData("Yug Vajani","Linux"));
        sd.add(new StudentsData("Harsh Sandesara","Maths"));
        sd.add(new StudentsData("Rohit Pai","DLDA"));*/
        /*recyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(getContext());
        mAdapter=new AddStudentsAdapter(sd);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);*/
        databaseStudent= FirebaseDatabase.getInstance().getReference("teachers");
        name=root.findViewById(R.id.et_student_name);
        subject=root.findViewById(R.id.et_subject_name);
        addData=root.findViewById(R.id.btn_add_student_data);

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        /*mAdapter=new AddStudentsAdapter(sd, getContext());
        recyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);*/

        addData.setOnClickListener(this);
        databaseStudent= FirebaseDatabase.getInstance().getReference("teachers");
        progressDialog.show();

      /*  databaseStudent.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               // sd.clear();
                  for (DataSnapshot d: dataSnapshot.getChildren()) {
                     // sd.clear();
                      StudentsData studentsData1=d.getValue(StudentsData.class);
                      sd.add(studentsData1);
                      progressDialog.dismiss();
                      mLayoutManager=new LinearLayoutManager(getContext());
                      recyclerView.setLayoutManager(mLayoutManager);
                      mAdapter.notifyDataSetChanged();
                     // Log.i("name", d.child("mName").getValue().toString());
                  }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               // sd.clear();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               // sd.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        /*databaseStudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                sd.clear();
                for(DataSnapshot subjectSnapshot : dataSnapshot.getChildren()){
                        /*StudentsData studentsData1=studentSnapshot.getValue(StudentsData.class);
                        sd.add(studentsData1);*/

                /*mAdapter=new AddStudentsAdapter(sd, getContext());
                recyclerView.setHasFixedSize(true);
                mLayoutManager=new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
                progressDialog.dismiss();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        databaseStudent.addValueEventListener/*addListenerForSingleValueEvent*/(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sd.clear();
                for(DataSnapshot teacherSnapshot : dataSnapshot.getChildren()){
                    for(DataSnapshot studentSnapshot : teacherSnapshot.getChildren()){
                        String mName=studentSnapshot.child("mName").getValue(String.class);
                        String mSubject=studentSnapshot.child("mSubject").getValue(String.class);
                        StudentsData studentsData=new StudentsData(mName,mSubject);
                        sd.add(studentsData);
                    }
                }
                mAdapter=new AddStudentsAdapter(sd, getContext());
                Log.d(getTag(),"It was here");
                recyclerView.invalidate();
              //  recyclerView.setHasFixedSize(true);
                mLayoutManager=new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);
               // mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return root;
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_add_student_data){
            addStudent();
        }
    }
    private void addStudent(){

        String etName=name.getText().toString().trim();
        String etSubject=subject.getText().toString().trim();
        if(TextUtils.isEmpty(etName)|| TextUtils.isEmpty(etSubject)){
            Toast.makeText(getContext(),"You Should Enter All Details",Toast.LENGTH_SHORT).show();
        }
        else{
            StudentsData studentsData=new StudentsData(etName,etSubject);
            databaseStudent= FirebaseDatabase.getInstance().getReference("teachers");
            mUser= FirebaseAuth.getInstance().getCurrentUser();
            String uniqueKey= FirebaseDatabase.getInstance().getReference().push().getKey();
            databaseStudent.child(etSubject).child(uniqueKey).setValue(studentsData);
            Toast.makeText(getContext(),"Data Added Successfully!",Toast.LENGTH_SHORT).show();
            mAdapter.notifyDataSetChanged();

        }
    }
/*
    @Override
    public void onStart() {
        super.onStart();
        databaseStudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sd.clear();
                for(DataSnapshot studentSnapshot:dataSnapshot.getChildren()){
                    StudentsData studData=studentSnapshot.getValue(StudentsData.class);
                    sd.add(studData);
                }
                recyclerView.setHasFixedSize(true);
                mLayoutManager=new LinearLayoutManager(getContext());
                mAdapter=new AddStudentsAdapter(sd);
                recyclerView.setLayoutManager(mLayoutManager);
                mAdapter=new AddStudentsAdapter(sd,getActivity());
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/
}
