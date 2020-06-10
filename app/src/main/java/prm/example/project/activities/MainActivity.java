package prm.example.project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import prm.example.project.R;
import prm.example.project.adapters.RecyclerViewCategoryAdapter;
import prm.example.project.fragments.MainBottomSheetDialog;
import prm.example.project.models.request_data.CategoryData;
import prm.example.project.models.request_data.DrugData;
import prm.example.project.presenters.MainPresenter;
import prm.example.project.utils.SharedPreferencesUtil;
import prm.example.project.views.MainView;

public class MainActivity extends AppCompatActivity implements MainView, MainBottomSheetDialog.BottomSheetListener {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int CREATED_PRESCIPRTION_C = 7;

    private FloatingActionButton mCameraButton;
    private MainPresenter mMainPresenter;
    private BottomAppBar mBottomAppBar;
    private RecyclerView mCategoryRecyclerView;
    private RelativeLayout mProgressBar;

    private SharedPreferencesUtil sharedPreferencesUtil;
    private GoogleSignInClient mGoogleSignInClient;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
        initView();
        initData();
        initOnClickEvent();
    }

    private void initView() {
        mBottomAppBar = findViewById(R.id.bottom_app_bar);
        mCategoryRecyclerView = findViewById(R.id.recycler_view_category);
        mCameraButton = findViewById(R.id.camera_button);
        mProgressBar = findViewById(R.id.progress_bar);
    }

    private void initOnClickEvent() {
        //set on Click for AppBar Hamburger Button
        mBottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainBottomSheetDialog bottomSheetDialog = new MainBottomSheetDialog();
                bottomSheetDialog.show(getSupportFragmentManager(), "BottomSheet");
            }
        });

        //set on Click for AppBar Menu Items
        mBottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.app_bar_gallery:
                        btnGalleryOnClick();
                        break;
                    case R.id.app_bar_list:
                        Toast.makeText(MainActivity.this, "List", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.app_bar_search:
                        Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            }
        });
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPresenter.dispatchTakePictureIntent();
            }
        });
    }

    private void initData() {
        mMainPresenter = new MainPresenter(getApplicationContext(), this);
        //set up Bottom App Bar
        setSupportActionBar(mBottomAppBar);
        //load categories
        mMainPresenter.getCategories();
        //init util
        sharedPreferencesUtil = new SharedPreferencesUtil();
    }

    private void btnGalleryOnClick() {
        //check runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                //show pop up for runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            } else {
                //permission already granted
                pickImageFromGallery();
            }
        } else {
            pickImageFromGallery();
        }
    }

    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    //handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                mMainPresenter.DetectText();
            } else if (requestCode == IMAGE_PICK_CODE) {
                try {
                    mMainPresenter.DetectTextFromGallery(data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.bottom_app_bar_menu, menu);
        return true;
    }

    @Override
    public void onCategoryLoadSuccess(CategoryData categoryData) {
        RecyclerViewCategoryAdapter adapter = new RecyclerViewCategoryAdapter(this, categoryData.getElements());
        mCategoryRecyclerView.setAdapter(adapter);
        mCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onCategoryLoadFailed(String message) {
        Toast.makeText(this, "load data failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToCamera(Intent intent, String currentPhotoPath) {
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    @Override
    public void turnOnProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void turnOffProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDrugLoadSuccess(List<DrugData.drugItem> listOfDrugs) {
        listOfDrugs = new ArrayList<DrugData.drugItem>(new LinkedHashSet<DrugData.drugItem>(listOfDrugs));
        //navigate to Prescription Activity
        Intent intent = new Intent(getApplicationContext(), PrescriptionActivity.class);
        intent.putExtra("listOfDrugs", (Serializable) listOfDrugs);
        Log.d("CHECK_INTENT", listOfDrugs.size()+"");
        startActivityForResult(intent, CREATED_PRESCIPRTION_C);
    }

    @Override
    public void onDrugLoadFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBtnEditProfileClicked(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onBtnLogOut() {
        sharedPreferencesUtil.removeAccount(getApplicationContext());
        mGoogleSignInClient.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //        //Converting ArrayList to HashSet to remove duplicates
//        HashSet<DrugData.drugItem> listToSet = new HashSet<>(listOfDrugs);
//
//        //Creating Arraylist without duplicate values
//        List<DrugData.drugItem> listWithoutDuplicates = new ArrayList<>(listToSet);
//        Log.d("TEST_CODE_DOM", listWithoutDuplicates.size()+"");
//        for(int i=0; i<listWithoutDuplicates.size();i++){
//            Log.d("TEST_CODE_DOM", listWithoutDuplicates.get(i).getName());
//        }

//        for (int i = 0; i < listOfDrugs.size(); i++) {
//            for(int j = i+1; j<listOfDrugs.size(); j++){
//                if(listOfDrugs.get(i).getId().equals(listOfDrugs.get(j).getId())){
//
//                }
//            }
//            Log.d("OBJECT_NE", i + listOfDrugs.get(i).toString());
//        }

//        listOfDrugs.stream().collect(Collectors.groupingBy(Function.identity(),
//                Collectors.counting()))
//                .entrySet()
//                .stream()
//                .filter(e -> e.getValue() > 1L)
//                .map(e -> e.getKey())
//                .collect(Collectors.toList())
//                .forEach(drugItem -> Log.d("TEST_CODE_XIN", drugItem.getId()));
}
