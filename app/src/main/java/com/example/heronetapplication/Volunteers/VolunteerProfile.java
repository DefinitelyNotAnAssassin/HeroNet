package com.example.heronetapplication.Volunteers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.heronetapplication.ObjectTypes.Users;
import com.example.heronetapplication.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VolunteerProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VolunteerProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VolunteerProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VolunteerProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static VolunteerProfile newInstance(String param1, String param2) {
        VolunteerProfile fragment = new VolunteerProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private TextInputEditText firstName, lastName, email, occupation;
    private MaterialButton editButton, saveButton;
    private TextView volunteerName, volunteerOccupation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_volunteer_profile, container, false);

        volunteerName = v.findViewById(R.id.profileVolunteerName);
        volunteerOccupation = v.findViewById(R.id.profileVolunteerOccupation);

        volunteerName.setText(Users.firstName + " " + Users.lastName);
        volunteerOccupation.setText(Users.occupation);

        firstName = v.findViewById(R.id.profileFirstName);
        lastName = v.findViewById(R.id.profileLastName);
        email = v.findViewById(R.id.profileEmail);
        occupation = v.findViewById(R.id.profileOccupation);
        editButton = v.findViewById(R.id.profileEditButton);
        saveButton = v.findViewById(R.id.profileSaveButton);

        loadUserData();

        editButton.setOnClickListener(s -> enableEditing(true));
        saveButton.setOnClickListener(s -> saveUserData());

        return v;
    }

    private void loadUserData() {
        firstName.setText(Users.firstName);
        lastName.setText(Users.lastName);
        email.setText(Users.email);
        occupation.setText(Users.occupation);

        enableEditing(false);
    }


    private void enableEditing(boolean enable) {
        firstName.setEnabled(enable);
        lastName.setEnabled(enable);
        email.setEnabled(enable);
        occupation.setEnabled(enable);

        editButton.setVisibility(enable ? View.GONE : View.VISIBLE);
        saveButton.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    private void saveUserData() {
        Users.firstName = firstName.getText().toString();
        Users.lastName = lastName.getText().toString();
        Users.email = email.getText().toString();
        Users.occupation = occupation.getText().toString();


        enableEditing(false);
    }

}