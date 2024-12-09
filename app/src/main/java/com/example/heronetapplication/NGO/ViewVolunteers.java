package com.example.heronetapplication.NGO;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.heronetapplication.R;
import com.example.heronetapplication.adapters.ManageApplicantListViewAdapter;
import com.example.heronetapplication.adapters.ViewVolunteersListViewAdapter;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewVolunteers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewVolunteers extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewVolunteers() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewVolunteers.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewVolunteers newInstance(String param1, String param2) {
        ViewVolunteers fragment = new ViewVolunteers();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_volunteers, container, false);


        // Create a new instance of ManageApplicantListViewAdapter
        // and set it as the adapter for the ListView

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        Bundle bundle = getArguments();
        String eventId = bundle.getString("eventId");
        ArrayList<Map<String, Object>> users = new ArrayList<>();
        AtomicReference<ArrayList<String>> volunteers = new AtomicReference<>(new ArrayList<>());

        db.collection("Events").document(eventId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    volunteers.set((ArrayList) documentSnapshot.get("Volunteers"));
                    System.out.println(volunteers);

                    for (String volunteer : volunteers.get()) {
                        db.collection("Users").document(volunteer).get()
                                .addOnSuccessListener(ds -> {
                                    Map<String, Object> user = ds.getData();
                                    user.put("id", ds.getId());
                                    user.put("eventId", eventId);
                                    users.add(user);
                                    // Update the ListView adapter after adding each user
                                    ListView volunteersList = v.findViewById(R.id.volunteersList);
                                    ViewVolunteersListViewAdapter adapter = new ViewVolunteersListViewAdapter(getActivity(), users);
                                    volunteersList.setAdapter(adapter);
                                })
                                .addOnFailureListener(e -> {
                                    System.out.println("Failed to get user");
                                    System.out.println("User ID: " + volunteer);
                                    System.out.println(e.getMessage());
                                });
                    }
                    System.out.println("Users: " + users);
                })
                .addOnFailureListener(e -> {
                    System.out.println("Failed to get event");
                    System.out.println("Event ID: " + eventId);
                    System.out.println(e.getMessage());
                });


        return v;
    }
}