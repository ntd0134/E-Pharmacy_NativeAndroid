package prm.example.project.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferencesMethods {
    private static final String TAG = "SharedPreferencesMethod";

    public static void saveStringSharedPreferences(Context context, String key, String value) {
        try {
            SharedPreferences prefs = context.getSharedPreferences(
                    context.getPackageName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(key, value);
            editor.commit();
        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
        }
    }

    public static String getStringSharedPreferences(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        String defaultValue = "";
        return prefs.getString(key, defaultValue);
    }

    public static void removeStringSharedPreferences(Context context, String key) {
        try {
            SharedPreferences prefs = context.getSharedPreferences(
                    context.getPackageName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(key);
            editor.commit();
        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
        }
    }

    public static void saveIntSharedPreferences(Context context, String key, int value) {
        try {
            SharedPreferences prefs = context.getSharedPreferences(
                    context.getPackageName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(key, value);
            editor.commit();
        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
        }
    }

    public static int getIntSharedPreferences(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        int defaultValue = 0;
        return prefs.getInt(key, defaultValue);
    }

    public static void removeIntSharedPreferences(Context context, String key) {
        try {
            SharedPreferences prefs = context.getSharedPreferences(
                    context.getPackageName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(key);
            editor.commit();
        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
        }
    }

    public static void saveBooleanSharedPreferences(Context context, String key, boolean value) {
        try {
            SharedPreferences prefs = context.getSharedPreferences(
                    context.getPackageName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(key, value);
            editor.commit();
        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
        }
    }

    public static boolean getBooleanSharedPreferences(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        boolean defaultValue = false;
        return prefs.getBoolean(key, defaultValue);
    }

    public static void removeBooleanSharedPreferences(Context context, String key) {
        try {
            SharedPreferences prefs = context.getSharedPreferences(
                    context.getPackageName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(key);
            editor.commit();
        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
        }
    }
}
