package com.piramidsoft.korlap.menus;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.piramidsoft.korlap.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailCollector extends AppCompatActivity {

    @BindView(R.id.ImgKtp)
    ImageView ImgKtp;
    @BindView(R.id.etNama)
    TextView etNama;
    @BindView(R.id.etNoHP)
    TextView etNoHP;
    @BindView(R.id.rvCollector)
    RecyclerView rvCollector;
    @BindView(R.id.Swipe)
    SwipeRefreshLayout Swipe;
    @BindView(R.id.rvSurvey)
    RecyclerView rvSurvey;
    @BindView(R.id.Swipe2)
    SwipeRefreshLayout Swipe2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_collector);
        ButterKnife.bind(this);
    }
}
