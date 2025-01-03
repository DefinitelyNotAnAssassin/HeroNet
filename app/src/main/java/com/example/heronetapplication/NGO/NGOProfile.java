package com.example.heronetapplication.NGO;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.heronetapplication.ObjectTypes.NGO;
import com.example.heronetapplication.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NGOProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NGOProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NGOProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NGOProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static NGOProfile newInstance(String param1, String param2) {
        NGOProfile fragment = new NGOProfile();
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
    private TextInputEditText description, email, name, password;
    private MaterialButton editButton, saveButton;
    private TextView ngoName, ngoRole;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_ngo_profile, container, false);

        description = v.findViewById(R.id.ngoDescription);
        email = v.findViewById(R.id.ngoEmail);
        name = v.findViewById(R.id.ngoName);
        password = v.findViewById(R.id.ngoPassword);
        editButton = v.findViewById(R.id.profileNGOEditButton);
        saveButton = v.findViewById(R.id.profileNGOSaveButton);
        ngoName = v.findViewById(R.id.profileNGOName);
        ngoRole = v.findViewById(R.id.profileNGORole);


        ngoName.setText(NGO.NGOName);
        description.setText(NGO.NGODescription);
        password.setText(NGO.password);


        return v;
    }
}