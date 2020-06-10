package prm.example.project.presenters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import prm.example.project.models.request_data.CategoryData;
import prm.example.project.models.request_data.DrugData;
import prm.example.project.models.service_repository.Repository;
import prm.example.project.models.service_repository.RepositoryImpl;
import prm.example.project.utils.CallBackData;
import prm.example.project.views.MainView;

public class MainPresenter {
    private String mCurrentPhotoPath;

    private Context mContext;
    private MainView mMainView;
    private Repository mRepository;

    private List<DrugData.drugItem> drugNameDataList;
    private List<Integer> listQuantities;

    public MainPresenter(Context mContext, MainView mMainView) {
        this.mContext = mContext;
        this.mMainView = mMainView;
        mRepository = new RepositoryImpl(mContext);
    }

    public void getCategories(){
        mRepository.getCategories(1, 100, new CallBackData<CategoryData>() {
            @Override
            public void onSuccess(CategoryData categoryData) {
                mMainView.onCategoryLoadSuccess(categoryData);
            }

            @Override
            public void onFail(String message) {
                mMainView.onCategoryLoadFailed(message);
            }
        });
    }

    public void DetectText(){
        //turn on ProgressBar
        mMainView.turnOnProgressBar();

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);

        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionCloudTextRecognizerOptions options = new FirebaseVisionCloudTextRecognizerOptions.Builder()
                .setLanguageHints(Arrays.asList("vi", "en"))
                .build();
        FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance()
                .getCloudTextRecognizer(options);

        textRecognizer.processImage(firebaseVisionImage)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText result) {
                        try {
                            processImage(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //turn off Progressbar
                        mMainView.turnOffProgressBar();
                        //Return drugs to View
                        if(!drugNameDataList.isEmpty()){
                            mMainView.onDrugLoadSuccess(drugNameDataList);
                        }
                        //No Drug Found (List is Empty)
                        else{
                            mMainView.onDrugLoadFailed("No Drug Found!");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void DetectTextFromGallery(Uri uri) throws IOException {
        mMainView.turnOnProgressBar();

//        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);

        Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);

        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionCloudTextRecognizerOptions options = new FirebaseVisionCloudTextRecognizerOptions.Builder()
                .setLanguageHints(Arrays.asList("vi", "en"))
                .build();
        FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance()
                .getCloudTextRecognizer(options);

        textRecognizer.processImage(firebaseVisionImage)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText result) {
                        try {
                            processImage(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //turn off Progressbar
                        mMainView.turnOffProgressBar();
                        //Return drugs to View
                        if(!drugNameDataList.isEmpty()){
                            mMainView.onDrugLoadSuccess(drugNameDataList);
                        }
                        //No Drug Found (List is Empty)
                        else{
                            mMainView.onDrugLoadFailed("No Drug Found!");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void processImage(FirebaseVisionText result) throws IOException {
        drugNameDataList = new ArrayList<>();
        for (FirebaseVisionText.TextBlock block: result.getTextBlocks()) {
            Log.d("BLOCK", block.getText());
            for (FirebaseVisionText.Line paragraph: block.getLines()) {
                Log.d("LINE", paragraph.getText());
                for (FirebaseVisionText.Element word: paragraph.getElements()) {
                    String wordText = word.getText().trim();
                    Log.d("WORD", wordText);

                    if(wordText.length()>3){
                        if(wordText.contains("(") || wordText.contains("=") || wordText.contains("-")
                                || wordText.contains("+")){
                            wordText = wordText.replaceAll("[^A-Za-z]+", "");
                            Log.d("REPLACED",wordText);
                        }
                        mRepository.getDrugByName(wordText, 1, 100, new CallBackData<DrugData>() {
                            @Override
                            public void onSuccess(DrugData drugData) {
                                for(int i = 0; i< drugData.getElements().size(); i++){
                                    drugNameDataList.add(drugData.getElements().get(i));
                                    Log.d("ELEMENTS", drugData.getElements().get(i).getName());
                                }
                            }

                            @Override
                            public void onFail(String message) {
                                //list is null; do nothing
                            }
                        });
                    }
                }
            }
        }
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(mContext.getApplicationContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                mMainView.navigateToCamera(takePictureIntent, mCurrentPhotoPath);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
