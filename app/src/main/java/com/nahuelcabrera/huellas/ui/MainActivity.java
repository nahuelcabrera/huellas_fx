package com.nahuelcabrera.huellas.ui;

import android.Manifest;
import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.nahuelcabrera.huellas.R;
import com.nahuelcabrera.huellas.io.PetstoreApiAdapter;
import com.nahuelcabrera.huellas.model.pojo.Pet;
import com.nahuelcabrera.huellas.ui.adapter.PetResultAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PetResultAdapter.OnPetListener {

    public static final String PET_ID_KEY = "PET_ID";
    private static final int REQUEST_LOCATION = 123;

    @BindView(R.id.rv_main_content)
    RecyclerView rvResults;
    @BindView(R.id.srl_main_content)
    SwipeRefreshLayout srlResults;


    //  DATA VARS

    private List<Pet> results;

    private PetResultAdapter petResultAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ActionBar ab = getSupportActionBar();

        ab
                .setElevation(0.0F);


        configureSwipeRefreshLayout();
        configureRecyclerView();
        executePetRequest();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||

                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||

                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,

                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,

                            Manifest.permission.ACCESS_COARSE_LOCATION,

                            Manifest.permission.BLUETOOTH,

                            Manifest.permission.BLUETOOTH_ADMIN}, REQUEST_LOCATION);

        } else {

            System.out.println("Location permissions available, starting location");

            Toast.makeText(this, "Location is ready to use", Toast.LENGTH_SHORT).show();

        }


    }

    @Override

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if(requestCode == REQUEST_LOCATION) {

            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                System.out.println("Location permissions granted, starting location");

                Toast.makeText(this, "Location is ready to use", Toast.LENGTH_SHORT).show();

            }

        }

    }

    private void configureSwipeRefreshLayout() {
        srlResults.setOnRefreshListener(()-> executePetRequest());
    }

    private void configureRecyclerView(){
        this.results = new ArrayList<>();
        this.petResultAdapter = new PetResultAdapter(results, this, this);
        this.rvResults.setAdapter(this.petResultAdapter);
        this.rvResults.setLayoutManager(new LinearLayoutManager(this));
    }

    private void executePetRequest() {

        Call<List<Pet>> pets = PetstoreApiAdapter.getPetstoreApiService().getAvailablePets();

        pets.enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {


                if(response.isSuccessful()){
                    Toast.makeText(getBaseContext(), "Respuesta exitosa", Toast.LENGTH_SHORT).show();
                    srlResults.setRefreshing(false);
                    updateUI(response.body());

                } else {
                    srlResults.setRefreshing(false);
                    Toast.makeText(getBaseContext(), "Sin respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                srlResults.setRefreshing(false);
                Toast.makeText(getBaseContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updateUI(List<Pet> data) {
        //Clear pets stored data
        results.clear();
        //Add new data
        results.addAll(data);
        //Notify changes
        petResultAdapter.notifyDataSetChanged();
    }


    @Override
    public void onPetClick(int position) {
        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra(PET_ID_KEY, results.get(position).getId());
        startActivity(intent);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        executePetRequest();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
