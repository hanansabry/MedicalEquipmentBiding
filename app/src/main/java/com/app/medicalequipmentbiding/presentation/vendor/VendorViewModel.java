package com.app.medicalequipmentbiding.presentation.vendor;

import com.app.medicalequipmentbiding.data.DatabaseRepository;
import com.app.medicalequipmentbiding.data.models.BidingOrder;
import com.app.medicalequipmentbiding.presentation.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VendorViewModel extends BaseViewModel {

    private final MutableLiveData<List<BidingOrder>> ordersLiveData = new MutableLiveData<>();

    @Inject
    public VendorViewModel(DatabaseRepository databaseRepository) {
        super(databaseRepository);
    }

    public void retrieveActiveBiding() {
        databaseRepository.retrieveActiveBiding()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<BidingOrder>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<BidingOrder> orders) {
                        ordersLiveData.setValue(orders);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }
                });
    }

    public MutableLiveData<List<BidingOrder>> getOrdersLiveData() {
        return ordersLiveData;
    }
}
