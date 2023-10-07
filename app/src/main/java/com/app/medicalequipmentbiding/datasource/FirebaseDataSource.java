package com.app.medicalequipmentbiding.datasource;

import com.app.medicalequipmentbiding.data.models.BidingOrder;
import com.app.medicalequipmentbiding.data.models.Client;
import com.app.medicalequipmentbiding.data.models.Equipment;
import com.app.medicalequipmentbiding.data.models.EquipmentOffer;
import com.app.medicalequipmentbiding.data.models.MedicalType;
import com.app.medicalequipmentbiding.data.models.Offer;
import com.app.medicalequipmentbiding.data.models.Vendor;
import com.app.medicalequipmentbiding.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
            DatabaseReference clientsRef = firebaseDatabase.getReference(Constants.CLIENTS_NODE);
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String clientId = task.getResult().getUser().getUid();
                            clientsRef.child(clientId)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Client client = snapshot.getValue(Client.class);
                                            if (client != null) {
                                                client.setUserId(clientId);
                                                emitter.onSuccess(client);
                                            } else {
                                                emitter.onError(new Throwable("Invalid email or password"));
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            emitter.onError(error.toException());
                                        }
                                    });
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    public Single<Vendor> loginAsVendor(String email, String password) {
        return Single.create(emitter -> {
            DatabaseReference vendorsRef = firebaseDatabase.getReference(Constants.VENDORS_NODE);
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String vendorId = task.getResult().getUser().getUid();
                            vendorsRef.child(vendorId)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Vendor vendor = snapshot.getValue(Vendor.class);
                                            if (vendor != null) {
                                                vendor.setUserId(vendorId);
                                                emitter.onSuccess(vendor);
                                            } else {
                                                emitter.onError(new Throwable("Invalid email or password"));
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            emitter.onError(error.toException());
                                        }
                                    });
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    public Single<Client> registerClient(Client client) {
        return Single.create(emitter -> {
            DatabaseReference clientsRef = firebaseDatabase.getReference(Constants.CLIENTS_NODE);
            //create client with email and password
            firebaseAuth.createUserWithEmailAndPassword(client.getEmail(), client.getPassword())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String clientId = task.getResult().getUser().getUid();
                            client.setUserId(clientId);
                            clientsRef.child(clientId)
                                    .setValue(client)
                                    .addOnCompleteListener(saveTask -> {
                                        if (saveTask.isSuccessful()) {
                                            emitter.onSuccess(client);
                                        } else {
                                            emitter.onError(saveTask.getException());
                                        }
                                    });
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    public Single<Vendor> registerVendor(Vendor vendor) {
        return Single.create(emitter -> {
            DatabaseReference clientsRef = firebaseDatabase.getReference(Constants.VENDORS_NODE);
            //create client with email and password
            firebaseAuth.createUserWithEmailAndPassword(vendor.getEmail(), vendor.getPassword())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String vendorId = task.getResult().getUser().getUid();
                            vendor.setUserId(vendorId);
                            clientsRef.child(vendorId)
                                    .setValue(vendor)
                                    .addOnCompleteListener(saveTask -> {
                                        if (saveTask.isSuccessful()) {
                                            emitter.onSuccess(vendor);
                                        } else {
                                            emitter.onError(saveTask.getException());
                                        }
                                    });
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    public Single<List<MedicalType>> retrieveMedicalEquipmentTypes() {
        return Single.create(emitter -> {
            firebaseDatabase.getReference(Constants.TYPES_NODE).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<MedicalType> types = new ArrayList<>();
                    for (DataSnapshot typeSnapshot : snapshot.getChildren()) {
                        String name = typeSnapshot.child("name").getValue().toString();
                        List<String> items = new ArrayList<>();
                        if (typeSnapshot.hasChild("items")) {
                            for (DataSnapshot itemSnapshot : typeSnapshot.child("items").getChildren()) {
                                if (itemSnapshot.hasChild("name")) {
                                    items.add(itemSnapshot.child("name").getValue().toString());
                                }
                            }
                        }
                        MedicalType type = new MedicalType();
                        type.setTypeId(typeSnapshot.getKey());
                        type.setName(name);
                        type.setItems(items);
                        types.add(type);
                    }
                    emitter.onSuccess(types);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    emitter.onError(error.toException());
                }
            });
        });
    }

    public Single<Boolean> addNewOrder(BidingOrder bidingOrder) {
        return Single.create(emitter -> {
            firebaseDatabase.getReference(Constants.ORDERS_NODE)
                    .push()
                    .setValue(bidingOrder)
                    .addOnCompleteListener(saveTask -> {
                        if (saveTask.isSuccessful()) {
                            emitter.onSuccess(true);
                        } else {
                            emitter.onError(saveTask.getException());
                        }
                    });

        });
    }

    public Single<List<BidingOrder>> retrieveClientBidingOrders(String clientId) {
        return Single.create(emitter -> {
            Query ordersQuery = firebaseDatabase.getReference(Constants.ORDERS_NODE)
                    .orderByChild("clientId")
                    .equalTo(clientId);

            ordersQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<BidingOrder> orders = new ArrayList<>();
                    for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                        BidingOrder order = orderSnapshot.getValue(BidingOrder.class);
                        if (order != null) {
                            order.setOrderId(orderSnapshot.getKey());
                            orders.add(order);
                        }
                    }
                    emitter.onSuccess(orders);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    emitter.onError(error.toException());
                }
            });
        });
    }

    public Single<BidingOrder> retrieveOrderDetails(String orderId) {
        return Single.create(emitter -> {
            firebaseDatabase.getReference(Constants.ORDERS_NODE)
                    .child(orderId)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            BidingOrder order = snapshot.getValue(BidingOrder.class);
                            if (order != null) {
                                order.setOrderId(snapshot.getKey());
                                emitter.onSuccess(order);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            emitter.onError(error.toException());
                        }
                    });
        });
    }

    public Single<List<Offer>> retrieveOrderOffers(String orderId) {
        return Single.create(emitter -> {
            Query offersQuery = firebaseDatabase.getReference(Constants.OFFERS_NODE)
                    .orderByChild("orderId")
                    .equalTo(orderId);
            offersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<Offer> offerList = new ArrayList<>();
                    for (DataSnapshot offerSnapshot : snapshot.getChildren()) {
                        Offer offer = offerSnapshot.getValue(Offer.class);
                        if (offer != null) {
                            offer.setOfferId(offerSnapshot.getKey());
                            offerList.add(offer);
                        }
                    }
                    emitter.onSuccess(offerList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    emitter.onError(error.toException());
                }
            });
        });
    }

    public Single<Offer> retrieveOfferDetails(String offerId) {
        return Single.create(emitter -> {
            firebaseDatabase.getReference(Constants.OFFERS_NODE)
                    .child(offerId)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Offer offer = snapshot.getValue(Offer.class);
                            if (offer != null) {
                                offer.setOfferId(snapshot.getKey());
                                emitter.onSuccess(offer);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            emitter.onError(error.toException());
                        }
                    });
        });
    }

    public Single<List<BidingOrder>> retrieveActiveBiding() {
        return Single.create(emitter -> {
            firebaseDatabase.getReference(Constants.ORDERS_NODE)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ArrayList<BidingOrder> orders = new ArrayList<>();
                            for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                                BidingOrder order = orderSnapshot.getValue(BidingOrder.class);
                                if (order != null && order.getCloseDate() > System.currentTimeMillis()) {
                                    order.setOrderId(orderSnapshot.getKey());
                                    orders.add(order);
                                }
                            }
                            emitter.onSuccess(orders);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            emitter.onError(error.toException());
                        }
                    });
        });
    }

    public Single<Boolean> saveOffer(Offer offer) {
        return Single.create(emitter -> {
            firebaseDatabase.getReference(Constants.OFFERS_NODE)
                    .push()
                    .setValue(offer)
                    .addOnCompleteListener(saveTask -> {
                        if (saveTask.isSuccessful()) {
                            emitter.onSuccess(true);
                        } else {
                            emitter.onError(saveTask.getException());
                        }
                    });

        });
    }
}
