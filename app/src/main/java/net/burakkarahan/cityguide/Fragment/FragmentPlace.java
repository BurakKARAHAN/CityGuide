package net.burakkarahan.cityguide.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.burakkarahan.cityguide.Account.Login;
import net.burakkarahan.cityguide.Adapter.StructureSummary;
import net.burakkarahan.cityguide.Model.ModelStructure;
import net.burakkarahan.cityguide.R;
import net.burakkarahan.cityguide.RestApi.ManagerAll;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPlace extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    ListView StructureListView;
    List<ModelStructure> list;
    StructureSummary structureSummary;
    private SwipeRefreshLayout yenileme_nesnesi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_place, container, false);

        Structure();

        StructureListView = (ListView) view.findViewById(R.id.StruvtureListView);

        yenileme_nesnesi = (SwipeRefreshLayout) view.findViewById(R.id.yenileme_nesnesi); // nesnemizi tanıttık
        yenileme_nesnesi.setOnRefreshListener(this);



        return view;
    }

    public void Structure()
    {
        list = new ArrayList<>();

        Call<List<ModelStructure>> request = ManagerAll.getInstance().Structure();
        request.enqueue(new Callback<List<ModelStructure>>() {
            @Override
            public void onResponse(Call<List<ModelStructure>> call, Response<List<ModelStructure>> response) {

                if (response.isSuccessful())
                {
                    list = response.body();
                    structureSummary = new StructureSummary(list,getContext(),getActivity());
                    StructureListView.setAdapter(structureSummary);
//                    pDialog.cancel();
                }

            }

            @Override
            public void onFailure(Call<List<ModelStructure>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onRefresh() {
        Structure();
        yenileme_nesnesi.setRefreshing(false);
    }
}

