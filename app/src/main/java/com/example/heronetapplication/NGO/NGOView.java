package com.example.heronetapplication.NGO;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.example.heronetapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NGOView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NGOView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NGOView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VolunteerView.
     */
    // TODO: Rename and change types and number of parameters
    public static NGOView newInstance(String param1, String param2) {
        NGOView fragment = new NGOView();
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


    FragmentContainerView fragmentContainer;
    BottomNavigationView bottomNavigationView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_ngo_view, container, false);
        fragmentContainer = v.findViewById(R.id.fragment_container);
        Fragment fragment = new HomeFeed();
        getChildFragmentManager().beginTransaction().replace(fragmentContainer.getId(), fragment).commit();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        bottomNavigationView = v.findViewById(R.id.ngo_navigation);

        bottomNavigationView.setOnItemSelectedListener(

                item -> {
                    Fragment selectedFragment = null;
                    int itemId = item.getItemId();
                    if (itemId == R.id.ngoHomeTab) {
                        selectedFragment = new NGOHomeFeed();
                    } else if (itemId == R.id.ngoEventsTab) {
                        selectedFragment = new EventFeed();
                    }
                    else if (itemId == R.id.ngoCreateEventTab){
                        selectedFragment = new CreateEvent();
                    }
                    else if (itemId == R.id.ngoProfileTab){
                        selectedFragment = new NGOProfile();
                    }
                    getChildFragmentManager().beginTransaction().replace(fragmentContainer.getId(), selectedFragment).commit();
                    return true;
                }
        );


        return v;
    }
}