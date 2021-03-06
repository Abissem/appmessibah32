package com.github.appmessibah32.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.github.appmessibah32.R;
import com.github.appmessibah32.Singletons;
import com.github.appmessibah32.presentation.controller.MainController;
import com.github.appmessibah32.presentation.model.Dragonball;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new MainController(
                this,
                 Singletons.getGson(),
                 Singletons.getSharedPreferences(getApplicationContext()),
                this

        );
        controller.onStart();
    }

    public void showList(List<Dragonball> characterList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(characterList, new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Dragonball item) {
                controller.onItemClick(item);
            }
        }, this);
        recyclerView.setAdapter(mAdapter);
    }

    public void showError() {
        Toast.makeText(this, "API Error", Toast.LENGTH_SHORT).show();
    }

    public void navigateToDetails(Dragonball dragonball) {
        Intent myIntent = new Intent(MainActivity.this, DetailActivity.class);
        myIntent.putExtra("dragonballKey", Singletons.getGson().toJson(dragonball));
        MainActivity.this.startActivity(myIntent);
    }
}