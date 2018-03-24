package com.piramidsoft.korlap.menus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.piramidsoft.korlap.R;
import com.piramidsoft.korlap.models.NotesModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClientNotes extends AppCompatActivity {

    @BindView(R.id.rvNotes)
    RecyclerView rvNotes;
    private ArrayList<NotesModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_notes);
        ButterKnife.bind(this);
        rvNotes.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(ClientNotes.this, 1,
                GridLayoutManager.VERTICAL, false);
        rvNotes.setLayoutManager(layoutManager);
    }
}
