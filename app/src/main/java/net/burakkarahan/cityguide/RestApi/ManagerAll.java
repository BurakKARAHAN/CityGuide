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

public class ManagerAll extends BaseManager{

    private static ManagerAll ourInstance = new ManagerAll();

    public static synchronized ManagerAll getInstance()
    {
        return ourInstance;
    }

    public Call<ModelLogin> login(String email, String password)
    {
        Call<ModelLogin> x = getRestApiClient().control(email,password);
        return x;
    }

    public Call<ModelSignup> register(String email, String password)
    {
        Call<ModelSignup> x = getRestApiClient().signup(email,password);
        return x;
    }

    public Call<ModelPHPMailer> PHPMailer(String email, String activationcode)
    {
        Call<ModelPHPMailer> x = getRestApiClient().ModelPHPMailer(email,activationcode);
        return x;
    }

    public Call<List<ModelStructure>> Structure()
    {
        Call<List<ModelStructure>> x = getRestApiClient().ModelStructure();
        return x;
    }

    public Call<ModelStructureMap> StructureMap(int id_structure)
    {
        Call<ModelStructureMap> x = getRestApiClient().ModelStructureMap(id_structure);
        return x;
    }

    public Call<ModelFavorite> Favorite(int id_user, int id_structure)
    {
        Call<ModelFavorite> x = getRestApiClient().ModelFavorite(id_user, id_structure);
        return x;
    }

    public Call<ModelFavoriteAdd> FavoriteAdd(int id_user, int id_structure)
    {
        Call<ModelFavoriteAdd> x = getRestApiClient().ModelFavoriteAdd(id_user, id_structure);
        return x;
    }

    public Call<ModelFavoriteDelete> FavoriteDelete(int id_user, int id_structure)
    {
        Call<ModelFavoriteDelete> x = getRestApiClient().ModelFavoriteelete(id_user, id_structure);
        return x;
    }

    public Call<List<ModelFavoriteShow>> FavoriteShow(int id_user)
    {
        Call<List<ModelFavoriteShow>> x = getRestApiClient().ModelFavoriteShow(id_user);
        return x;
    }

    public Call<ModelActivation> Activation(int id_user, String activation)
    {
        Call<ModelActivation> x = getRestApiClient().ModelActivation(id_user, activation);
        return x;
    }

    public Call<ModelUserInformation> UserInformation(int id_user)
    {
        Call<ModelUserInformation> x = getRestApiClient().ModelUserInformation(id_user);
        return x;
    }

    public Call<ModelStructureIdFind> StructureIdFind(String name)
    {
        Call<ModelStructureIdFind> x = getRestApiClient().ModelStructureIdFind(name);
        return x;
    }

    public Call<ModelUserImage> UserImage(int id_user, String image)
    {
        Call<ModelUserImage> x = getRestApiClient().ModelUserImage(id_user,image);
        return x;
    }

    public Call<List<ModelStructureImage>> StructureImage(int id_structure)
    {
        Call<List<ModelStructureImage>> x = getRestApiClient().ModelStructureImage(id_structure);
        return x;
    }

    public Call<List<ModelTravelShow>> TravelShow(int id_user)
    {
        Call<List<ModelTravelShow>> x = getRestApiClient().ModelTravelShow(id_user);
        return x;
    }

    public Call<ModelTravelAdd> TravelAdd(int id_user, int id_structure)
    {
        Call<ModelTravelAdd> x = getRestApiClient().ModelTravelAdd(id_user, id_structure);
        return x;
    }

}
