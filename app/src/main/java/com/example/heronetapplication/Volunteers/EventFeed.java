package com.example.heronetapplication.Volunteers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.heronetapplication.R;
import com.example.heronetapplication.adapters.EventFeedListViewAdapter;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventfeed, container, false);
        ListView eventFeedListView = view.findViewById(R.id.eventFeedListView);

        getEvents();

        EventFeedListViewAdapter eventFeedListViewAdapter = new EventFeedListViewAdapter(getContext(), events);
        eventFeedListView.setAdapter(eventFeedListViewAdapter);

        return view;
    }

public void getEvents() {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    db.collection("Events")
        .get()
        .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                events.clear(); // Clear the list to avoid duplicates
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Map<String, String> event = new HashMap<>();
                    event.put("name", document.getString("EventName"));
                    event.put("description", document.getString("EventDescription"));
                    event.put("location", document.getString("EventLocation"));
                    event.put("date", document.getString("EventDate"));
                    event.put("duration", document.getString("EventDuration"));
                    event.put("NGOName", document.getString("NGOName"));
                    event.put("id", document.getId());


                    events.add(event);
                }
                // Notify the adapter that the data has changed
                EventFeedListViewAdapter eventFeedListViewAdapter = new EventFeedListViewAdapter(getContext(), events);
                ListView eventFeedListView = getView().findViewById(R.id.eventFeedListView);
                eventFeedListView.setAdapter(eventFeedListViewAdapter);
            } else {
                Toast.makeText(getContext(), "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
}
    }