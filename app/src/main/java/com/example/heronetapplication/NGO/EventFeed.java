package com.example.heronetapplication.NGO;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.example.heronetapplication.ObjectTypes.NGO;
import com.example.heronetapplication.R;
import com.example.heronetapplication.adapters.EventFeedListViewAdapter;
import com.example.heronetapplication.adapters.NGOEventFeedListViewAdapter;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 */
public class EventFeed extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    List<Map<String, String>> events = new ArrayList<>();


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventFeed() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EventFeed newInstance(int columnCount) {
        EventFeed fragment = new EventFeed();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    ListView eventFeedListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventfeed, container, false);
        eventFeedListView = view.findViewById(R.id.eventFeedListView);

        getEvents();





        return view;
    }

public void getEvents() {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String id = "vPplP3Sxp0bGWQgZx3u0";

    // filter events by NGO id
    db.collection("Events")
            .whereEqualTo("NGOId", id)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, String> event = new HashMap<>();
                        event.put("eventName", document.getString("EventName"));
                        event.put("eventDescription", document.getString("EventDescription"));
                        event.put("eventLocation", document.getString("EventLocation"));
                        event.put("eventDate", document.getString("EventDate"));
                        event.put("eventDuration", document.getString("EventDuration"));
                        event.put("eventID", document.getId());
                        events.add(event);

                    }
                    System.out.println(events);

                    NGOEventFeedListViewAdapter adapter = new NGOEventFeedListViewAdapter(this, events);
                    eventFeedListView.setAdapter(adapter);
                } else {

                    Toast.makeText(getContext(), "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
            );



}
    }