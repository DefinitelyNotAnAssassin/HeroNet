package com.example.heronetapplication.NGO;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.heronetapplication.ObjectTypes.NGO;
import com.example.heronetapplication.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEvent extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEvent newInstance(String param1, String param2) {
        CreateEvent fragment = new CreateEvent();
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

    TextInputEditText eventName, eventDescription, eventLocation, eventDate, eventDuration;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_create_event, container, false);

        Button submitEventButton = v.findViewById(R.id.submitEventButton);
        eventName = v.findViewById(R.id.eventName);
        eventDescription = v.findViewById(R.id.eventDescription);
        eventLocation = v.findViewById(R.id.eventLocation);
        eventDate = v.findViewById(R.id.eventDate);
        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });        eventDuration = v.findViewById(R.id.eventDuration);

        submitEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEvent(eventName.getText().toString(), eventDescription.getText().toString(), eventLocation.getText().toString(), eventDate.getText().toString(), eventDuration.getText().toString());
                eventName.setText("");
                eventDescription.setText("");
                eventLocation.setText("");
                eventDate.setText("");
                eventDuration.setText("");

            }
        });
        return v;
    }

    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Event Date")
                .build();

        datePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String date = sdf.format(new Date(selection));
            eventDate.setText(date);
        });
    }



    public void createEvent(String eventName, String eventDescription, String eventLocation, String eventDate, String eventDuration) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> event = Map.of(
            "EventName", eventName,
            "EventDescription", eventDescription,
            "EventLocation", eventLocation,
            "EventDate", eventDate,
            "EventDuration", eventDuration,
            "NGOId", NGO.NGOId,
            "NGOName", NGO.NGOName,
            "Volunteers", new ArrayList<>(),
            "Applicants", new ArrayList<>()
        );
        db.collection("Events").add(event).addOnCompleteListener(
            task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(),"Event created successfully", Toast.LENGTH_SHORT).show();

                } else {
                    // Event creation failed
                }
            }
        );


    }
}