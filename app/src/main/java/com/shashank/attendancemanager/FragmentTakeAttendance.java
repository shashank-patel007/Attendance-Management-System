package com.shashank.attendancemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class FragmentTakeAttendance extends Fragment implements View.OnClickListener {
    private CardView ADS;
    private CardView OOP;
    private CardView DSGT;
    private CardView DLDA;
    private CardView Maths;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_take_attendance,container,false);
        ADS=root.findViewById(R.id.card_ads);
        OOP=root.findViewById(R.id.card_oop);
        DSGT=root.findViewById(R.id.card_dsgt);
        DLDA=root.findViewById(R.id.card_dlda);
        Maths=root.findViewById(R.id.card_maths);
        ADS.setOnClickListener(this);
        OOP.setOnClickListener(this);
        DSGT.setOnClickListener(this);
        DLDA.setOnClickListener(this);
        Maths.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.card_ads:
                Intent intent = new Intent(getActivity(), AdsAttendance.class);
                startActivity(intent);
            break;
        }

    }
}
