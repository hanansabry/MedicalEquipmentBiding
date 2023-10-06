package com.app.medicalequipmentbiding.data;

import com.app.medicalequipmentbiding.data.models.BidingOrder;
import com.app.medicalequipmentbiding.data.models.Client;
import com.app.medicalequipmentbiding.data.models.MedicalType;
import com.app.medicalequipmentbiding.data.models.Offer;
import com.app.medicalequipmentbiding.data.models.Vendor;
import com.app.medicalequipmentbiding.datasource.FirebaseDataSource;

import java.util.List;

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

    public Single<List<MedicalType>> retrieveMedicalEquipmentTypes() {
        return firebaseDataSource.retrieveMedicalEquipmentTypes();
    }

    public Single<Boolean> addNewOrder(BidingOrder bidingOrder) {
        return firebaseDataSource.addNewOrder(bidingOrder);
    }

    public Single<List<BidingOrder>> retrieveClientBidingOrders(String clientId) {
        return firebaseDataSource.retrieveClientBidingOrders(clientId);
    }

    public Single<BidingOrder> retrieveOrderDetails(String orderId) {
        return firebaseDataSource.retrieveOrderDetails(orderId);
    }

    public Single<List<Offer>> retrieveOrderOffers(String orderId) {
        return firebaseDataSource.retrieveOrderOffers(orderId);
    }

    public Single<Offer> retrieveOfferDetails(String offerId) {
        return firebaseDataSource.retrieveOfferDetails(offerId);
    }

    public Single<List<BidingOrder>> retrieveActiveBiding() {
        return firebaseDataSource.retrieveActiveBiding();
    }
}
