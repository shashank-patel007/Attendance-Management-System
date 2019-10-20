package com.shashank.attendancemanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentSettings extends Fragment implements View.OnClickListener {
    private EditText currentPassword,newPassword;
    private Button change;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_settings,container,false);
        currentPassword=view.findViewById(R.id.et_current_password);
        newPassword=view.findViewById(R.id.et_new_password);
        change=view.findViewById(R.id.btn_change_password);
        change.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View view) {
        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String email=user.getEmail();
        String password=currentPassword.getText().toString().trim();
        final String newPass=newPassword.getText().toString().trim();
        if(TextUtils.isEmpty(password) || TextUtils.isEmpty(newPass)){
            Toast.makeText(getContext(),"Please Enter the Details",Toast.LENGTH_SHORT).show();
        }
        else{
            AuthCredential credential = EmailAuthProvider
                    .getCredential(email, password);

// Prompt the user to re-provide their sign-in credentials
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(),"Password updated successfully!",Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(),"Password update Failed!",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getContext(),"Authentication failed!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
