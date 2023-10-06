package com.app.medicalequipmentbiding.presentation.client;

import com.app.medicalequipmentbiding.data.DatabaseRepository;
import com.app.medicalequipmentbiding.data.models.BidingOrder;
import com.app.medicalequipmentbiding.data.models.MedicalType;
import com.app.medicalequipmentbiding.presentation.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ClientBidingViewModel extends BaseViewModel {

    private final MutableLiveData<List<MedicalType>> typesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> addOrderStateLiveData = new MutableLiveData<>();

    @Inject
    public ClientBidingViewModel(DatabaseRepository databaseRepository) {
        super(databaseRepository);
    }

    public void retrieveMedicalEquipmentTypes() {
        databaseRepository.retrieveMedicalEquipmentTypes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MedicalType>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<MedicalType> types) {
                        typesLiveData.setValue(types);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }
                });
    }

    public void addNewOrder(BidingOrder bidingOrder) {
        databaseRepository.addNewOrder(bidingOrder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Boolean success) {
                        addOrderStateLiveData.postValue(success);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }
                });
    }

    public MutableLiveData<List<MedicalType>> getTypesLiveData() {
        return typesLiveData;
    }

    public MutableLiveData<Boolean> getAddOrderStateLiveData() {
        return addOrderStateLiveData;
    }
}
