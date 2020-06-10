package prm.example.project.presenters;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseUser;

import prm.example.project.models.request_data.LoginData;
import prm.example.project.models.request_data.LoginGoogleData;
import prm.example.project.models.service_repository.Repository;
import prm.example.project.models.service_repository.RepositoryImpl;
import prm.example.project.utils.CallBackData;
import prm.example.project.utils.SharedPreferencesUtil;
import prm.example.project.views.LoginView;

public class LoginPresenter {
    private Context mContext;
    private LoginView mLoginView;
    private Repository mRepository;
    private SharedPreferencesUtil sharedPreferencesUtil;

    public LoginPresenter(Context mContext, LoginView mLoginView) {
        this.mContext = mContext;
        this.mLoginView = mLoginView;
        mRepository = new RepositoryImpl(mContext);
        sharedPreferencesUtil = new SharedPreferencesUtil();
    }

    public void login(String username, String password) {
        String validateString = validateInput(username, password);
        if (!validateString.equals("")) {
            mLoginView.loginFailed(validateString);
        } else {
            mRepository.login(username, password, new CallBackData<LoginData>() {
                @Override
                public void onSuccess(LoginData loginData) {
                    sharedPreferencesUtil.saveAccessToken(mContext, loginData.getToken());
                    sharedPreferencesUtil.saveUsername(mContext, loginData.getUsername());
                    mLoginView.loginSuccess();
                }

                @Override
                public void onFail(String message) {
                    if (message.equals("")) message = "Wrong username/password";
                    mLoginView.loginFailed(message);
                }
            });
        }
    }

    public void loginGoogle() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(mContext.getApplicationContext());
        mRepository.loginGoogle(account.getIdToken(), new CallBackData<LoginGoogleData>() {
            @Override
            public void onSuccess(LoginGoogleData loginGoogleData) {
                sharedPreferencesUtil.saveAccessToken(mContext, loginGoogleData.getAccessToken());
                sharedPreferencesUtil.saveUsername(mContext, loginGoogleData.getUsername());
                mLoginView.loginSuccess();
            }

            @Override
            public void onFail(String message) {
                mLoginView.loginFailed(message);
            }
        });
    }

    private String getUsername(String username) {
        int pos = username.indexOf("@");
        return username.substring(0, pos);
    }


    private String validateInput(String username, String password) {
        if (username.equals("")) {
            return "Please input username!";
        } else if (password.equals("")) {
            return "Please input password!";
        } else
            return "";
    }
}
