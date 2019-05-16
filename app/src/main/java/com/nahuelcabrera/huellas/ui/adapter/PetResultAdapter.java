package com.nahuelcabrera.huellas.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.nahuelcabrera.huellas.R;
import com.nahuelcabrera.huellas.model.pojo.Pet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetResultAdapter extends RecyclerView.Adapter<PetResultViewHolder> {

    public interface OnPetListener{
        void onPetClick(int position);
    }

    private List<Pet> results;
    private OnPetListener onPetListener;

    public PetResultAdapter(List<Pet> results, OnPetListener onPetListener) {
        this.results = results;
        this.onPetListener = onPetListener;
    }

    @NonNull
    @Override
    public PetResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_pet_result, parent,false);

        return new PetResultViewHolder(view, onPetListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PetResultViewHolder holder, int position) {
        holder.updateWithPetResult(this.results.get(position));

    }


    @Override
    public int getItemCount() {
        return this.results.size();
    }
}


class PetResultViewHolder extends ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_item_pet_result_title)
    TextView tvTitle;

    //
    PetResultAdapter.OnPetListener onPetListener;


    public PetResultViewHolder(@NonNull View itemView, PetResultAdapter.OnPetListener onPetListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.onPetListener = onPetListener;

        itemView.setOnClickListener(this);
    }

    public void updateWithPetResult(Pet pet){
        this.tvTitle.setText(pet.getName());
    }


    @Override
    public void onClick(View v) {
        onPetListener.onPetClick(getAdapterPosition());
    }
}
