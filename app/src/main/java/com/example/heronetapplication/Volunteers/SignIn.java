package com.example.heronetapplication.Volunteers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heronetapplication.NGO.NGOView;
import com.example.heronetapplication.NGO.SignUp;
import com.example.heronetapplication.ObjectTypes.NGO;
import com.example.heronetapplication.ObjectTypes.Users;
import com.example.heronetapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignIn#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignIn extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignIn() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignIn.
     */
    // TODO: Rename and change types and number of parameters
    public static SignIn newInstance(String param1, String param2) {
        SignIn fragment = new SignIn();
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


    TextInputEditText signInEmail, signInPassword;
    Button signInButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);
        signInEmail = v.findViewById(R.id.signInEmailEditText);
        signInPassword = v.findViewById(R.id.signInPasswordEditText);
        signInButton = v.findViewById(R.id.signInBtn);

        signInButton.setOnClickListener(this::SignInUser);


        TextView NGOSignInLink = v.findViewById(R.id.NGOSignInLink);

        NGOSignInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new com.example.heronetapplication.NGO.SignIn();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, fragment).commit();
            }
        });
        return v;
    }


    public void SignInUser(View v) {
        String email = signInEmail.getText().toString();
        String password = signInPassword.getText().toString();
        System.out.println(email + " " + password);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Users").whereEqualTo("Email", email).whereEqualTo("Password", password).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getDocuments().size() > 0) {
                    // user exists
                    // navigate to home feed
                    Users.id = task.getResult().getDocuments().get(0).getId();
                    Users.occupation = task.getResult().getDocuments().get(0).getString("occupation");
                    Users.email = task.getResult().getDocuments().get(0).getString("email");
                    Users.firstName = task.getResult().getDocuments().get(0).getString("FirstName");
                    Users.lastName = task.getResult().getDocuments().get(0).getString("LastName");

                    Fragment fragment = new VolunteerView();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, fragment).commit();
                } else {
                    // user does not exist
                    // show error toast
                    Toast.makeText(getContext(), "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}