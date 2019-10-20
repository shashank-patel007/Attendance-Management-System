package com.shashank.attendancemanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
private Button SignOut;
private DrawerLayout drawer;
    private boolean doubleBackToExitPressedOnce=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FragmentHome()).commit();
        }
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        String userName=user.getEmail();
        View Headerview=navigationView.getHeaderView(0);
        TextView tvEmail=(TextView) Headerview.findViewById(R.id.tv_email_drawer);
        tvEmail.setText(userName);

        /*Intent intent=getIntent();
        String email=intent.getStringExtra(MainActivity.EXTRA_TEXT);
        tvEmail.setText(email);*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FragmentHome()).commit();
                break;
            case R.id.nav_add_students:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FragmentAddStudents()).commit();
                break;
           /* case R.id.nav_add_subjects:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FragmentAddSubjects()).commit();
                break;*/
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FragmentSettings()).commit();
                break;
            case R.id.nav_take_attendance:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FragmentTakeAttendance()).commit();
                break;
            case R.id.nav_edit:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FragmentEdit()).commit();
                break;
            case R.id.nav_sign_out:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(Dashboard.this,MainActivity.class));
                break;

        }
        drawer.closeDrawer(GravityCompat.START);

        return false;
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
       /* else {
            super.onBackPressed();
        }*/
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this,"Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            return;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
