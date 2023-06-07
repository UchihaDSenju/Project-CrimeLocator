package com.example.crimelocator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class galleryAdapter extends RecyclerView.Adapter<galleryAdapter.ViewHolder>{
    ArrayList<galleryData> gallery;
    Context context;
    public galleryAdapter(ArrayList<galleryData> gallery, Context context) {
     this.gallery = gallery;
     this.context=context;

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

      final galleryData galleryDataList= gallery.get(position);
      holder.galleryImage.setImageBitmap(galleryDataList.getGalleryImage());
      holder.galleryDesc.setText(galleryDataList.getDesc());

      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Toast.makeText(context,"image clicked",Toast.LENGTH_SHORT).show();
          }
      });



    }

    @Override
    public int getItemCount() {
        return gallery.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView galleryImage;
        TextView galleryDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            galleryImage =itemView.findViewById(R.id.galleryImg);
            galleryDesc=itemView.findViewById(R.id.galleryDesc);

        }
    }
}
