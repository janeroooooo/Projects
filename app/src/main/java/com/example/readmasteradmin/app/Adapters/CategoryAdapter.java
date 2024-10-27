package com.example.readmasteradmin.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readmasteradmin.Models.CategoryModels;
import com.example.readmasteradmin.app.R;
import com.example.readmasteradmin.app.databinding.RvCategoryBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder>{

    Context context;
    ArrayList<CategoryModels>list;

    public CategoryAdapter(Context context, ArrayList<CategoryModels> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rv_category,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        CategoryModels model = list.get(position);
        holder.binding.categoryName.setText(model.getTitle());

        Picasso.get()
                .load(model.getImage())
                .placeholder(R.drawable.admin_logo)
                .into(holder.binding.categoryimage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        RvCategoryBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding = RvCategoryBinding.bind(itemView);
        }

    }
}
