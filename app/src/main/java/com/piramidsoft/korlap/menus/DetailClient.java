package com.piramidsoft.korlap.menus;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.piramidsoft.korlap.R;
import com.piramidsoft.korlap.Setting.OwnProgressDialog;
import com.piramidsoft.korlap.models.ClientModel;
import com.piramidsoft.korlap.models.UrlModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.piramidsoft.korlap.Config.AppConf.URL_GET_IMAGE;
import static com.piramidsoft.korlap.Config.AppConf.URL_GET_IMAGE_FROM_FOLDER;

public class DetailClient extends AppCompatActivity {

    @BindView(R.id.etNama)
    TextView etNama;
    @BindView(R.id.etAlamat)
    TextView etAlamat;
    @BindView(R.id.etNoHP)
    TextView etNoHP;
    @BindView(R.id.etAlamatKantor)
    TextView etAlamatKantor;
    @BindView(R.id.etTelpKantor)
    TextView etTelpKantor;
    @BindView(R.id.swipeKlienSurvey)
    SwipeRefreshLayout swipeKlienSurvey;
    @BindView(R.id.ImgKtp)
    ImageView ImgKtp;
    private OwnProgressDialog progressDialog;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private final int MY_SOCKET_TIMEOUT_MS = 10 * 1000;
    String id, urlKtp, idKar, id_pengajuan, iduser;
    private ArrayList<UrlModel> ktp = new ArrayList<>();
    ConnectivityManager conMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_client);
        ButterKnife.bind(this);
        requestQueue = Volley.newRequestQueue(DetailClient.this);
        ImgKtp.setOnTouchListener(new ImageMatrixTouchHandler(DetailClient.this));
        Intent intent = getIntent();
        id = intent.getStringExtra("cli_id");
        id_pengajuan = intent.getStringExtra("pengajuan_id");
        progressDialog = new OwnProgressDialog(DetailClient.this);

        progressDialog.show();

        swipeKlienSurvey.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cekInternet();
                load();
                viewKtpDialog();
            }
        });
        cekInternet();
        load();
        viewKtpDialog();

    }

    private void cekInternet() {
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void load() {
        progressDialog.show();
        stringRequest = new StringRequest(Request.Method.GET, "http://118.98.64.44/korlap/perdata.php?cli_id=" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ClientModel dataClient = new ClientModel();
                        dataClient.setNama(json.getString("cli_nama_lengkap"));
                        dataClient.setNohp(json.getString("cli_handphone"));
                        dataClient.setAlamat(json.getString("cli_alamat"));
                        dataClient.setTelp_perusahaan(json.getString("cli_telepon_perusahaan"));
                        dataClient.setAlamat_perusahaan(json.getString("cli_alamat_perusahaan"));

                        etNama.setText(dataClient.getNama());
                        etNoHP.setText(dataClient.getNohp());
                        etAlamatKantor.setText(dataClient.getAlamat_perusahaan());
                        etAlamat.setText(dataClient.getAlamat());
                        etTelpKantor.setText(dataClient.getTelp_perusahaan());

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();
                if (swipeKlienSurvey != null) {
                    swipeKlienSurvey.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (swipeKlienSurvey != null) {
                    swipeKlienSurvey.setRefreshing(false);
                }
                if (error instanceof TimeoutError) {
                    Toast.makeText(DetailClient.this, "timeout", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(DetailClient.this, "no connection", Toast.LENGTH_SHORT).show();
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

    private void viewKtpDialog() {
        ktp = new ArrayList<UrlModel>();
        stringRequest = new StringRequest(Request.Method.GET, URL_GET_IMAGE + id, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("responese url", response);
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        UrlModel model = new UrlModel();
                        model.setKodePelanggan(json.getString("cli_nomor_pelanggan"));
                        model.setNamaDocument(json.getString("cli_doc_file"));
                        ktp.add(model);
                        urlKtp = URL_GET_IMAGE_FROM_FOLDER + model.getKodePelanggan() + "/" + model.getNamaDocument();
                        Log.d("hasilurl", urlKtp);

                        if (urlKtp != null) {
                            Glide.with(DetailClient.this).load(urlKtp)
                                    .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ImgKtp);

                        } else {
                            ImgKtp.setImageResource(R.drawable.noimage);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (swipeKlienSurvey != null) {
                    swipeKlienSurvey.setRefreshing(false);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                if (swipeKlienSurvey != null) {
                    swipeKlienSurvey.setRefreshing(false);
                }
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
}
