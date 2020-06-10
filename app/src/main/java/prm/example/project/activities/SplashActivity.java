package prm.example.project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import prm.example.project.R;
import prm.example.project.utils.SharedPreferencesUtil;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private final int SPLASH_DISPLAY_LENGTH = 4000;
    private SharedPreferencesUtil sharedPreferencesUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initData();
        new MyAsyncTask().execute();
    }

    private void initData() {
        sharedPreferencesUtil = new SharedPreferencesUtil();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(SPLASH_DISPLAY_LENGTH);
            } catch (InterruptedException e) {
                Log.d(TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            String accessToken = sharedPreferencesUtil.getAccessToken(getApplicationContext());
            String username = sharedPreferencesUtil.getUsername(getApplicationContext());
            Intent intent;
            if (accessToken.equals("") || username.equals("")) {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
            finish();
            super.onPostExecute(aVoid);
        }
    }


}
