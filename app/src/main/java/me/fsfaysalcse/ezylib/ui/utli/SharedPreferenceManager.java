package me.fsfaysalcse.ezylib.ui.utli;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_USER_TYPE = "userType";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private static final String KEY_IS_STUDENT_ID = "studentId";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setUserType(String userType) {
        editor.putString(KEY_USER_TYPE, userType);
        editor.apply();
    }

    public String getUserType() {
        return sharedPreferences.getString(KEY_USER_TYPE, "");
    }

    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setStudentId(String studentId) {
        editor.putString(KEY_IS_STUDENT_ID, studentId);
        editor.apply();
    }

    public String getStudentId() {
        return sharedPreferences.getString(KEY_IS_STUDENT_ID, "");
    }


    public void clearSharedPreferences() {
        editor.clear();
        editor.apply();
    }
}

