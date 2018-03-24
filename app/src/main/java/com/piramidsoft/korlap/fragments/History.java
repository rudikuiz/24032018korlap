package com.piramidsoft.korlap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.piramidsoft.korlap.R;
import com.piramidsoft.korlap.adapters.HistoryAdapter;
import com.piramidsoft.korlap.menus.DaftarSurvey;
import com.piramidsoft.korlap.models.HistoryModel;
import com.piramidsoft.korlap.models.ListModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class History extends Fragment {


    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;
    @BindView(R.id.Swipe)
    SwipeRefreshLayout Swipe;
    Unbinder unbinder;
    private HistoryAdapter adapter;
    private ArrayList<HistoryModel> dataSet = new ArrayList<>();


    public History() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_history, container, false);
        unbinder = ButterKnife.bind(this, view);

//        rvHistory.setHasFixedSize(true);
rvHistory.setHasFixedSize(true);


        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1,
                GridLayoutManager.VERTICAL, false);
        rvHistory.setLayoutManager(layoutManager);
        adapter = new HistoryAdapter(dataSet, getActivity());
        rvHistory.setAdapter(adapter);
        getData();

        return view;
    }

    private void getData() {

        dataSet.add(new HistoryModel("21/04/2017","Rp 100.000,-"));
        dataSet.add(new HistoryModel("29/04/2017","Rp 200.000,-"));
        dataSet.add(new HistoryModel("25/04/2017","Rp 100.000,-"));
        dataSet.add(new HistoryModel("20/04/2017","Rp 100.000,-"));
        dataSet.add(new HistoryModel("18/04/2017","Rp 100.000,-"));
        dataSet.add(new HistoryModel("13/04/2017","Rp 200.000,-"));

        Log.d("TOTALSSS", dataSet.size()+" cxxx");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
