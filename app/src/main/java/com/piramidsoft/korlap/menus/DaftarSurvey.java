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

public class DaftarSurvey extends AppCompatActivity {

    @BindView(R.id.rvDaftarSurvey)
    RecyclerView rvDaftarSurvey;
    @BindView(R.id.Swipe)
    SwipeRefreshLayout Swipe;
    private ArrayList<ListModel> listModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_survey);
        ButterKnife.bind(this);
        rvDaftarSurvey.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(DaftarSurvey.this, 1,
                GridLayoutManager.VERTICAL, false);
        rvDaftarSurvey.setLayoutManager(layoutManager);
    }
}
