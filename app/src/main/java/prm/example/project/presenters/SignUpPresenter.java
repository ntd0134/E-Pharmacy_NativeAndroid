package prm.example.project.presenters;

import android.content.Context;

import prm.example.project.models.service_repository.Repository;
import prm.example.project.models.service_repository.RepositoryImpl;
import prm.example.project.utils.SharedPreferencesUtil;
import prm.example.project.views.SignUpView;

public class SignUpPresenter {
    private Context mContext;
    private SignUpView mSignUpView;
    private Repository mRepository;
    private SharedPreferencesUtil sharedPreferencesUtil;

    public SignUpPresenter(Context mContext, SignUpView mSignUpView) {
        this.mContext = mContext;
        this.mSignUpView = mSignUpView;
        mRepository = new RepositoryImpl(mContext);
        sharedPreferencesUtil = new SharedPreferencesUtil();
    }


}
