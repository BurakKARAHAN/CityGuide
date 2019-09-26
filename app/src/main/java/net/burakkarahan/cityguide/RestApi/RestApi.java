package net.burakkarahan.cityguide.RestApi;

import net.burakkarahan.cityguide.Model.ModelActivation;
import net.burakkarahan.cityguide.Model.ModelFavorite;
import net.burakkarahan.cityguide.Model.ModelFavoriteAdd;
import net.burakkarahan.cityguide.Model.ModelFavoriteDelete;
import net.burakkarahan.cityguide.Model.ModelFavoriteShow;
import net.burakkarahan.cityguide.Model.ModelLogin;
import net.burakkarahan.cityguide.Model.ModelPHPMailer;
import net.burakkarahan.cityguide.Model.ModelSignup;
import net.burakkarahan.cityguide.Model.ModelStructure;
import net.burakkarahan.cityguide.Model.ModelStructureIdFind;
import net.burakkarahan.cityguide.Model.ModelStructureImage;
import net.burakkarahan.cityguide.Model.ModelStructureMap;
import net.burakkarahan.cityguide.Model.ModelTravelAdd;
import net.burakkarahan.cityguide.Model.ModelTravelShow;
import net.burakkarahan.cityguide.Model.ModelUserImage;
import net.burakkarahan.cityguide.Model.ModelUserInformation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RestApi {

    @FormUrlEncoded
    @POST("/login.php")
    Call<ModelLogin> control(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/register.php")
    Call<ModelSignup> signup(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/PHPMailer.php")
    Call<ModelPHPMailer> ModelPHPMailer(@Field("email") String email, @Field("activationcode") String activationcode);

    @GET("/structure.php")
    Call<List<ModelStructure>>  ModelStructure();

    @FormUrlEncoded
    @POST("/structuremap.php")
    Call<ModelStructureMap>  ModelStructureMap(@Field("id_structure") int id_structure);

    @GET("/FavoriteControl.php")
    Call<ModelFavorite>  ModelFavorite(@Query("id_user") int id_user, @Query("id_structure") int id_structure);

    @GET("/FavoriteAdd.php")
    Call<ModelFavoriteAdd>  ModelFavoriteAdd(@Query("id_user") int id_user, @Query("id_structure") int id_structure);

    @GET("/FavoriteDelete.php")
    Call<ModelFavoriteDelete>  ModelFavoriteelete(@Query("id_user") int id_user, @Query("id_structure") int id_structure);

    @GET("/FavoriteShow.php")
    Call<List<ModelFavoriteShow>>  ModelFavoriteShow(@Query("id_user") int id_user);

    @GET("/Activation.php")
    Call<ModelActivation>  ModelActivation(@Query("id_user") int id_user, @Query("activation") String activation);

    @GET("/UserInformation.php")
    Call<ModelUserInformation>  ModelUserInformation(@Query("id_user") int id_user);

    @GET("/StructureIdFind.php")
    Call<ModelStructureIdFind>  ModelStructureIdFind(@Query("name") String name);

    @FormUrlEncoded
    @POST("/UserImage.php")
    Call<ModelUserImage>  ModelUserImage(@Field("id_user") int id_user, @Field("image") String image);

    @GET("/StructureImage.php")
    Call<List<ModelStructureImage>>  ModelStructureImage(@Query("id_structure") int id_structure);

    @GET("/TravelShow.php")
    Call<List<ModelTravelShow>>  ModelTravelShow(@Query("id_user") int id_user);

    @GET("/TravelAdd.php")
    Call<ModelTravelAdd>  ModelTravelAdd(@Query("id_user") int id_user, @Query("id_structure") int id_structure);
}
