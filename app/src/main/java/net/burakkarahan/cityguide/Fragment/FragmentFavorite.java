package net.burakkarahan.cityguide.Fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.burakkarahan.cityguide.Adapter.FavoriteShow;
import net.burakkarahan.cityguide.Adapter.StructureSummary;
import net.burakkarahan.cityguide.Model.ModelFavoriteShow;
import net.burakkarahan.cityguide.Model.ModelStructure;
import net.burakkarahan.cityguide.R;
import net.burakkarahan.cityguide.RestApi.ManagerAll;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFavorite extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    ListView FavoriteListView;
    FavoriteShow favoriteShow;
    List<ModelFavoriteShow> modelFavoriteShow;
    SharedPreferences sharedPreferences;
    private SwipeRefreshLayout yenileme_nesnesi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_favorite,container,false);

        //Toast.makeText(FragmentFavorite.this, "Favorite Screen:", Toast.LENGTH_LONG).show();

        FavoriteListView = (ListView) view.findViewById(R.id.FavoriteListView);
        Favorite();

        yenileme_nesnesi = (SwipeRefreshLayout) view.findViewById(R.id.yenileme_nesnesi); // nesnemizi tanıttık
        yenileme_nesnesi.setOnRefreshListener(this);

        return view;
    }

    public void Favorite()
    {
        sharedPreferences = getContext().getSharedPreferences("login",0);
        final int id_user = Integer.valueOf(sharedPreferences.getString("id_user",""));

        modelFavoriteShow = new ArrayList<>();

        Call<List<ModelFavoriteShow>> request = ManagerAll.getInstance().FavoriteShow(id_user);
        request.enqueue(new Callback<List<ModelFavoriteShow>>() {
            @Override
            public void onResponse(Call<List<ModelFavoriteShow>> call, Response<List<ModelFavoriteShow>> response) {

                if (response.isSuccessful())
                {
                    modelFavoriteShow = response.body();
                    favoriteShow = new FavoriteShow(modelFavoriteShow,getContext(),getActivity());
                    FavoriteListView.setAdapter(favoriteShow);
//                    pDialog.cancel();
                }

            }

            @Override
            public void onFailure(Call<List<ModelFavoriteShow>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onRefresh() {
        Favorite();
        yenileme_nesnesi.setRefreshing(false);
    }
}
