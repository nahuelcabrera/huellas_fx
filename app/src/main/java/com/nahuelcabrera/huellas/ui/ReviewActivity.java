package com.nahuelcabrera.huellas.ui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.nahuelcabrera.huellas.R;
import com.nahuelcabrera.huellas.ui.fragment.PetLocationFragment;
import com.nahuelcabrera.huellas.ui.fragment.PetReviewFragment;

public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        getSupportActionBar().setElevation(0.0F);
        getSupportActionBar().setTitle(R.string.title_review_screen);

        //GET PET ID
        Intent receiver = getIntent();
        String petID = receiver.getStringExtra(MainActivity.PET_ID_KEY);

        //MANAGE FRAGMENTS
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        PetReviewFragment petReviewFragment = new PetReviewFragment();
        PetLocationFragment petLocationFragment = new PetLocationFragment();

        //Create a bundle to save the id
        Bundle idBundle = new Bundle();
        idBundle.putString(MainActivity.PET_ID_KEY, petID);

        //Send data
        petReviewFragment.setArguments(idBundle);
        petLocationFragment.setArguments(idBundle);


        transaction.add(R.id.fl_map, petLocationFragment);
        transaction.add(R.id.fl_review, petReviewFragment);

        transaction.commit();
        fm.beginTransaction();


    }

}
