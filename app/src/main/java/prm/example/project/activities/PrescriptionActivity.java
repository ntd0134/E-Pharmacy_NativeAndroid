package prm.example.project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import prm.example.project.R;
import prm.example.project.adapters.RecyclerViewDrugAdapter;
import prm.example.project.models.request_data.DrugData;
import prm.example.project.models.request_object.PrescriptionObject;
import prm.example.project.models.service_repository.Repository;
import prm.example.project.models.service_repository.RepositoryImpl;
import prm.example.project.utils.CallBackData;
import prm.example.project.utils.HashCodeUtil;
import prm.example.project.utils.SharedPreferencesUtil;

public class PrescriptionActivity extends AppCompatActivity {
    private static final String TAG = "PrescriptionActivity";

    private TextView mTxtId, mTxtCreatedDate, mTxtHospital, mTxtDoctor;
    private Button mBtnConfirm;
    private RecyclerView mRecyclerView;

    private Repository mRepository;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private ArrayList<DrugData.drugItem> listOfDrugs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        loadIntent();
        initView();
        initData();
    }

    private void initView(){
        mTxtId = findViewById(R.id.txt_id);
        mTxtCreatedDate = findViewById(R.id.txt_created_date);
        mTxtHospital = findViewById(R.id.txt_hospital);
        mTxtDoctor = findViewById(R.id.txt_doctor);
        mRecyclerView = findViewById(R.id.recycler_view_drug);
        mBtnConfirm = findViewById(R.id.btn_confirm);
    }

    private void loadIntent(){
        Intent intent = getIntent();
        listOfDrugs = (ArrayList<DrugData.drugItem>) intent.getSerializableExtra("listOfDrugs");
        Log.d("CHECK_INTENT", listOfDrugs.size()+"");
    }

    private void initData(){
        mRepository = new RepositoryImpl(this);
        sharedPreferencesUtil = new SharedPreferencesUtil();
        RecyclerViewDrugAdapter adapter = new RecyclerViewDrugAdapter(this, listOfDrugs);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrescriptionObject object = initRequestObject();
                mRepository.createPrescription(object, new CallBackData<String>() {
                    @Override
                    public void onSuccess(String s) {
                        setResult(7);
                        finish();
                    }

                    @Override
                    public void onFail(String message) {
                        Toast.makeText(PrescriptionActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private PrescriptionObject initRequestObject(){
        HashCodeUtil util = new HashCodeUtil();
        String hashString = sharedPreferencesUtil.getUsername(getApplicationContext()) + System.currentTimeMillis();
        hashString = util.encryptThisString(hashString);

        Date today = new Date();
        SimpleDateFormat spdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = spdf.format(today);

        ArrayList<PrescriptionObject.PrescriptionDetailsRequestList> requestList = new ArrayList<>();
        PrescriptionObject.PrescriptionDetailsRequestList requestDrug;
        for(int i=0;i<listOfDrugs.size();i++){
            requestDrug = new PrescriptionObject.PrescriptionDetailsRequestList(listOfDrugs.get(i).getId());
            requestList.add(requestDrug);
        }
        PrescriptionObject object = new PrescriptionObject(date, requestList, hashString,sharedPreferencesUtil.getUsername(this));
        return object;
    }
}
