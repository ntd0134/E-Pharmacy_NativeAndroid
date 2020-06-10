package prm.example.project.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import prm.example.project.R;
import prm.example.project.activities.EditProfileActivity;
import prm.example.project.utils.SharedPreferencesUtil;

public class MainBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener{
    private View mView;
    private BottomSheetListener mListener;
    private Button mBtnEditProfile, mBtnLogOut;
    private TextView mTxtUsername;

    private SharedPreferencesUtil sharedPreferencesUtil;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.bottom_sheet_layout, container, false);
        initView();
        initData();
        return mView;
    }

    private void initView(){
        mBtnEditProfile = mView.findViewById(R.id.btn_edit_profile);
        mBtnLogOut = mView.findViewById(R.id.btn_log_out);
        mTxtUsername = mView.findViewById(R.id.txt_username);
    }

    private void initData(){
        mBtnEditProfile.setOnClickListener(this);
        mBtnLogOut.setOnClickListener(this);
        //init util
        sharedPreferencesUtil = new SharedPreferencesUtil();
        //set Username
        mTxtUsername.setText(sharedPreferencesUtil.getUsername(getContext()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_edit_profile:
                Intent intent = new Intent(mView.getContext(), EditProfileActivity.class);
                mListener.onBtnEditProfileClicked(intent);
                dismiss();
                break;
            case R.id.btn_log_out:
                mListener.onBtnLogOut();
                dismiss();
                break;
        }
    }

    public interface BottomSheetListener{
        void onBtnEditProfileClicked(Intent intent);
        void onBtnLogOut();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        }
        catch (ClassCastException e){
            Log.d("MainBottomSheetDialog", " must implement BottomSheetListener");
        }
    }
}
