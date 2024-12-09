package com.example.heronetapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.heronetapplication.ObjectTypes.Users;
import com.example.heronetapplication.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventFeedListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, String>> events;

    public EventFeedListViewAdapter(Context context, List<Map<String, String>> events) {
        this.context = context;
        this.events = events;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapters_event_feed_item_list, parent, false);
        }

        Map<String, String> event = events.get(position);
        TextView eventName = convertView.findViewById(R.id.listViewEventName);
        TextView eventDate = convertView.findViewById(R.id.listViewEventDate);
        eventName.setText(event.get("name"));
        eventDate.setText(event.get("date"));

        Button registerButton = convertView.findViewById(R.id.registerBtn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               registerForEvent(event.get("id"), Users.id);
                System.out.println("Registering for event");
            }
        });

        return convertView;
    }


     public void registerForEvent(String eventId, String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
         System.out.println("Ping");
        db.collection("Events").document(eventId).get()
            .addOnSuccessListener(documentSnapshot -> {
                ArrayList applicants = (ArrayList) documentSnapshot.get("Applicants");
                applicants.add(userId);
                db.collection("Events").document(eventId).update("Applicants", applicants);
            })
            .addOnFailureListener(e -> {
                System.out.println("Failed to get event");
                System.out.println("Event ID: " + eventId);
                System.out.println(e.getMessage());
            });
         System.out.println("Event ID: " + eventId);
         System.out.println("Pong");
    }
}