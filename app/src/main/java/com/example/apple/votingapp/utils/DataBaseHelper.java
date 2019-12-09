package com.example.apple.votingapp.utils;

import android.support.annotation.NonNull;

import com.example.apple.votingapp.classes.Policy;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

/**
 * DataBaseHelper is a utility class for all things related
 * to the Firebase Realtime Database such as :
 *
 * <ul>
 * <li> Loading data
 * <li> Inserting data
 * <li> Deleting data
 * <li> Updating data
 * </ul>
 *
 * @author      Rayyan Nasr
 * @author      Jihad Eddine Al Khrufan
 * @version     %I%, %G%
 * @since       1.0
 */
public class DataBaseHelper {

    /**
     *  Firebase Database reference
     */
    private FirebaseDatabase fDB;

    /**
     *  Database reference
     */
    private DatabaseReference dbReference;

    /**
     *  List of policies
     */
    private List<Policy> policies = new ArrayList<>();

    /**
     * The interface Data status.
     */
    public interface DataStatus {
        /**
         * Data is loaded.
         *
         * @param p    the p
         * @param keys the keys
         */
        void DataIsLoaded(List<Policy> p, List<String> keys);

        /**
         * Data is inserted.
         */
        void DataIsInserted();

        /**
         * Data is deleted.
         */
        void DataIsDeleted();

        /**
         * Data is updated.
         */
        void DataIsUpdated();
    }

    /**
     * Instantiates a new Data base helper.
     */
    public DataBaseHelper() {
        fDB = FirebaseDatabase.getInstance();
        dbReference = fDB.getReference("policies");
    }

    /**
     * Read policies.
     *
     * @param dataStatus the data status
     */
    public void readPolicies(final DataStatus dataStatus) {
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                policies.clear();
                List<String> keys = new ArrayList<>();

                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
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

    /**
     * Update policy.
     *
     * @param key        the key
     * @param policy     the policy
     * @param dataStatus the data status
     */
    public void updatePolicy(String key, Policy policy, final DataStatus dataStatus) {
        dbReference.child(key).setValue(policy).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }
}
