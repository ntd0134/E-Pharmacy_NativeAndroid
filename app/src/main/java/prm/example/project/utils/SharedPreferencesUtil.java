package prm.example.project.utils;

import android.content.Context;

public class SharedPreferencesUtil {
    private final String ACCESS_TOKEN = "accessToken";
    private final String USERNAME = "userName";

    public String getAccessToken(Context context){
        return SharedPreferencesMethods.getStringSharedPreferences(context, ACCESS_TOKEN);
    }

    public void saveAccessToken(Context context, String value){
        SharedPreferencesMethods.saveStringSharedPreferences(context, ACCESS_TOKEN, value);
    }

    public void removeAccessToken(Context context){
        SharedPreferencesMethods.removeStringSharedPreferences(context, ACCESS_TOKEN);
    }

    public void removeUsername(Context context){
        SharedPreferencesMethods.removeStringSharedPreferences(context, USERNAME);
    }

    public void removeAccount(Context context){
        removeAccessToken(context);
        removeUsername(context);
    }

    public String getUsername(Context context){
        return SharedPreferencesMethods.getStringSharedPreferences(context, USERNAME);
    }

    public void saveUsername(Context context, String value){
        SharedPreferencesMethods.saveStringSharedPreferences(context, USERNAME, value);
    }
}
