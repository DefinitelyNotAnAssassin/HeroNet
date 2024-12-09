package com.example.heronetapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heronetapplication.ObjectTypes.Users;
import com.example.heronetapplication.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManageApplicantListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> users;

    public ManageApplicantListViewAdapter(Context context, ArrayList<Map<String, Object>> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapters_applicants_item_list, parent, false);
        }

        Map<String, Object> event = users.get(position);
        TextView applicantsListFullName = convertView.findViewById(R.id.applicantsListFullName);
        TextView applicantsListOccupation = convertView.findViewById(R.id.applicantsListOccupation);
        TextView applicantsSkillLevel = convertView.findViewById(R.id.applicantsListSkillLevel);
        Button approveBtn = convertView.findViewById(R.id.approveBtn);
        Button rejectBtn = convertView.findViewById(R.id.rejectBtn);


        applicantsListFullName.setText(event.get("FirstName").toString() + " " + event.get("LastName").toString());
        applicantsListOccupation.setText(event.get("Occupation").toString());
        applicantsSkillLevel.setText(event.get("SkillLevel").toString());

        approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveApplicant(event.get("id").toString(), event.get("eventId").toString());
                    }
        });

        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Rejecting applicant");
            }
        });





        return convertView;
    }



    public void approveApplicant(String userId, String eventId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Events").document(eventId).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                List<String> applicants = (List<String>) task.getResult().get("Applicants");
                applicants.remove(userId);
                db.collection("Events").document(eventId).update("Applicants", applicants);

                // get Volunteers and update the event
                db.collection("Events").document(eventId).get().addOnCompleteListener(
                        task1 -> {
                            if(task1.isSuccessful()){
                                List<String> volunteers = (List<String>) task1.getResult().get("Volunteers");
                                volunteers.add(userId);
                                db.collection("Events").document(eventId).update("Volunteers", volunteers);
                                Toast.makeText(context, "Applicant Approved", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
    }


}