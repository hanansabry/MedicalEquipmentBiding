package com.app.medicalequipmentbiding.presentation.vendor;

import com.app.medicalequipmentbiding.data.DatabaseRepository;
import com.app.medicalequipmentbiding.data.models.BidingOrder;
import com.app.medicalequipmentbiding.data.models.Offer;
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
    private final MutableLiveData<BidingOrder> bidingOrderLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> offerStateLiveData = new MutableLiveData<>();

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

    public void retrieveOrderDetails(String orderId) {
        databaseRepository.retrieveOrderDetails(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<BidingOrder>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(BidingOrder order) {
                        bidingOrderLiveData.setValue(order);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }
                });
    }

    public void saveOffer(Offer offer) {
        databaseRepository.saveOffer(offer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Boolean success) {
                        offerStateLiveData.postValue(success);
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

    public MutableLiveData<BidingOrder> getBidingOrderLiveData() {
        return bidingOrderLiveData;
    }

    public MutableLiveData<Boolean> getOfferStateLiveData() {
        return offerStateLiveData;
    }
}
