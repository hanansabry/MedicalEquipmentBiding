package com.app.medicalequipmentbiding.datasource;

import com.app.medicalequipmentbiding.data.DatabaseRepository;
import com.app.medicalequipmentbiding.data.models.Client;
import com.app.medicalequipmentbiding.data.models.Vendor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public class FirebaseDataSource {

    private final FirebaseDatabase firebaseDatabase;
    private final FirebaseAuth firebaseAuth;

    @Inject
    public FirebaseDataSource(FirebaseDatabase firebaseDatabase, FirebaseAuth firebaseAuth) {
        this.firebaseDatabase = firebaseDatabase;
        this.firebaseAuth = firebaseAuth;
    }

    public Single<Client> loginAsClient(String email, String password) {
        return Single.create(emitter -> {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onSuccess(new Client());
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    public Single<Vendor> loginAsVendor(String email, String password) {
        return Single.create(emitter -> {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onSuccess(new Vendor());
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    public Single<Client> registerClient(Client client) {
        return null;
    }

    public Single<Vendor> registerVendor(Vendor vendor) {
        return null;
    }
}
