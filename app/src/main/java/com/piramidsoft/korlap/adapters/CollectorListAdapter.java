package com.piramidsoft.korlap.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piramidsoft.korlap.R;
import com.piramidsoft.korlap.menus.DetailCollector;
import com.piramidsoft.korlap.models.ListCollectorModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */

public class CollectorListAdapter extends RecyclerView.Adapter<CollectorListAdapter.ColHolder> {

    private ArrayList<ListCollectorModel> arrayList;
    private Context context;

    public CollectorListAdapter(ArrayList<ListCollectorModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public ColHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_collector, parent, false);
        return new ColHolder(view);
    }

    @Override
    public void onBindViewHolder(ColHolder holder, int position) {
        int nomer= position +1;
        holder.etNumber.setText(nomer);
        holder.etNama.setText(arrayList.get(position).getNama());
        holder.etJmlCol.setText(arrayList.get(position).getJmlcol());
        holder.etJmlSur.setText(arrayList.get(position).getJmlsur());
        holder.etDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(context, DetailCollector.class);
            context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ColHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.etNumber)
        TextView etNumber;
        @BindView(R.id.etNama)
        TextView etNama;
        @BindView(R.id.etJmlCol)
        TextView etJmlCol;
        @BindView(R.id.etJmlSur)
        TextView etJmlSur;
        @BindView(R.id.etDetails)
        TextView etDetails;

        public ColHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
