package prm.example.project.models.service_repository;

import prm.example.project.models.request_data.CategoryData;
import prm.example.project.models.request_data.DrugData;
import prm.example.project.models.request_data.LoginData;
import prm.example.project.models.request_data.LoginGoogleData;
import prm.example.project.models.request_object.LoginGoogleObject;
import prm.example.project.models.request_object.LoginObject;
import prm.example.project.models.request_object.PrescriptionObject;
import prm.example.project.models.request_object.SignUpObject;
import prm.example.project.utils.ConfigApi;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    //login
    @POST(ConfigApi.LOGIN)
    Call<LoginData> login(@Body LoginObject loginObject);
    @POST(ConfigApi.TOKEN_LOGIN)
    Call<LoginGoogleData> loginToken(@Body LoginGoogleObject loginGoogleObject);
    //category
    @GET(ConfigApi.CATEGORY)
    Call<CategoryData> getCategories(@Query("page") int page, @Query("size") int size);
    //drug
    @GET(ConfigApi.DRUG_NAME)
    Call<DrugData> getDrugByName(@Path("name") String name, @Query("page") int page, @Query("size")int size);
    //sign up
    @POST(ConfigApi.SIGN_UP)
    Call<Void> signUp(@Body SignUpObject signUpObject);
    //prescription
    @POST(ConfigApi.PRESCRIPTION)
    Call<Void> createPresciption(@Body PrescriptionObject prescriptionObject);
}
