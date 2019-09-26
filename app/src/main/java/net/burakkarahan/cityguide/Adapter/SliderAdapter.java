package net.burakkarahan.cityguide.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import net.burakkarahan.cityguide.Model.ModelStructureImage;
import net.burakkarahan.cityguide.R;

import java.util.List;

public class SliderAdapter extends PagerAdapter {

    List<ModelStructureImage> list;
    Context contex;
    LayoutInflater layoutInflater;

    public SliderAdapter(List<ModelStructureImage> list, Context contex) {
        this.list = list;
        this.contex = contex;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (LinearLayout) o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) contex.getSystemService(contex.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout,container,false);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        Picasso.with(contex).load("http://www.cityguide.ml/StructureImage/" + list.get(position).getImage()).resize(1400,800).into(imageView);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

}
