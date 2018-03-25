package com.piramidsoft.korlap.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piramidsoft.korlap.R;
import com.piramidsoft.korlap.menus.DetailClientSurvey;
import com.piramidsoft.korlap.models.ListModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */

public class DaftarSurveyAdapter extends RecyclerView.Adapter<DaftarSurveyAdapter.SurveyHolder> {

    private ArrayList<ListModel> listModels;
    private Context context;

    public DaftarSurveyAdapter(ArrayList<ListModel> listModels, Context context) {
        this.listModels = listModels;
        this.context = context;
    }

    @Override
    public SurveyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_daftar_sruvey, parent, false);
        return new SurveyHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyHolder holder, int position) {
        int nomer= position +1;
        holder.number.setText(String.valueOf(nomer));
        holder.Nama.setText(listModels.get(position).getNama());
        holder.Status.setText(listModels.get(position).getStatus());
        holder.Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailClientSurvey.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listModels.size();
    }

    public class SurveyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.Nama)
        TextView Nama;
        @BindView(R.id.Status)
        TextView Status;
        @BindView(R.id.Details)
        TextView Details;

        public SurveyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
