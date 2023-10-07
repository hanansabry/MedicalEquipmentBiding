package com.app.medicalequipmentbiding.presentation.client;

import com.app.medicalequipmentbiding.data.DatabaseRepository;
import com.app.medicalequipmentbiding.data.models.BidingOrder;
import com.app.medicalequipmentbiding.data.models.MedicalType;
import com.app.medicalequipmentbiding.data.models.Offer;
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
    private final MutableLiveData<List<BidingOrder>> ordersLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Offer>> orderOffersLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> addOrderStateLiveData = new MutableLiveData<>();
    private final MutableLiveData<BidingOrder> bidingOrderLiveData = new MutableLiveData<>();
    private final MutableLiveData<Offer> offerDetailsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> selectOfferStateLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> setRankStateLiveData = new MutableLiveData<>();

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

    public void retrieveClientBidingOrders(String clientId) {
        databaseRepository.retrieveClientBidingOrders(clientId)
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

    public void retrieveOrderOffers(String orderId) {
        databaseRepository.retrieveOrderOffers(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Offer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Offer> offers) {
                        orderOffersLiveData.setValue(offers);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }
                });
    }

    public void retrieveOfferDetails(String offerId) {
        databaseRepository.retrieveOfferDetails(offerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Offer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Offer offer) {
                        offerDetailsLiveData.setValue(offer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }
                });
    }

    public void selectOffer(String orderId, String offerId) {
        databaseRepository.selectOffer(orderId, offerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Boolean success) {
                        selectOfferStateLiveData.setValue(success);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }
                });
    }

    public void setVendorRank(String vendorId, String offerId, int rankValue) {
        databaseRepository.setVendorRank(vendorId, offerId, rankValue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Boolean success) {
                        setRankStateLiveData.setValue(success);
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

    public MutableLiveData<List<BidingOrder>> getOrdersLiveData() {
        return ordersLiveData;
    }

    public MutableLiveData<BidingOrder> getBidingOrderLiveData() {
        return bidingOrderLiveData;
    }

    public MutableLiveData<List<Offer>> getOrderOffersLiveData() {
        return orderOffersLiveData;
    }

    public MutableLiveData<Offer> getOfferDetailsLiveData() {
        return offerDetailsLiveData;
    }

    public MutableLiveData<Boolean> getSelectOfferStateLiveData() {
        return selectOfferStateLiveData;
    }

    public MutableLiveData<Boolean> getSetRankStateLiveData() {
        return setRankStateLiveData;
    }
}
