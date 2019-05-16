package com.nahuelcabrera.huellas.ui.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nahuelcabrera.huellas.R;
import com.nahuelcabrera.huellas.io.PetstoreApiAdapter;
import com.nahuelcabrera.huellas.model.pojo.Pet;
import com.nahuelcabrera.huellas.ui.ReviewActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nahuelcabrera.huellas.ui.ReviewActivity.PET_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class PetReviewFragment extends Fragment {

    @BindView(R.id.tv_review_name) TextView tvName;
    @BindView(R.id.tv_review_description) TextView tvDescription;

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

        Bundle bundle = getArguments();
        petID = bundle.getString(PET_ID);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvName.setText(petID);
        executePetRequest(petID);


    }

    private void executePetRequest(String id) {

        Call<Pet> pet = PetstoreApiAdapter.getPetstoreApiService().getPetForId(id);

        pet.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {

                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "Respuesta exitosa", Toast.LENGTH_SHORT).show();

                    updateUI(response.body());

                } else {
                    //srlResults.setRefreshing(false);
                    Toast.makeText(getContext(), "Respuesta inválida", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void updateUI(Pet body) {


        tvName.setText(body.getName());
    }

}
