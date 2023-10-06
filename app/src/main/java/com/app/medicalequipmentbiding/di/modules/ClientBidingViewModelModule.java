package com.app.medicalequipmentbiding.di.modules;

import com.app.medicalequipmentbiding.di.ViewModelKey;
import com.app.medicalequipmentbiding.presentation.authentication.AuthenticationViewModel;
import com.app.medicalequipmentbiding.presentation.client.ClientBidingViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ClientBidingViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ClientBidingViewModel.class)
    public abstract ViewModel bindViewModel(ClientBidingViewModel viewModel);
}
