package com.nahuelcabrera.huellas.controller;

import com.nahuelcabrera.huellas.model.pojo.Pet;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface PetService {


    Observable<List<Pet>> petsObservable = Observable.create(new ObservableOnSubscribe<List<Pet>>() {
        @Override
        public void subscribe(ObservableEmitter<List<Pet>> emitter) throws Exception {
            List<Pet> pets = new ArrayList<>();

            pets.add(new Pet("432432", "ñandu"));

            emitter.onNext(pets);

            pets.add(new Pet("2321", "Kaoras" ));

            emitter.onNext(pets);

            emitter.onComplete();
        }
    });

    /*static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://petstore.swagger.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .client()
            .build();*/

}
