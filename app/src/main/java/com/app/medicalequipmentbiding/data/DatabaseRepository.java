package com.app.medicalequipmentbiding.data;

import com.app.medicalequipmentbiding.data.models.Client;
import com.app.medicalequipmentbiding.data.models.Vendor;
import com.app.medicalequipmentbiding.datasource.FirebaseDataSource;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class DatabaseRepository {

    private final FirebaseDataSource firebaseDataSource;

    @Inject
    public DatabaseRepository(FirebaseDataSource firebaseDataSource) {
        this.firebaseDataSource = firebaseDataSource;
    }

    public Single<Client> loginAsClient(String email, String password) {
        return firebaseDataSource.loginAsClient(email, password);
    }

    public Single<Vendor> loginAsVendor(String email, String password) {
        return firebaseDataSource.loginAsVendor(email, password);
    }

    public Single<Client> registerClient(Client client) {
        return firebaseDataSource.registerClient(client);
    }

    public Single<Vendor> registerVendor(Vendor vendor) {
        return firebaseDataSource.registerVendor(vendor);
    }
}
