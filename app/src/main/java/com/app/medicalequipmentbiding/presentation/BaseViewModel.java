package com.app.medicalequipmentbiding.presentation;

import com.app.medicalequipmentbiding.data.DatabaseRepository;

import javax.inject.Inject;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel {

    protected final DatabaseRepository databaseRepository;
    protected final CompositeDisposable disposable = new CompositeDisposable();
    protected final MutableLiveData<String> errorState = new MediatorLiveData<>();

    @Inject
    public BaseViewModel(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    public MutableLiveData<String> getErrorState() {
        return errorState;
    }

    public void dispose() {
        disposable.dispose();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        dispose();
    }
}
