package com.policarp.journal.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.policarp.journal.models.Mark;
import com.policarp.journal.models.School;
import com.policarp.journal.models.SchoolParticipant;

import java.util.ArrayList;

public class DataBaseAccess {
    public static final String DBTAG = "DATABASE";
    public static final String SCHOOLS = "schools";
    public static final String PARTICIPANTS = "participants";
    private final FirebaseFirestore db;
    public DataBaseAccess() {
        db = FirebaseFirestore.getInstance();
    }
    public void addSchoolParticipant(School school, SchoolParticipant participant){
        db.collection(SCHOOLS)
                .document(school.Name)
                .collection(PARTICIPANTS)
                .add(participant)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(DBTAG,
                                "Participant " + participant.FullName + " written with ID: " + documentReference.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(DBTAG, "Failed to write participant " + participant.FullName + " Msg: " + e.getMessage());
                    }
                });
    }
    public ArrayList<SchoolParticipant> getParticipants(School school) throws Exception {
        ArrayList<SchoolParticipant> res = new ArrayList<>();
        final Exception[] ex = {null};
        db.collection(SCHOOLS)
                .document(school.Name)
                .collection(PARTICIPANTS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot doc : task.getResult()){
                                res.add(doc.toObject(SchoolParticipant.class));
                            }
                        }
                        else{
                            ex[0] = task.getException();
                        }
                    }
                });
        if(ex[0] != null)
            throw ex[0];
        return res;
    }
}
