package com.piramidsoft.korlap.menus;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.piramidsoft.korlap.R;
import com.piramidsoft.korlap.models.ListModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DaftarPenagihan extends AppCompatActivity {


    @BindView(R.id.Swipe)
    SwipeRefreshLayout Swipe;
    @BindView(R.id.rvDaftarPenagihan)
    RecyclerView rvDaftarPenagihan;
    private ArrayList<ListModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_penagihan);
        ButterKnife.bind(this);
        rvDaftarPenagihan.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(DaftarPenagihan.this, 1,
                GridLayoutManager.VERTICAL, false);
        rvDaftarPenagihan.setLayoutManager(layoutManager);

    }
}
