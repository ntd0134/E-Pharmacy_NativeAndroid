package prm.example.project.models.service_repository;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import prm.example.project.R;
import prm.example.project.models.request_data.CategoryData;
import prm.example.project.models.request_data.DrugData;
import prm.example.project.models.request_data.LoginData;
import prm.example.project.models.request_data.LoginGoogleData;
import prm.example.project.models.request_object.LoginGoogleObject;
import prm.example.project.models.request_object.LoginObject;
import prm.example.project.models.request_object.PrescriptionObject;
import prm.example.project.models.request_object.SignUpObject;
import prm.example.project.utils.CallBackData;
import prm.example.project.utils.ConfigApi;
import prm.example.project.utils.SharedPreferencesMethods;
import prm.example.project.utils.SharedPreferencesUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryImpl implements Repository {
    private final String TAG = SharedPreferencesMethods.class.getName();
    private Context mContext;
    private Retrofit retrofit;
    private Service service;
    private OkHttpClient client;
    private SharedPreferencesUtil sharedPreferencesUtil;

    public RepositoryImpl(Context mContext) {
        sharedPreferencesUtil = new SharedPreferencesUtil();
        this.mContext = mContext;
    }

    @Override
    public void login(String username, String password, final CallBackData<LoginData> callBackData) {
        initRetrofitForLogin();
        LoginObject loginObject = new LoginObject(password, username);
        Call<LoginData> call = service.login(loginObject);
        call.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if (response.isSuccessful()) {
                    callBackData.onSuccess(response.body());
                } else {
                    callBackData.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                callBackData.onFail(t.getMessage());
            }
        });
    }

    @Override
    public void loginGoogle(String idToken, CallBackData<LoginGoogleData> callBackData) {
        initRetrofitForLogin();
        LoginGoogleObject object = new LoginGoogleObject(idToken);
        Call<LoginGoogleData> call = service.loginToken(object);
        Log.d("TOKEN", idToken);
        call.enqueue(new Callback<LoginGoogleData>() {
            @Override
            public void onResponse(Call<LoginGoogleData> call, Response<LoginGoogleData> response) {
                if(response.isSuccessful()){
                    callBackData.onSuccess(response.body());
                }else{
                    callBackData.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginGoogleData> call, Throwable t) {
                callBackData.onFail(t.getMessage());
            }
        });
    }

    @Override
    public void signUp(SignUpObject signUpObject, CallBackData<String> callBackData) {
        initRetrofit();
        Call<Void> call = service.signUp(signUpObject);
        Log.d("SignUp", signUpObject.toString());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 201){
                    callBackData.onSuccess("Success");
                }else if(response.code() == 409){
                    callBackData.onFail("User existed");
                }else{
                    callBackData.onFail(response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callBackData.onFail(t.getMessage());
            }
        });
    }

    @Override
    public void createPrescription(PrescriptionObject object, CallBackData<String> callBackData) {
        initRetrofit();
        Call<Void> call = service.createPresciption(object);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    callBackData.onSuccess("Success");
                }else if(response.code() == 409){
                    callBackData.onFail("Presciprtion existed");
                }else{
                    callBackData.onFail(response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callBackData.onFail(t.getMessage());
            }
        });
    }

    @Override
    public void getCategories(int page, int size, CallBackData<CategoryData> callBackData) {
        initRetrofit();
        Call<CategoryData> call = service.getCategories(page, size);
        call.enqueue(new Callback<CategoryData>() {
            @Override
            public void onResponse(Call<CategoryData> call, Response<CategoryData> response) {
                if (response.isSuccessful()) {
                    callBackData.onSuccess(response.body());
                } else {
                    callBackData.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<CategoryData> call, Throwable t) {
                callBackData.onFail(t.getMessage());
            }
        });
    }

    @Override
    public void getDrugByName(String name, int page, int size, CallBackData<DrugData> callBackData) throws IOException {
        initRetrofit();
        Call<DrugData> call = service.getDrugByName(name, page, size);
        Response<DrugData> response = call.execute();
        if (response.isSuccessful()) {
            callBackData.onSuccess(response.body());
        } else if (response.code() == 404) {
            callBackData.onFail("none");
        } else {
            callBackData.onFail(response.code() + "");
        }
    }


    private void initRetrofitForLogin() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(ConfigApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        service = retrofit.create(Service.class);
    }

    private void initRetrofit() {
        initClient();
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(ConfigApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Service.class);
    }

    private void initClient() {
        String accessToken = sharedPreferencesUtil.getAccessToken(mContext);
        client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + accessToken)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
    }
}
