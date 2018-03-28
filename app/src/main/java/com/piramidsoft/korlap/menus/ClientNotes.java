package com.piramidsoft.korlap.menus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.piramidsoft.korlap.LoginPage;
import com.piramidsoft.korlap.R;
import com.piramidsoft.korlap.Setting.DateTools;
import com.piramidsoft.korlap.Setting.OwnProgressDialog;
import com.piramidsoft.korlap.adapters.ClientNotesAdapter;
import com.piramidsoft.korlap.models.CatatanModel;
import com.piramidsoft.korlap.models.NotesModel;
import com.piramidsoft.korlap.models.UrlModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.piramidsoft.korlap.Config.AppConf.URL_GET_CATATAN;
import static com.piramidsoft.korlap.Config.AppConf.URL_GET_IMAGE;
import static com.piramidsoft.korlap.Config.AppConf.URL_GET_IMAGE_FROM_FOLDER;
import static com.piramidsoft.korlap.Config.http.TAG_USERNAME;

public class ClientNotes extends AppCompatActivity {

    @BindView(R.id.rvNotes)
    RecyclerView rvNotes;
    @BindView(R.id.etUser)
    TextView etUser;
    private ArrayList<NotesModel> arrayList = new ArrayList<>();

    RequestQueue requestQueue;
    StringRequest stringRequest;
    OwnProgressDialog loading;
    ArrayList<CatatanModel> listCatatan = new ArrayList<>();
    String client_id, nama_lengkap,urlKtp;
    SharedPreferences sharedpreferences;
    private final int MY_SOCKET_TIMEOUT_MS = 60 * 1000;


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
        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        nama_lengkap = sharedpreferences.getString(TAG_USERNAME, "");
        etUser.setText(nama_lengkap);

        Intent intent = getIntent();
        client_id = intent.getStringExtra("cli_id");

        loading.show();

        getJSON();

    }

    private void getJSON() {

        listCatatan = new ArrayList<CatatanModel>();

        stringRequest = new StringRequest(Request.Method.GET, URL_GET_CATATAN + client_id, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("response_catatan: ", response);
                Log.d("catatan url ", URL_GET_CATATAN + client_id);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        CatatanModel modelCatatan = new CatatanModel();
                        modelCatatan.setXxx(json.getString("ajunote_catatan"));
                        HashMap<String, String> tgls = DateTools.getDateString(json.getString("ajunote_tanggal"), "yyyy-MM-dd");
                        modelCatatan.setTxtTanggal(tgls.get("date")+" "+tgls.get("month")+" "+tgls.get("year"));
                        listCatatan.add(modelCatatan);
                    }

                    ClientNotesAdapter adapter = new ClientNotesAdapter(listCatatan, ClientNotes.this);
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
