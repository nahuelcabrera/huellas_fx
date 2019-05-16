package com.nahuelcabrera.huellas.controller;

import androidx.annotation.Nullable;

import com.nahuelcabrera.huellas.model.pojo.Pet;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetCalls {

    public interface Callbacks {
        void onResponse(@Nullable List<Pet> pets);
        void onFailure();
    }

    public static void fetchAvailablePets(Callbacks callbacks){

        /*final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);

        PetService petService = PetService.retrofit.create(PetService.class);

        Call<List<Pet>> call = petService.getPet();

        call.enqueue();*/
    }
}
