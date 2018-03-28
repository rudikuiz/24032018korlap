package com.piramidsoft.korlap.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piramidsoft.korlap.R;
import com.piramidsoft.korlap.models.CatatanModel;
import com.piramidsoft.korlap.models.NotesModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**

 */

public class ClientNotesAdapter extends RecyclerView.Adapter<ClientNotesAdapter.NotesHolder> {

    private ArrayList<CatatanModel> arrayList;
    private Context context;

    public ClientNotesAdapter(ArrayList<CatatanModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public NotesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_client_notes, parent, false);
        return new NotesHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesHolder holder, int position) {
        holder.etTanggal.setText(arrayList.get(position).getTxtTanggal());
        holder.etKet.setText(arrayList.get(position).getXxx());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class NotesHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.etTanggal)
        TextView etTanggal;
        @BindView(R.id.etKet)
        TextView etKet;
        public NotesHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
