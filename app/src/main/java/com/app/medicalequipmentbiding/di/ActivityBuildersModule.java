package com.app.medicalequipmentbiding.di;



import com.app.medicalequipmentbiding.presentation.BaseActivity;
import com.app.medicalequipmentbiding.presentation.SplashActivity;
import com.app.medicalequipmentbiding.di.modules.BaseViewModelModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {BaseViewModelModule.class})
    abstract BaseActivity contributeBaseActivity();

    @ContributesAndroidInjector
    abstract SplashActivity contributeSplashActivity();

}
