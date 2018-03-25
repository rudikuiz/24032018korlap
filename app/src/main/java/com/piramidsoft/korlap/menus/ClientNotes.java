package com.piramidsoft.korlap.menus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.piramidsoft.korlap.R;
import com.piramidsoft.korlap.Setting.OwnProgressDialog;
import com.piramidsoft.korlap.adapters.ClientNotesAdapter;
import com.piramidsoft.korlap.models.NotesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.piramidsoft.korlap.Config.AppConf.URL_GET_CATATAN;

public class ClientNotes extends AppCompatActivity {

    @BindView(R.id.rvNotes)
    RecyclerView rvNotes;
    private ArrayList<NotesModel> arrayList = new ArrayList<>();
    RequestQueue requestQueue;
    StringRequest stringRequest;
    OwnProgressDialog loading;

    String client_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_notes);
        ButterKnife.bind(this);
        rvNotes.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(ClientNotes.this, 1,
                GridLayoutManager.VERTICAL, false);
        rvNotes.setLayoutManager(layoutManager);
        requestQueue = Volley.newRequestQueue(ClientNotes.this);
        loading = new OwnProgressDialog(ClientNotes.this);

        loading.show();

        getJSON();
    }

    private void getJSON() {

        arrayList = new ArrayList<NotesModel>();

        stringRequest = new StringRequest(Request.Method.GET, URL_GET_CATATAN + "nothing", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("response_catatan: ", response);
                Log.d("catatan url ", URL_GET_CATATAN + client_id);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        NotesModel modelCatatan = new NotesModel();
                        modelCatatan.setTgl(json.getString("ajunote_catatan"));
                        modelCatatan.setNotes(json.getString("ajunote_waktu"));

                        arrayList.add(modelCatatan);
                    }

                    ClientNotesAdapter adapter = new ClientNotesAdapter(arrayList, ClientNotes.this);
                    rvNotes.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loading.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                loading.dismiss();

            }
        });
        requestQueue.add(stringRequest);
    }
}
