package net.burakkarahan.cityguide.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.burakkarahan.cityguide.Map.StructureDetail;
import net.burakkarahan.cityguide.Model.ModelFavoriteShow;
import net.burakkarahan.cityguide.Model.ModelTravelShow;
import net.burakkarahan.cityguide.R;

import java.util.List;

public class TravelShow extends BaseAdapter {

    List<ModelTravelShow> list;
    Context contex;
    Activity activity;

    public TravelShow(List<ModelTravelShow> list, Context contex, Activity activity) {
        this.list = list;
        this.contex = contex;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(contex).inflate(R.layout.travel_layout,parent,false);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        CardView travel_layout_cardview = (CardView) convertView.findViewById(R.id.travel_layout_cardview);

        name.setText("" + list.get(position).getName());
        date.setText("" + list.get(position).getDate());
        final String id_structure = "" + list.get(position).getId_structure();

        travel_layout_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = contex.getSharedPreferences("sharedPreferences",0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id_structure",id_structure);
                editor.commit();

                Intent i = new Intent(contex,StructureDetail.class);
                activity.startActivity(i);
            }
        });

        return convertView;
    }
}
