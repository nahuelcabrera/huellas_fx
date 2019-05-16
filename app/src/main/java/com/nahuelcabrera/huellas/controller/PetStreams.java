package com.nahuelcabrera.huellas.controller;

import com.nahuelcabrera.huellas.model.pojo.Pet;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class PetStreams {



    /*public static Observable<List<Pet>> streamFetchAvailablePets(){





        PetService petService = PetService.retrofit.create(PetService.class);
        return petService.getPet(new Pet("a","a"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }*/

}
