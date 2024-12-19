package com.example.heronetapplication.Volunteers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.heronetapplication.ObjectTypes.Users;
import com.example.heronetapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUp extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public SignUp() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUp.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUp newInstance(String param1, String param2) {
        SignUp fragment = new SignUp();
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


    TextInputEditText signUpFirstName, signUpLastName, signUpEmail, signUpPassword, signUpConfirmPassword, signUpOccupation;
    Button signUpBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_sign_up, container, false);

        signUpFirstName = v.findViewById(R.id.signUpFirstName);
        signUpLastName = v.findViewById(R.id.signUpLastName);
        signUpEmail = v.findViewById(R.id.signUpEmail);
        signUpPassword = v.findViewById(R.id.signUpPassword);
        signUpConfirmPassword = v.findViewById(R.id.signUpConfirmPassword);
        signUpOccupation = v.findViewById(R.id.signUpOccupation);

        signUpBtn = v.findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = signUpFirstName.getText().toString();
                String lastName = signUpLastName.getText().toString();
                String email = signUpEmail.getText().toString();
                String password = signUpPassword.getText().toString();
                String confirmPassword = signUpConfirmPassword.getText().toString();
                String occupation = signUpOccupation.getText().toString();
                signUpUser(firstName, lastName, email, password, confirmPassword, occupation, 5.0);



            }
        });


        return v;
    }


    public void signUpUser(String firstName, String lastName, String email, String password, String confirmPassword, String occupation, double rating){



        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || occupation.isEmpty()){
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)){
            Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        Users.firstName = firstName;
        Users.lastName = lastName;
        Users.email = email;
        Users.password = password;
        Users.occupation = occupation;
        Users.skillLevel = rating;



        db.collection("Users").add(Users.getUserInfo()).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(),"User created successfully", Toast.LENGTH_SHORT).show();
                        Fragment fragment = new SignIn();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, fragment).commit();

                    } else {
                        Toast.makeText(getContext(),"User creation failed", Toast.LENGTH_SHORT).show();
                    }
                }
        );



    }
}