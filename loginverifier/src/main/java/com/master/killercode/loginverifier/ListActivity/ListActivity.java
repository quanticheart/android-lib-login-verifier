package com.master.killercode.loginverifier.ListActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.master.killercode.loginverifier.LoginVerifier;
import com.master.killercode.loginverifier.R;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private Activity activity;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<UserModel> dataBase = new ArrayList<>();
    private Adapter adapter;
    private Bundle bundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        initVars();
        initActions();
    }

    private void initVars() {
        bundle = getIntent().getExtras();
        activity = ListActivity.this;
        recyclerView = findViewById(R.id.rv);
        swipeRefreshLayout = findViewById(R.id.sfl);
    }

    private void initActions() {

        GetDataBase getDataBase = new GetDataBase(activity);
        dataBase = getDataBase.getList();

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Class nextIntent = (Class) bundle.get("class");
        adapter = new Adapter(activity, dataBase , nextIntent);
        recyclerView.setAdapter(adapter);

        Log.w("Array", dataBase.toString());

    }

}

