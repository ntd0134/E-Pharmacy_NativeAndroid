package prm.example.project.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import prm.example.project.R;
import prm.example.project.activities.MainActivity;
import prm.example.project.presenters.LoginPresenter;
import prm.example.project.views.LoginView;

public class LoginFragment extends Fragment implements LoginView, View.OnClickListener{
    private final int RC_SIGN_IN = 1;
    private final String TAG = LoginFragment.class.getName();

    private View mView;
    private LoginPresenter mLoginPresenter;
    private EditText mEdtUsername, mEdtPassword;
    private Button mBtnLogin;
    private RelativeLayout mProgressBar;

    private SignInButton mBtnSignIn;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_login,container,false);
        initView();
        initData();
        return mView;
    }

    private void initView(){
        mProgressBar = mView.findViewById(R.id.progress_bar);
        mEdtUsername = mView.findViewById(R.id.edt_username);
        mEdtPassword = mView.findViewById(R.id.edt_password);
        mBtnLogin = mView.findViewById(R.id.btn_login);
        mBtnSignIn = mView.findViewById(R.id.btn_sign_in);
        mAuth = FirebaseAuth.getInstance();
    }

    private void initData(){
        mLoginPresenter = new LoginPresenter(mView.getContext(), this);
        mBtnLogin.setOnClickListener(this);
        mBtnSignIn.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(),gso);
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(mView.getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void loginFailed(String message) {
        mProgressBar.setVisibility(View.GONE);
        mGoogleSignInClient.signOut();
        Toast.makeText(mView.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                mProgressBar.setVisibility(View.VISIBLE);
                String username = mEdtUsername.getText().toString();
                String password = mEdtPassword.getText().toString();
                mLoginPresenter.login(username,password);
                break;
            case R.id.btn_sign_in:
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            FirebaseGoogleAuth(acc);
        }
        catch(ApiException e){
            Log.d(TAG, e.getMessage());
            FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acc){
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acc.getIdToken(),null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mLoginPresenter.loginGoogle();
                }
                else{
                    Toast.makeText(mView.getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
