package com.example.crimelocator;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    ArrayList<UserData> userData;
    Context context;
    String id;
    public UserAdapter(ArrayList<UserData> userData, Context context, String id) {
        this.userData=userData;
        this.context=context;
        this.id = id;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.helped_user_text_view,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final UserData userDataList= userData.get(position);
        holder.helpedUserEmail.setText(userDataList.getUser());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Helped User Gallery",Toast.LENGTH_SHORT).show();
                String email = userDataList.getUser();
                Intent intent = new Intent(context, HelpedUserListGallery.class);
                Toast.makeText(context, "Id:" + id, Toast.LENGTH_SHORT).show();
                intent.putExtra("EMAIL", email);
                intent.putExtra("ID", id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView helpedUserEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            helpedUserEmail=itemView.findViewById(R.id.helpedUserEmail);
        }
    }
}
