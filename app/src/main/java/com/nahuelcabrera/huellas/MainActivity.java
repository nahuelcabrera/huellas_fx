package com.nahuelcabrera.huellas;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.nahuelcabrera.huellas.controller.PetStreams;
import com.nahuelcabrera.huellas.io.PetstoreApiAdapter;
import com.nahuelcabrera.huellas.model.pojo.Pet;
import com.nahuelcabrera.huellas.ui.ReviewActivity;
import com.nahuelcabrera.huellas.ui.adapter.PetResultAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PetResultAdapter.OnPetListener {

    public static final String PET_ID = "PET_ID_ACTIVITY_MAIN";

    @BindView(R.id.rv_results)
    RecyclerView rvResults;
    @BindView(R.id.srl_results)
    SwipeRefreshLayout srlResults;


    //  DATA

    private Disposable disposable;

    private List<Pet> results;

    private PetResultAdapter petResultAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        configureSwipeRefreshLayout();
        configureRecyclerView();
        executePetRequest();


    }

    private void configureSwipeRefreshLayout() {
        srlResults.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executePetRequest();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposeWhenDestroy();
    }

    private void configureRecyclerView(){
        this.results = new ArrayList<>();
        this.petResultAdapter = new PetResultAdapter(results, this);
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

                    updateUI(response.body());

                } else {
                    srlResults.setRefreshing(false);
                    Toast.makeText(getBaseContext(), "Respuesta inválida", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                srlResults.setRefreshing(false);
                Toast.makeText(getBaseContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

        /*this.disposable = PetStreams.streamFetchAvailablePets().subscribeWith(new DisposableObserver<List<Pet>>() {
            @Override
            public void onNext(List<Pet> pets) {
                updateUI(pets);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("FETCH ERROR", "On Error " + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("FETCH SUCCESS", "Complete");
            }
        });*/



    }

    private void updateUI(List<Pet> data) {
        //Stop refreshing
        srlResults.setRefreshing(false);
        //Clear pets stored data
        results.clear();
        //Add new data
        results.addAll(data);
        //Notify changes
        petResultAdapter.notifyDataSetChanged();
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    @Override
    public void onPetClick(int position) {

        Toast.makeText(this, "Item with position "+position + " with id "+ results.get(position).getId() +" is calling his review", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, ReviewActivity.class);

        intent.putExtra(PET_ID, results.get(position).getId());

        startActivity(intent);
    }
}
