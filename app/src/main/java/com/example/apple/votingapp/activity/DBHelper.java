package com.example.apple.votingapp.activity;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    private FirebaseDatabase fDB;
    private DatabaseReference dbReference;
    private List<Policy> policies = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Policy> p, List<String> keys);
        void DataIsInserted();
        void DataIsDeleted();
        void DataIsUpdated();
    }

    public DBHelper() {
        fDB = FirebaseDatabase.getInstance();
        dbReference = fDB.getReference("policies");
    }

    public void readPolicies(final DataStatus dataStatus){
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                policies.clear();
                List<String> keys = new ArrayList<>();

                for(DataSnapshot keyNode : dataSnapshot.getChildren())
                {
                    keys.add(keyNode.getKey());
                    Policy p = keyNode.getValue(Policy.class);
                    policies.add(p);
                }

                dataStatus.DataIsLoaded(policies, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
