package com.app.medicalequipmentbiding.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class SessionManager {

    //Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_ID = "id";

    public static final String KEY_NAME = "name";

    public static final String KEY_EMAIL = "email";

    public static final String KEY_ROLE = "role";

    public static final String KEY_START_SESSION_TIME = "session_start_time";

    private static final long SESSION_TIMEOUT_DURATION_MS = 2 * 7 * 24 * 60 * 60 * 1000; //2 weeks
//    private static final long SESSION_TIMEOUT_DURATION_MS = 30 * 24 * 60 * 60 * 1000; //1 day
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_ACCOUNT = "account_type";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    @Inject
    public SessionManager(Context context, SharedPreferences sharedPreferences) {
        this.context = context;
        this.sharedPreferences = sharedPreferences;
        editor = sharedPreferences.edit();
    }

    //Create login session
    public void createLoginSession(String firebaseId, String name, String email, String accountType) {
        editor.putString(KEY_ID, firebaseId);
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ACCOUNT, accountType);
        editor.putLong(KEY_START_SESSION_TIME, System.currentTimeMillis());
        editor.apply();
    }

//    /**
//     * Check login method wil check user login status If false it will redirect
//     * user to login page Else won't do anything
//     */
//    public void checkLogin() {
//        // Check login status
//        if (isLoggedIn() && !isSessionTimeout()) {
//            // user is logged in, redirect him to Home Activity
//            Intent i = new Intent(context, MainActivity.class);
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
//        } else {
//            // user is not logged in, redirect him to Login Activity
//            Intent i = new Intent(context, SignInActivity.class);
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
//        }
//    }
//
//    /**
//     * Session timeout
//     */
//    public boolean isSessionTimeout() {
//        long sessionStartTime = sharedPreferences.getLong(KEY_START_SESSION_TIME, 0);
//        long currentTime = System.currentTimeMillis();
//        return currentTime - sessionStartTime > SESSION_TIMEOUT_DURATION_MS;
//    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.apply();
//        FirebaseAuth.getInstance().signOut();
        // After logout redirect user to Login Activity
//        checkLogin();
    }

    /**
     * Get stored session data
     */
//    public HashMap<String, Object> getUserDetails() {
//        HashMap<String, Object> user = new HashMap<>();
//        user.put(KEY_ID, sharedPreferences.getString(KEY_ID, null));
//        user.put(KEY_NAME, sharedPreferences.getString(KEY_NAME, null));
//        user.put(KEY_EMAIL, sharedPreferences.getString(KEY_EMAIL, null));
//        user.put(KEY_ROLE, sharedPreferences.getInt(KEY_ROLE, Constants.INVALID_ID));
//        user.put(KEY_TOKEN, sharedPreferences.getString(KEY_TOKEN, null));
//        user.put(KEY_FARM_NAME, sharedPreferences.getString(KEY_FARM_NAME, null));
//        user.put(KEY_FARM_ID, sharedPreferences.getInt(KEY_FARM_ID, Constants.INVALID_ID));
//        user.put(KEY_TENANT_ID, sharedPreferences.getInt(KEY_TENANT_ID, Constants.INVALID_ID));
//        return user;
//    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void saveIdToken(String token) {
        System.out.println(token);
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public String getAccountType() {
        return sharedPreferences.getString(KEY_ACCOUNT, null);
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public String getFirebaseId() {
        return sharedPreferences.getString(KEY_ID, null);
    }

    public String getUserName() {
        return sharedPreferences.getString(KEY_NAME, null);
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

}
