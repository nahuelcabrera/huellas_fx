package com.nahuelcabrera.huellas.ui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

import com.nahuelcabrera.huellas.MainActivity;
import com.nahuelcabrera.huellas.R;
import com.nahuelcabrera.huellas.ui.fragment.PetLocationFragment;
import com.nahuelcabrera.huellas.ui.fragment.PetReviewFragment;

public class ReviewActivity extends AppCompatActivity {


    public static final String PET_ID = "REVIEW_ACTIVITY_PET_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        //GET THE ID PET
        Intent receiver = getIntent();
        String petID = receiver.getStringExtra(MainActivity.PET_ID);

        //MANAGE FRAGMENTS
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        PetReviewFragment petReviewFragment = new PetReviewFragment();
        PetLocationFragment petLocationFragment = new PetLocationFragment();

        //Create bundle to store data
        Bundle idBundle = new Bundle();
        idBundle.putString(PET_ID, petID);

        //Send data
        petReviewFragment.setArguments(idBundle);
        petLocationFragment.setArguments(idBundle);


        transaction.add(R.id.fl_review, petReviewFragment);
        transaction.add(R.id.fl_map, petLocationFragment);
        transaction.commit();
        fm.beginTransaction();
    }
}
