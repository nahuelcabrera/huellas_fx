package com.nahuelcabrera.huellas.io;

import com.nahuelcabrera.huellas.model.pojo.Pet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PetstoreApiService {

    @GET("v2/pet/findByStatus?status=available")
    Call<List<Pet>> getAvailablePets();

    @GET("v2/pet/{id}")
    Call<Pet> getPetForId(@Path("id") String id);

}
