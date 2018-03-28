package com.piramidsoft.korlap.menus;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.piramidsoft.korlap.LoginPage;
import com.piramidsoft.korlap.R;
import com.piramidsoft.korlap.Setting.OwnProgressDialog;
import com.piramidsoft.korlap.adapters.CollectorListAdapter;
import com.piramidsoft.korlap.models.ListCollectorModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.piramidsoft.korlap.Config.http.TAG_MEMBER_ID_KARYAWAN;

public class DaftarCollector extends AppCompatActivity {

    @BindView(R.id.rvListCollector)
    RecyclerView rvListCollector;
    @BindView(R.id.Swipe)
    SwipeRefreshLayout Swipe;
    private ArrayList<ListCollectorModel> arrayList = new ArrayList<>();
    RequestQueue requestQueue;
    StringRequest stringRequest;
    OwnProgressDialog loading;
    SharedPreferences sharedpreferences;
    String iduser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_collector);
        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        iduser = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");

        GridLayoutManager layoutManager = new GridLayoutManager(DaftarCollector.this, 1,
                GridLayoutManager.VERTICAL, false);
        rvListCollector.setLayoutManager(layoutManager);
        requestQueue = Volley.newRequestQueue(DaftarCollector.this);

        loading = new OwnProgressDialog(DaftarCollector.this);

        loading.show();
        Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJSON();

            }
        });
        getJSON();

//        Log.d("adsg", "http://118.98.64.44/korlap/view_daftar_collector.php" + "?member_id_karyawan=" + iduser);
//        getStatis();
    }

    private void getJSON() {

        arrayList = new ArrayList<ListCollectorModel>();

        stringRequest = new StringRequest(Request.Method.GET, "http://118.98.64.44/korlap/view_daftar_collector.php" + "?member_id_karyawan=" + iduser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response: ", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ListCollectorModel modelMenu = new ListCollectorModel();
                        modelMenu.setNama(json.getString("kar_namalengkap"));
                        modelMenu.setJmlcol(json.getString("jumlah_collect"));
                        modelMenu.setJmlsur(json.getString("jumlah_survey"));
                        modelMenu.setKar_id(json.getString("kar_id"));
                        arrayList.add(modelMenu);
                    }

                    CollectorListAdapter adapter = new CollectorListAdapter(arrayList, DaftarCollector.this);
                    rvListCollector.setAdapter(adapter);
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
                    Toast.makeText(DaftarCollector.this, "timeout", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(DaftarCollector.this, "no connection", Toast.LENGTH_SHORT).show();
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

    private void getStatis() {
        CollectorListAdapter adapter = new CollectorListAdapter(arrayList, DaftarCollector.this);
        rvListCollector.setAdapter(adapter);

        arrayList.add(new ListCollectorModel("Saudah", "23", "2"));
        arrayList.add(new ListCollectorModel("Alex", "13", "1"));
        arrayList.add(new ListCollectorModel("Anhar", "9", "3"));
    }
}
