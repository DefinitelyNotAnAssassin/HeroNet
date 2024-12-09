package com.example.heronetapplication.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.heronetapplication.NGO.ManageApplicants;
import com.example.heronetapplication.NGO.ViewVolunteers;
import com.example.heronetapplication.R;

import java.util.List;
import java.util.Map;

public class NGOEventFeedListViewAdapter extends BaseAdapter {
    private Fragment fragment;
    private List<Map<String, String>> events;

    public NGOEventFeedListViewAdapter(Fragment fragment, List<Map<String, String>> events) {
        this.fragment = fragment;
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
            convertView = LayoutInflater.from(fragment.getContext()).inflate(R.layout.adapters_ngo_event_feed_item_list, parent, false);
        }

        Map<String, String> event = events.get(position);
        TextView eventName = convertView.findViewById(R.id.ngoEventName);
        TextView eventDate = convertView.findViewById(R.id.ngoEventDate);
        eventName.setText(event.get("eventName"));
        eventDate.setText(event.get("eventDate"));
        String eventId = event.get("eventID");

        Button viewVolunteersBtn = convertView.findViewById(R.id.viewVolunteersBtn);
        Button manageApplicantsBtn = convertView.findViewById(R.id.manageApplicantsBtn);
        manageApplicantsBtn.setOnClickListener(v -> manageApplicants(eventId));

        viewVolunteersBtn.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("eventId", eventId);
            ViewVolunteers viewVolunteers = new ViewVolunteers();
            viewVolunteers.setArguments(bundle);
            FragmentTransaction transaction = fragment.getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, viewVolunteers);
            transaction.commit();
        });
        return convertView;
    }

    public void manageApplicants(String eventId) {
        Bundle bundle = new Bundle();
        bundle.putString("eventId", eventId);
        ManageApplicants manageApplicantsFragment = new ManageApplicants();
        manageApplicantsFragment.setArguments(bundle);
        FragmentTransaction transaction = fragment.getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, manageApplicantsFragment);
        transaction.commit();
    }

}