package com.app.medicalequipmentbiding.presentation.authentication;

import com.app.medicalequipmentbiding.R;
import com.app.medicalequipmentbiding.data.DatabaseRepository;
import com.app.medicalequipmentbiding.data.models.Client;
import com.app.medicalequipmentbiding.data.models.Vendor;
import com.app.medicalequipmentbiding.presentation.BaseViewModel;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AuthenticationViewModel extends BaseViewModel {

    private final MutableLiveData<Client> clientLiveData = new MutableLiveData<>();
    private final MutableLiveData<Vendor> vendorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> validationState = new MutableLiveData<>();

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

    public MutableLiveData<Integer> getValidationState() {
        return validationState;
    }

    public boolean validateRegister(String organizationName,
                                    String email,
                                    String password,
                                    String rePassword,
                                    String phone,
                                    String approveNum,
                                    String accountType) {
        if (organizationName.isEmpty() || email.isEmpty() || password.isEmpty()
                || rePassword.isEmpty() || phone.isEmpty() || approveNum.isEmpty() || accountType.isEmpty()) {
            validationState.setValue(R.string.all_fields_required);
            return false;
        } else if (!password.equals(rePassword)) {
            validationState.setValue(R.string.password_not_match_error);
            return false;
        } else {
            return true;
        }
    }

}
