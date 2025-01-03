package com.example.heronetapplication.NGO;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heronetapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NGOSignUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NGOSignUp extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NGOSignUp() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NGOSignUp.
     */
    // TODO: Rename and change types and number of parameters
    public static NGOSignUp newInstance(String param1, String param2) {
        NGOSignUp fragment = new NGOSignUp();
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


    TextView loginLink;
    TextInputEditText signUpOrganizationName, signUpOrganizationDescription, signUpOrganizationEmail, signUpOrganizationPassword;
    Button signUpButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_ngo_signup, container, false);

        loginLink = v.findViewById(R.id.loginLink);
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SignIn();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        signUpOrganizationName = v.findViewById(R.id.signUpOrganizationName);
        signUpOrganizationDescription = v.findViewById(R.id.signUpOrganizationDescription);
        signUpOrganizationEmail = v.findViewById(R.id.signUpOrganizationEmail);
        signUpOrganizationPassword = v.findViewById(R.id.signUpOrganizationPassword);

        signUpButton = v.findViewById(R.id.ngoSignUpBtn);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpOrganization(signUpOrganizationName.getText().toString(), signUpOrganizationDescription.getText().toString(), signUpOrganizationEmail.getText().toString(), signUpOrganizationPassword.getText().toString());
            }
        });



        return v;
    }


    public void signUpOrganization(String name, String description, String email, String password){
        // Create a new organization
        // Add the organization to the database
        // Redirect to the organization's home feed

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, String> organization = Map.of("Name", name, "Description", description, "Email", email, "Password", password);
        db.collection("NGOs").add(organization).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(getContext(), "Organization created successfully", Toast.LENGTH_SHORT).show();
                Fragment fragment = new SignIn();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainFragmentContainer, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}