package com.app.medicalequipmentbiding.di.modules;

import com.app.medicalequipmentbiding.di.ViewModelKey;
import com.app.medicalequipmentbiding.presentation.authentication.AuthenticationViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthenticationViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthenticationViewModel.class)
    public abstract ViewModel bindViewModel(AuthenticationViewModel viewModel);
}
