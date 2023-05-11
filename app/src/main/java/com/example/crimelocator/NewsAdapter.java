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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    NewsData[] newsData;
    Context context;

    public NewsAdapter(NewsData[] newsData, NewsFeed activity) {
        this.newsData=newsData;
        this.context=activity;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.activity_card_list_news_feed,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     final NewsData newsDatalist=newsData[position];
     holder.textView1CV.setText(newsDatalist.getNewsDescription());
     holder.textView2Cv.setText(newsDatalist.getNewsTime());
     holder.imageViewCV.setImageResource(newsDatalist.getNewsImage());

     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Toast.makeText(context,newsDatalist.getNewsDescription(),Toast.LENGTH_SHORT).show();
         }
     });
    }

    @Override
    public int getItemCount() {
        return newsData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewCV;
        TextView textView1CV;
        TextView textView2Cv;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageViewCV=itemView.findViewById(R.id.imageViewCV);
            textView1CV=itemView.findViewById(R.id.textView1CV);
            textView2Cv=itemView.findViewById(R.id.textView2CV);
        }
    }
}
