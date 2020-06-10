package prm.example.project.models.service_repository;

import java.io.IOException;

import prm.example.project.models.request_data.CategoryData;
import prm.example.project.models.request_data.DrugData;
import prm.example.project.models.request_data.LoginData;
import prm.example.project.models.request_data.LoginGoogleData;
import prm.example.project.models.request_object.PrescriptionObject;
import prm.example.project.models.request_object.SignUpObject;
import prm.example.project.utils.CallBackData;

public interface Repository {
    void login(String username, String password, CallBackData<LoginData> callBackData);
    void getCategories(int page, int size, CallBackData<CategoryData> callBackData);
    void getDrugByName(String name, int page, int size, CallBackData<DrugData> callBackData) throws IOException;
    void loginGoogle(String idToken, CallBackData<LoginGoogleData> callBackData);
    void signUp(SignUpObject signUpObject, CallBackData<String> callBackData);
    void createPrescription(PrescriptionObject object, CallBackData<String> callBackData);
}
