package com.app.medicalequipmentbiding.presentation.authentication;

import com.app.medicalequipmentbiding.data.DatabaseRepository;
import com.app.medicalequipmentbiding.data.models.Client;
import com.app.medicalequipmentbiding.data.models.Vendor;
import com.app.medicalequipmentbiding.presentation.BaseViewModel;

import javax.inject.Inject;

import androidx.core.util.Pair;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AuthenticationViewModel extends BaseViewModel {

    private final MutableLiveData<Client> clientLiveData = new MutableLiveData<>();
    private final MutableLiveData<Vendor> vendorLiveData = new MutableLiveData<>();

    @Inject
    public AuthenticationViewModel(DatabaseRepository databaseRepository) {
        super(databaseRepository);
    }

    public void loginAsClient(String email, String password) {
        databaseRepository.loginAsClient(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Client>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Client client) {
                        // Emit the Employee and Farm objects to the corresponding LiveData
                        clientLiveData.postValue(client);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }
                });
    }

    public void loginAsVendor(String email, String password) {
        databaseRepository.loginAsVendor(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Vendor>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Vendor vendor) {
                        // Emit the Employee and Farm objects to the corresponding LiveData
                        vendorLiveData.postValue(vendor);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }
                });
    }

    public void registerClient(Client client) {
        databaseRepository.registerClient(client)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Client>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Client registeredClient) {
                        clientLiveData.postValue(registeredClient);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }
                });
    }

    public void registerVendor(Vendor vendor) {
        databaseRepository.registerVendor(vendor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Vendor>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Vendor registeredVendor) {
                        vendorLiveData.postValue(registeredVendor);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }
                });
    }

    public MutableLiveData<Client> getClientLiveData() {
        return clientLiveData;
    }

    public MutableLiveData<Vendor> getVendorLiveData() {
        return vendorLiveData;
    }
}
