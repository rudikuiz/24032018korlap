package com.piramidsoft.korlap.menus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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
import com.piramidsoft.korlap.adapters.ColSurveyAdapter;
import com.piramidsoft.korlap.adapters.CollectionAdapter;
import com.piramidsoft.korlap.models.ClientModel;
import com.piramidsoft.korlap.models.ListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.piramidsoft.korlap.Config.AppConf.URL_GET_ALL;
import static com.piramidsoft.korlap.Config.AppConf.URL_GET_DETAIL;
import static com.piramidsoft.korlap.Config.http.TAG_MEMBER_ID_KARYAWAN;

public class DetailCollector extends AppCompatActivity {

    @BindView(R.id.ImgKtp)
    ImageView ImgKtp;
    @BindView(R.id.etNama)
    TextView etNama;
    @BindView(R.id.etNoHP)
    TextView etNoHP;
    @BindView(R.id.rvCollector)
    RecyclerView rvCollector;
    @BindView(R.id.Swipe)
    SwipeRefreshLayout Swipe;
    @BindView(R.id.rvSurvey)
    RecyclerView rvSurvey;
    @BindView(R.id.Swipe2)
    SwipeRefreshLayout Swipe2;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    @BindView(R.id.etAlamat)
    TextView etAlamat;
    private ArrayList<ListModel> collectionLM = new ArrayList<>();
    private ArrayList<ListModel> surveyLM = new ArrayList<>();
    OwnProgressDialog loading;
    OwnProgressDialog progressDialog;
    private final int MY_SOCKET_TIMEOUT_MS = 10 * 1000;
    SharedPreferences sharedpreferences;
    String member_id_kry, kar_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_collector);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        member_id_kry = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");
        Intent intent = getIntent();
        kar_id = intent.getStringExtra("kar_id");

        GridLayoutManager manager = new GridLayoutManager(DetailCollector.this, 1,
                GridLayoutManager.VERTICAL, false);
        rvCollector.setLayoutManager(manager);

        GridLayoutManager manager2 = new GridLayoutManager(DetailCollector.this, 1,
                GridLayoutManager.VERTICAL, false);
        rvSurvey.setLayoutManager(manager2);

        requestQueue = Volley.newRequestQueue(DetailCollector.this);
        loading = new OwnProgressDialog(DetailCollector.this);

        loading.show();
        Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJSONCol();

            }
        });

        Swipe2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJSONCol();
            }
        });

        getJSONCol();

    }

    private void getJSONCol() {
        collectionLM.clear();
        surveyLM.clear();

        stringRequest = new StringRequest(Request.Method.GET, "http://118.98.64.44/korlap/detail_collector.php?member_id_karyawan=" + member_id_kry + "&" + "kar_id=" + kar_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response: ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("penagihan");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ListModel modelMenu = new ListModel();
                        modelMenu.setNama(json.getString("cli_nama_lengkap"));
                        modelMenu.setStatus(json.getString("cli_status"));
                        modelMenu.setCli_id(json.getString("cli_id"));
                        collectionLM.add(modelMenu);
                    }

                    CollectionAdapter adapter = new CollectionAdapter(collectionLM, DetailCollector.this);
                    rvCollector.setAdapter(adapter);


                    JSONArray jsonArray2 = jsonObject.getJSONArray("survey");
                    for (int a = 0; a < jsonArray2.length(); a++) {
                        JSONObject json = jsonArray2.getJSONObject(a);
                        ListModel modelMenu = new ListModel();
                        modelMenu.setNama(json.getString("cli_nama_lengkap"));
                        modelMenu.setStatus(json.getString("cli_status"));
                        modelMenu.setCli_id(json.getString("cli_id"));
                        surveyLM.add(modelMenu);
                    }

                    ColSurveyAdapter adapters = new ColSurveyAdapter(surveyLM, DetailCollector.this);
                    rvSurvey.setAdapter(adapters);

                    JSONArray jsonArray3 = jsonObject.getJSONArray("detail");
                    for (int a = 0; a < jsonArray3.length(); a++) {
                        JSONObject json = jsonArray3.getJSONObject(a);
                        ClientModel dataClient = new ClientModel();
                        dataClient.setNama(json.getString("kar_namalengkap"));
                        dataClient.setNohp(json.getString("kar_telpon"));
                        dataClient.setAlamat(json.getString("kar_alamat"));

                        etNama.setText("Nama : " + dataClient.getNama());
                        etNoHP.setText("No HP : "+dataClient.getNohp());
                        etAlamat.setText("Alamat : "+dataClient.getAlamat());

                    }


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
                    Toast.makeText(DetailCollector.this, "timeout", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(DetailCollector.this, "no connection", Toast.LENGTH_SHORT).show();
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
