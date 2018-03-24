package com.piramidsoft.korlap.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piramidsoft.korlap.R;
import com.piramidsoft.korlap.models.HistoryModel;
import com.piramidsoft.korlap.models.ListModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.SurveyHolder> {

    private ArrayList<HistoryModel> dataSet;
    private Context context;

    public HistoryAdapter(ArrayList<HistoryModel> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
    }

    @Override
    public SurveyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_history, parent, false);
        return new SurveyHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyHolder holder, int position) {

        holder.txTgl.setText(dataSet.get(position).getTgl());
        holder.txNominal.setText(dataSet.get(position).getNominal());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class SurveyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txTgl)
        TextView txTgl;
        @BindView(R.id.txNominal)
        TextView txNominal;


        public SurveyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
