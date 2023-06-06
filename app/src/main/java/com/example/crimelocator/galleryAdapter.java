package com.example.crimelocator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class galleryAdapter extends RecyclerView.Adapter<galleryAdapter.ViewHolder>{
    galleryData[]  galleryData;
    Context context;
    public galleryAdapter(galleryData[] galleryData,NewsDesc newsDesc) {
     this.galleryData=galleryData;
     this.context=newsDesc;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.galley_view,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

      final galleryData galleryDataList= galleryData[position];
      holder.galleryImage.setImageResource(galleryDataList.getGalleryImage());

      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Toast.makeText(context,"image clicked",Toast.LENGTH_SHORT).show();
          }
      });



    }

    @Override
    public int getItemCount() {
        return galleryData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView galleryImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            galleryImage =itemView.findViewById(R.id.galleryImg);
        }
    }
}
