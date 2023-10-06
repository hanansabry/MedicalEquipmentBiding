package com.app.medicalequipmentbiding.di;



import com.app.medicalequipmentbiding.di.modules.AuthenticationViewModelModule;
import com.app.medicalequipmentbiding.di.modules.ClientBidingViewModelModule;
import com.app.medicalequipmentbiding.presentation.BaseActivity;
import com.app.medicalequipmentbiding.presentation.MainActivity;
import com.app.medicalequipmentbiding.presentation.SplashActivity;
import com.app.medicalequipmentbiding.di.modules.BaseViewModelModule;
import com.app.medicalequipmentbiding.presentation.authentication.LoginActivity;
import com.app.medicalequipmentbiding.presentation.authentication.RegisterActivity;
import com.app.medicalequipmentbiding.presentation.client.AwardingActivity;
import com.app.medicalequipmentbiding.presentation.client.ClientBidingListActivity;
import com.app.medicalequipmentbiding.presentation.client.NewBidingOrderActivity;
import com.app.medicalequipmentbiding.presentation.client.OfferDetailsActivity;
import com.app.medicalequipmentbiding.presentation.vendor.ActiveBidingListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {BaseViewModelModule.class})
    abstract BaseActivity contributeBaseActivity();

    @ContributesAndroidInjector
    abstract SplashActivity contributeSplashActivity();

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = AuthenticationViewModelModule.class)
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector(modules = AuthenticationViewModelModule.class)
    abstract RegisterActivity contributeRegisterActivity();

    @ContributesAndroidInjector(modules = ClientBidingViewModelModule.class)
    abstract ClientBidingListActivity contributeClientBidingListActivity();

    @ContributesAndroidInjector
    abstract ActiveBidingListActivity contributeActiveBidingListActivity();

    @ContributesAndroidInjector(modules = ClientBidingViewModelModule.class)
    abstract NewBidingOrderActivity contributeNewBidingOrderActivity();

    @ContributesAndroidInjector(modules = ClientBidingViewModelModule.class)
    abstract AwardingActivity contributeAwardingActivity();

    @ContributesAndroidInjector(modules = ClientBidingViewModelModule.class)
    abstract OfferDetailsActivity contributeOfferDetailsActivity();

}
