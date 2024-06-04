package tlu.cse.ht63.coffeeshop.Services;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "LoginPrefs";
    private static final String KEY_USERID = "userId";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_FULLNAME = "fullName";
    private static final String KEY_LOGGED_IN = "loggedIn";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoggedIn(int userId, String username, String fullname, boolean loggedIn) {
        editor.putInt(KEY_USERID, userId);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_FULLNAME, fullname);
        editor.putBoolean(KEY_LOGGED_IN, loggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_LOGGED_IN, false);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }

    public int getUserId() {
        return sharedPreferences.getInt(KEY_USERID, 0);
    }
    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }


    public String getFullName() {
        return sharedPreferences.getString(KEY_FULLNAME, null);
    }

    public void setFullName(String fullName) {
        editor.putString(KEY_FULLNAME, fullName);
        editor.apply();
    }
}
