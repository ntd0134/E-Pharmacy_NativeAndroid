package prm.example.project.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import prm.example.project.R;
import prm.example.project.activities.MainActivity;
import prm.example.project.models.request_data.LoginData;
import prm.example.project.models.request_object.SignUpObject;
import prm.example.project.models.service_repository.Repository;
import prm.example.project.models.service_repository.RepositoryImpl;
import prm.example.project.utils.CallBackData;
import prm.example.project.utils.SharedPreferencesUtil;

public class SignUpFragment extends Fragment {

    private View mView;

    private EditText mUsername, mPassword, mConfirmPassword, mEmail, mName, mAddress;
    private TextView mBirthDate;
    private Spinner mSpGender;
    private Button mSignUpBtn;
    private boolean genderIsMale;
    private Repository mRepository;
    private DatePickerDialog.OnDateSetListener onPickDateListener;
    private SharedPreferencesUtil sharedPreferencesUtil;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sign_up,container,false);
        initView();
        initData();
        return mView;
    }

    private void initView(){
        mBirthDate = mView.findViewById(R.id.txt_birth_date);
        mUsername = mView.findViewById(R.id.edt_username);
        mPassword = mView.findViewById(R.id.edt_password);
        mConfirmPassword = mView.findViewById(R.id.edt_confirm_password);
        mEmail = mView.findViewById(R.id.edt_email);
        mName = mView.findViewById(R.id.edt_name);
        mAddress = mView.findViewById(R.id.edt_address);
        mSpGender = mView.findViewById(R.id.spn_gender);
        mSignUpBtn = mView.findViewById(R.id.btn_sign_up);
    }

    private void initData(){
        sharedPreferencesUtil = new SharedPreferencesUtil();
        mRepository = new RepositoryImpl(mView.getContext());
        initSpGender();
        initDatePicker();
        initBtnPickDate();
        initBtnSignUp();
    }

    private void initBtnSignUp(){
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String confirmPassword = mConfirmPassword.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String name = mName.getText().toString().trim();
                String birthdate = mBirthDate.getText().toString().trim();
                String address = mAddress.getText().toString().trim();

                if(username.isEmpty()|| password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty() || name.isEmpty() || birthdate.isEmpty() || address.isEmpty()){
                    Toast.makeText(mView.getContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                }else if(!confirmPassword.equals(password)){
                    Toast.makeText(mView.getContext(), "Confirm Password doesn't match", Toast.LENGTH_SHORT).show();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(mView.getContext(), "Email format isn't correct", Toast.LENGTH_SHORT).show();
                }else{
                    SignUpObject object = new SignUpObject(address, birthdate, email, genderIsMale, name, password, username);
                    mRepository.signUp(object, new CallBackData<String>() {
                        @Override
                        public void onSuccess(String s) {
                            mRepository.login(username, password, new CallBackData<LoginData>() {
                                @Override
                                public void onSuccess(LoginData loginData) {
                                    sharedPreferencesUtil.saveAccessToken(mView.getContext(), loginData.getToken());
                                    sharedPreferencesUtil.saveUsername(mView.getContext(), loginData.getUsername());
                                    Intent intent = new Intent(mView.getContext(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }

                                @Override
                                public void onFail(String message) {

                                }
                            });
                        }

                        @Override
                        public void onFail(String message) {
                            Toast.makeText(mView.getContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void initSpGender(){
        List<String> stringsOfGender = new ArrayList<>();
        stringsOfGender.add("Male");
        stringsOfGender.add("Female");

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, stringsOfGender);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpGender.setAdapter(genderAdapter);
        mSpGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    genderIsMale = true;
                }else{
                    genderIsMale = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initBtnPickDate(){
        mBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(mView.getContext(), android.R.style.Theme_Material_Dialog, onPickDateListener, year, month, day);
                dialog.show();
            }
        });
    }

    private void initDatePicker(){
        onPickDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String time, theDay;
                if(dayOfMonth < 10){
                    theDay = "0" + dayOfMonth;
                }else{
                    theDay = dayOfMonth+"";
                }
                if (month > 8) {
                    time = year + "-" + (month+1) + "-" +theDay;
                } else {
                    time = year + "-0" + (month+1) + "-" +theDay;
                }
                mBirthDate.setText(time);
            }
        };
    }
}
