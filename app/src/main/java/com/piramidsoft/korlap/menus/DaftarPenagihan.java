package com.piramidsoft.korlap.menus;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.piramidsoft.korlap.R;
import com.piramidsoft.korlap.Setting.OwnProgressDialog;
import com.piramidsoft.korlap.adapters.PenagihanAdapter;
import com.piramidsoft.korlap.models.ListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.piramidsoft.korlap.Config.AppConf.URL_GET_ALL;

public class DaftarPenagihan extends AppCompatActivity {


    @BindView(R.id.Swipe)
    SwipeRefreshLayout Swipe;
    @BindView(R.id.rvDaftarPenagihan)
    RecyclerView rvDaftarPenagihan;
    private ArrayList<ListModel> arrayList = new ArrayList<>();
    RequestQueue requestQueue;
    StringRequest stringRequest;

    OwnProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_penagihan);
        ButterKnife.bind(this);
        rvDaftarPenagihan.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(DaftarPenagihan.this, 1,
                GridLayoutManager.VERTICAL, false);
        rvDaftarPenagihan.setLayoutManager(layoutManager);

        requestQueue = Volley.newRequestQueue(DaftarPenagihan.this);

        loading = new OwnProgressDialog(DaftarPenagihan.this);

        loading.show();
        Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJSON();
            }
        });
        getJSON();
    }

    private void getJSON() {

        arrayList = new ArrayList<ListModel>();

        stringRequest = new StringRequest(Request.Method.GET, URL_GET_ALL + "nothing", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response: ", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ListModel modelMenu = new ListModel();
                        modelMenu.setNama(json.getString("number"));
                        modelMenu.setStatus(json.getString("cli_id"));

                        arrayList.add(modelMenu);
                    }

                    PenagihanAdapter adapter = new PenagihanAdapter(arrayList, DaftarPenagihan.this);
                    rvDaftarPenagihan.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loading.dismiss();
                if (Swipe != null) {
                    Swipe.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(DaftarPenagihan.this, "timeout", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(DaftarPenagihan.this, "no connection", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                loading.dismiss();
                if (Swipe != null) {
                    Swipe.setRefreshing(false);
                }
            }
        });
        requestQueue.add(stringRequest);
    }
}
