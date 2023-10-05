package com.app.medicalequipmentbiding.presentation;

import android.os.Bundle;
import android.view.View;

import com.app.medicalequipmentbiding.utils.SessionManager;
import com.app.medicalequipmentbiding.utils.DeleteListener;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    @Inject
    public SessionManager sessionManager;
    @Inject
    public DeleteListener deleteListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getDataBindingView());
    }

    public abstract View getDataBindingView();

    public void onBackClicked(View view) {
        onBackPressed();
    }

    public View getRootView(){
        return null;
    }
}