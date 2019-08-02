package com.nahuelcabrera.huellas.ui.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nahuelcabrera.huellas.R;
import com.nahuelcabrera.huellas.io.PetstoreApiAdapter;
import com.nahuelcabrera.huellas.model.pojo.Pet;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nahuelcabrera.huellas.ui.MainActivity.PET_ID_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class PetReviewFragment extends Fragment {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_code)
    TextView tvBarcode;
    @BindView(R.id.iv_profile_picture)
    ImageView ivPicture;

    private String petID;


    public PetReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pet_review, container, false);
        ButterKnife.bind(this, view);

        //GET PET ID
        Bundle bundle = getArguments();
        petID = bundle.getString(PET_ID_KEY);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        executePetRequest(petID, view);


    }

    private void executePetRequest(String id, View view) {

        Call<Pet> pet = PetstoreApiAdapter.getPetstoreApiService().getPetForId(id);

        pet.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {

                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Mascota encontrada", Toast.LENGTH_SHORT).show();

                    updateUI(response.body(), view);

                } else {
                    Toast.makeText(getActivity(), "Sin respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void updateUI(Pet body, View view) {

        tvName.setText(body.getName().toUpperCase());
        //TODO
        //tvCategory.setText("TO DO");
        tvBarcode.setText(body.getId());
        tvStatus.setText(body.getStatus());
        Glide.with(view).load("https://placedog.net/640/480?random").circleCrop().into(ivPicture);
    }

}
