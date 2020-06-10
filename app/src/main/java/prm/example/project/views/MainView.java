package prm.example.project.views;

import android.content.Intent;

import java.util.List;

import prm.example.project.models.request_data.CategoryData;
import prm.example.project.models.request_data.DrugData;

public interface MainView {
    //load Category action
    void onCategoryLoadSuccess(CategoryData categoryData);
    void onCategoryLoadFailed(String message);

    //move to camera
    void navigateToCamera(Intent intent, String currentPhotoPath);

    //switching progress bar
    void turnOnProgressBar();
    void turnOffProgressBar();

    //load drug from camera
    void onDrugLoadSuccess(List<DrugData.drugItem> listOfDrugs);
    void onDrugLoadFailed(String message);
}
