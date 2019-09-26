package net.burakkarahan.cityguide.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.burakkarahan.cityguide.Map.StructureDetail;
import net.burakkarahan.cityguide.Model.ModelStructure;
import net.burakkarahan.cityguide.R;

import java.util.List;

public class StructureSummary extends BaseAdapter {

    public List<ModelStructure> StructureList;
    public Context contex;
    public Activity activity;
    ImageView favorite, non_favorite;

    public StructureSummary(List<ModelStructure> structureList, Context contex,Activity activity) {
        StructureList = structureList;
        this.contex = contex;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return StructureList.size();
    }

    @Override
    public Object getItem(int position) {
        return StructureList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(contex).inflate(R.layout.place_layout,parent,false);
        final ImageView image = (ImageView) convertView.findViewById(R.id.image);
        CardView cardView=convertView.findViewById(R.id.place_layout_cardview);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView city_county = (TextView) convertView.findViewById(R.id.city_county);
        LinearLayout place_linearlayout = convertView.findViewById(R.id.place_layout);
        favorite = (ImageView) convertView.findViewById(R.id.favorite);
        non_favorite = (ImageView) convertView.findViewById(R.id.non_favorite);


        name.setText("" + StructureList.get(position).getName());
        city_county.setText("" + StructureList.get(position).getCity() + "/" + StructureList.get(position).getCounty());
        Picasso.with(contex).load("http://www.cityguide.ml/StructureImage/" + StructureList.get(position).getImage()).resize(1400,800).into(image);
        final String id_structure = "" + StructureList.get(position).getIdStructure();

        cardView.setOnClickListener(new View.OnClickListener() {
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
