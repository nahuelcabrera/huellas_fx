package com.nahuelcabrera.huellas.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
    private Context context;

    public PetResultAdapter(List<Pet> results, OnPetListener onPetListener, Context context) {
        this.results = results;
        this.onPetListener = onPetListener;
        this.context = context;
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

        holder.cvContainer.setCardElevation(0.0F);

        Glide.with(context)
                .load("https://placedog.net/640/480?random")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .circleCrop()
                .into(holder.ivPicture);

    }


    @Override
    public int getItemCount() {
        return this.results.size();
    }
}


class PetResultViewHolder extends ViewHolder implements View.OnClickListener {

    //Bind views for data showing

    @BindView(R.id.cv_item_pet_result)
    CardView cvContainer;

    @BindView(R.id.tv_item_pet_name)
    TextView tvName;

    @BindView(R.id.tv_item_pet_code)
    TextView tvCode;

    @BindView(R.id.iv_item_pet_profile_picture)
    ImageView ivPicture;

    //
    PetResultAdapter.OnPetListener onPetListener;


    public PetResultViewHolder(@NonNull View itemView, PetResultAdapter.OnPetListener onPetListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.onPetListener = onPetListener;

        itemView.setOnClickListener(this);
    }

    public void updateWithPetResult(Pet pet){
        this.tvName.setText(pet.getName());
        this.tvCode.setText(pet.getId());

    }


    @Override
    public void onClick(View v) {
        onPetListener.onPetClick(getAdapterPosition());
    }
}
