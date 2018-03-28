package com.piramidsoft.korlap.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piramidsoft.korlap.R;
import com.piramidsoft.korlap.menus.DetailClient;
import com.piramidsoft.korlap.models.ListModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Muhammad on 3/25/2018.
 */

public class ColSurveyAdapter extends RecyclerView.Adapter<ColSurveyAdapter.ColSurHolder> {

    private ArrayList<ListModel> models;
    private Context context;

    public ColSurveyAdapter(ArrayList<ListModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public ColSurHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_survey_col, parent, false);
        return new ColSurHolder(view);
    }

    @Override
    public void onBindViewHolder(ColSurHolder holder, final int position) {
        int number = position + 1;
        holder.number.setText(String.valueOf(number));
        holder.etNama.setText(models.get(position).getNama());
        holder.etStatus.setText(models.get(position).getStatus());
        holder.etJanjiBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailClient.class);
                intent.putExtra("cli_id", models.get(position).getCli_id());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ColSurHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.etNama)
        TextView etNama;
        @BindView(R.id.etStatus)
        TextView etStatus;
        @BindView(R.id.etJanjiBayar)
        TextView etJanjiBayar;
        public ColSurHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
