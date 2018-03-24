package com.piramidsoft.korlap.menus;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.piramidsoft.korlap.R;
import com.piramidsoft.korlap.models.ListCollectorModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListCollector extends AppCompatActivity {

    @BindView(R.id.rvListCollector)
    RecyclerView rvListCollector;
    @BindView(R.id.Swipe)
    SwipeRefreshLayout Swipe;
    private ArrayList<ListCollectorModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_collector);
        ButterKnife.bind(this);

        GridLayoutManager layoutManager = new GridLayoutManager(ListCollector.this, 1,
                GridLayoutManager.VERTICAL, false);
        rvListCollector.setLayoutManager(layoutManager);
    }
}
