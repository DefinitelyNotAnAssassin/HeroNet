package com.example.heronetapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.example.heronetapplication.Volunteers.SignIn;
import com.example.heronetapplication.Volunteers.SignUp;
import com.example.heronetapplication.Volunteers.VolunteerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set main fragment container
        FragmentContainerView mainFragmentContainer = findViewById(R.id.mainFragmentContainer);
        Fragment fragment = new SignIn();
        getSupportFragmentManager().beginTransaction().replace(mainFragmentContainer.getId(), fragment).commit();


    }
}