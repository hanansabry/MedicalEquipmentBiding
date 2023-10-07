package com.app.medicalequipmentbiding.di.modules;

import com.app.medicalequipmentbiding.di.ViewModelKey;
import com.app.medicalequipmentbiding.presentation.AdminViewModel;
import com.app.medicalequipmentbiding.presentation.vendor.VendorViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AdminViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AdminViewModel.class)
    public abstract ViewModel bindViewModel(AdminViewModel viewModel);
}
