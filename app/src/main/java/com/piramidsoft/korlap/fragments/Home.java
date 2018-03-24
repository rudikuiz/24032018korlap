package com.piramidsoft.korlap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.piramidsoft.korlap.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Home extends Fragment {

    @BindView(R.id.etUser)
    TextView etUser;
    @BindView(R.id.ic_home)
    ImageView icHome;
    @BindView(R.id.t_total)
    TextView tTotal;
    @BindView(R.id.etJmlDP)
    TextView etJmlDP;
    @BindView(R.id.btDaftarPenagihan)
    LinearLayout btDaftarPenagihan;
    @BindView(R.id.etJmlDS)
    TextView etJmlDS;
    @BindView(R.id.btTotalSurvey)
    LinearLayout btTotalSurvey;
    @BindView(R.id.etDaftarCol)
    ImageView etDaftarCol;
    @BindView(R.id.btDaftarCol)
    LinearLayout btDaftarCol;
    Unbinder unbinder;

    public Home() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btDaftarPenagihan, R.id.btTotalSurvey, R.id.btDaftarCol})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btDaftarPenagihan:
                break;
            case R.id.btTotalSurvey:
                break;
            case R.id.btDaftarCol:
                break;
        }
    }
}
