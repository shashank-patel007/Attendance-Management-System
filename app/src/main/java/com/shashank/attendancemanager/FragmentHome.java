package com.shashank.attendancemanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentHome extends Fragment implements View.OnClickListener {
    private CardView attendance,edit,students,settings;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home,container,false);
        attendance=view.findViewById(R.id.card_attendance);
        edit=view.findViewById(R.id.card_edit);
        students=view.findViewById(R.id.card_add_students);
        settings=view.findViewById(R.id.card_settings);
        attendance.setOnClickListener(this);
        edit.setOnClickListener(this);
        students.setOnClickListener(this);
        settings.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        switch(view.getId()){
            case R.id.card_attendance:
                transaction.replace(R.id.fragment_container,new FragmentTakeAttendance() );
                transaction.commit();
                break;
            case R.id.card_edit:
               // FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,new FragmentEdit() );
                transaction.commit();
                break;
            case R.id.card_settings:
                transaction.replace(R.id.fragment_container,new FragmentSettings() );
                transaction.commit();
                break;
            case R.id.card_add_students:
                transaction.replace(R.id.fragment_container,new FragmentAddStudents() );
                transaction.commit();
                break;
        }
    }
    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}
