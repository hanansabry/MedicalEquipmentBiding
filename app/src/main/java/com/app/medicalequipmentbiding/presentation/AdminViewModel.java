package com.app.medicalequipmentbiding.presentation;

import com.app.medicalequipmentbiding.data.DatabaseRepository;
import com.app.medicalequipmentbiding.data.models.VendorOfferHistory;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AdminViewModel extends BaseViewModel{

    private final MutableLiveData<Double> totalSalesLiveData = new MutableLiveData<>();

    @Inject
    public AdminViewModel(DatabaseRepository databaseRepository) {
        super(databaseRepository);
    }

    public void retrieveTotalOrdersSales() {
        databaseRepository.retrieveTotalOrdersSales()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Double>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Double totalSales) {
                        totalSalesLiveData.setValue(totalSales);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }
                });
    }

    public MutableLiveData<Double> getTotalSalesLiveData() {
        return totalSalesLiveData;
    }
}
