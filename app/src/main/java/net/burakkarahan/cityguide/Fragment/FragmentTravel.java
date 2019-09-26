package net.burakkarahan.cityguide.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.burakkarahan.cityguide.Adapter.FavoriteShow;
import net.burakkarahan.cityguide.Adapter.TravelShow;
import net.burakkarahan.cityguide.Model.ModelFavoriteShow;
import net.burakkarahan.cityguide.Model.ModelTravelShow;
import net.burakkarahan.cityguide.R;
import net.burakkarahan.cityguide.RestApi.ManagerAll;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTravel extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    ListView TravelListView;
    TravelShow travelShow;
    List<ModelTravelShow> modelTravelShow;
    SharedPreferences sharedPreferences;
    private SwipeRefreshLayout yenileme_nesnesi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_travel,container,false);

        TravelListView = (ListView) view.findViewById(R.id.TravelListView);
        Travel();

        yenileme_nesnesi = (SwipeRefreshLayout) view.findViewById(R.id.yenileme_nesnesi); // nesnemizi tanıttık
        yenileme_nesnesi.setOnRefreshListener(this);

        return view;
    }

    public void Travel()
    {

//        final SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        pDialog.setTitleText("Loading");
//        pDialog.setCancelable(false);
//        pDialog.show();

        sharedPreferences = getContext().getSharedPreferences("login",0);
        final int id_user = Integer.valueOf(sharedPreferences.getString("id_user",""));

        modelTravelShow = new ArrayList<>();

        Call<List<ModelTravelShow>> request = ManagerAll.getInstance().TravelShow(id_user);
        request.enqueue(new Callback<List<ModelTravelShow>>() {
            @Override
            public void onResponse(Call<List<ModelTravelShow>> call, Response<List<ModelTravelShow>> response) {

                if (response.isSuccessful())
                {
                    modelTravelShow = response.body();
                    travelShow = new TravelShow(modelTravelShow,getContext(),getActivity());
                    TravelListView.setAdapter(travelShow);
//                    pDialog.cancel();
                }

            }

            @Override
            public void onFailure(Call<List<ModelTravelShow>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onRefresh() {
        Travel();
        yenileme_nesnesi.setRefreshing(false);
    }

}
