package com.piramidsoft.korlap.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piramidsoft.korlap.R;
import com.piramidsoft.korlap.menus.DetailClientPenagihan;
import com.piramidsoft.korlap.models.ListModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**

 */

public class PenagihanAdapter extends RecyclerView.Adapter<PenagihanAdapter.PenagihanHolder> {

    private ArrayList<ListModel> arrayList;
    private Context context;

    public PenagihanAdapter(ArrayList<ListModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public PenagihanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_tabel_penagihan, parent, false);
        return new PenagihanHolder(view);
    }

    @Override
    public void onBindViewHolder(PenagihanHolder holder, int position) {
        int nomer= position +1;

        holder.number.setText(String.valueOf(nomer));
        holder.Nama.setText(arrayList.get(position).getNama());
        holder.Status.setText(arrayList.get(position).getStatus());
        holder.Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailClientPenagihan.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class PenagihanHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.Nama)
        TextView Nama;
        @BindView(R.id.Status)
        TextView Status;
        @BindView(R.id.Details)
        TextView Details;

        public PenagihanHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
