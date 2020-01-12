package com.example.trendythreads.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trendythreads.R;
import com.example.trendythreads.AllModel.slidermodel;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class adapter extends SliderViewAdapter<adapter.SliderAdapterVH> {

    private Context context;
    ArrayList<slidermodel> arrayList;

    public adapter(Context context,ArrayList<slidermodel> arrayList)
    {
        this.context = context;
        this.arrayList=arrayList;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent)
    {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position)
    {
        slidermodel obj=arrayList.get(position);
        viewHolder.imageViewBackground.setImageResource(obj.getImg());
        viewHolder.textViewDescription.setText(obj.getText());


    }

    @Override
    public int getCount()
    {
        //slider view count could be dynamic size
        return arrayList.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder
    {

        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView)
        {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
        }
    }
}

