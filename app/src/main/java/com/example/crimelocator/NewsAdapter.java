package com.example.crimelocator;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    ArrayList<NewsData> newsData;
    Context context;

    public NewsAdapter(ArrayList<NewsData> newsData, NewsFeed activity) {
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
     final NewsData newsDataList = newsData.get(position);
     holder.tvNewsTitle.setText(newsDataList.getNewsTitle());
     holder.tvNewsTime.setText(newsDataList.getNewsTime());
     holder.imageViewCV.setImageBitmap(newsDataList.getNewsImage());

     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Toast.makeText(context,newsDataList.getNewsTitle(),Toast.LENGTH_SHORT).show();




             Intent intent = new Intent(context ,NewsDesc.class);
             intent.putExtra("TITLE",newsDataList.getNewsTitle())
                     .putExtra("DESC", newsDataList.getNewsDesc())
                     .putExtra("DATE", newsDataList.getNewsTime())
                     .putExtra("ID", newsDataList.getNewsId())
                     .putExtra("EMAIL", newsDataList.getEmail())
                     .putExtra("ADMIN", newsDataList.isAdmin());
             context.startActivity(intent);
         }

     });
    }

    @Override
    public int getItemCount() {
        return newsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewCV;
        TextView tvNewsTitle;
        TextView tvNewsTime;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageViewCV=itemView.findViewById(R.id.imageViewCV);
            tvNewsTitle =itemView.findViewById(R.id.textView1CV);
            tvNewsTime =itemView.findViewById(R.id.textView2CV);
        }

    }
}
