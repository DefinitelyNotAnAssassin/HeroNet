package com.example.heronetapplication.NGO;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.heronetapplication.ObjectTypes.NGO;
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

    TextInputEditText email, password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_ngo_sign_in, container, false);
        Button signUpBtn = v.findViewById(R.id.ngoSignInBtn);
        email = v.findViewById(R.id.ngoSignInEmailEditText);
        password = v.findViewById(R.id.ngoSignInPasswordEditText);


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser(email.getText().toString(), password.getText().toString());
            }
        });





        return v;
    }

    public void signInUser(String email, String password){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("NGOs").whereEqualTo("email", email).whereEqualTo("password", password).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                if (task.getResult().getDocuments().size() > 0) {
                    // user exists
                    // navigate to home feed
                    NGO.email = email;
                    NGO.NGODescription = task.getResult().getDocuments().get(0).getString("description");
                    NGO.NGOName = task.getResult().getDocuments().get(0).getString("name");
                    NGO.NGOId = task.getResult().getDocuments().get(0).getId();
                    Fragment fragment = new NGOView();
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