package com.app.medicalequipmentbiding.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.app.medicalequipmentbiding.utils.DeleteListener;
import com.app.medicalequipmentbiding.utils.SessionManager;
import com.app.medicalequipmentbiding.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    static FirebaseDatabase provideFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

    @Singleton
    @Provides
    static FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Singleton
    @Provides
    static SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    static SessionManager provideSessionManager(Application application, SharedPreferences sharedPreferences) {
        return new SessionManager(application, sharedPreferences);
    }

    @Provides
    static DeleteListener provideDeleteListener() {
        return new DeleteListener();
    }

}
