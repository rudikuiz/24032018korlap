package com.piramidsoft.korlap.menus;

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
    private ArrayList<ListModel> arrayList = new ArrayList<>();
    OwnProgressDialog loading;
    OwnProgressDialog progressDialog;
    private final int MY_SOCKET_TIMEOUT_MS = 10 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_collector);
        ButterKnife.bind(this);

        GridLayoutManager manager = new GridLayoutManager(DetailCollector.this, 1,
                GridLayoutManager.VERTICAL, false);
        rvCollector.setLayoutManager(manager);
        rvSurvey.setLayoutManager(manager);
        requestQueue = Volley.newRequestQueue(DetailCollector.this);

        loading = new OwnProgressDialog(DetailCollector.this);

        loading.show();
        Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJSONCol();

            }
        });
        getJSONCol();

        Swipe2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJSONSurvey();
            }
        });

        getJSONSurvey();
        load();
    }

    private void getJSONCol() {

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
                        modelMenu.setNama(json.getString("nama"));
                        modelMenu.setStatus(json.getString("status"));
                        arrayList.add(modelMenu);
                    }

                    CollectionAdapter adapter = new CollectionAdapter(arrayList, DetailCollector.this);
                    rvCollector.setAdapter(adapter);
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

    private void getJSONSurvey() {

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
                        modelMenu.setNama(json.getString("nama"));
                        modelMenu.setStatus(json.getString("status"));

                        arrayList.add(modelMenu);
                    }

                    ColSurveyAdapter adapter = new ColSurveyAdapter(arrayList, DetailCollector.this);
                    rvSurvey.setAdapter(adapter);
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

    private void load() {
        progressDialog.show();
        stringRequest = new StringRequest(Request.Method.GET, URL_GET_DETAIL + "nothing", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("datakuz", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("client");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ClientModel dataClient = new ClientModel();
                        dataClient.setNama(json.getString("cli_nama_lengkap"));
                        dataClient.setNohp(json.getString("cli_handphone"));
                        dataClient.setAlamat(json.getString("cli_alamat"));

                        etNama.setText("Nama : " + dataClient.getNama());
                        etNoHP.setText("No HP : "+dataClient.getNohp());
                        etAlamat.setText("Alamat : "+dataClient.getAlamat());

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof TimeoutError) {
                    Toast.makeText(DetailCollector.this, "timeout", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(DetailCollector.this, "no connection", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

}
