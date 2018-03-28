package com.piramidsoft.korlap.menus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.piramidsoft.korlap.LoginPage;
import com.piramidsoft.korlap.R;
import com.piramidsoft.korlap.Setting.OwnProgressDialog;
import com.piramidsoft.korlap.models.ClientModel;
import com.piramidsoft.korlap.models.UrlModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.piramidsoft.korlap.Config.AppConf.URL_GET_ALL;
import static com.piramidsoft.korlap.Config.AppConf.URL_GET_IMAGE;
import static com.piramidsoft.korlap.Config.AppConf.URL_GET_IMAGE_FROM_FOLDER;
import static com.piramidsoft.korlap.Config.http.TAG_MEMBER_ID_KARYAWAN;

public class DetailClientPenagihan extends AppCompatActivity {

    @BindView(R.id.ImgKtp)
    ImageView ImgKtp;
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
    @BindView(R.id.spinCol)
    Spinner spinCol;
    @BindView(R.id.btNotes)
    Button btNotes;
    @BindView(R.id.btSimpan)
    Button btSimpan;
    @BindView(R.id.swipeKlienPenagihan)
    SwipeRefreshLayout swipeKlienPenagihan;
    private OwnProgressDialog progressDialog;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private final int MY_SOCKET_TIMEOUT_MS = 10 * 1000;
    String id, urlKtp, idpengajuan, idKar, iduser;
    private ArrayList<UrlModel> ktp = new ArrayList<>();
    final ArrayList<String> karyawanname = new ArrayList<>();
    final ArrayList<String> karyawanid = new ArrayList<>();
    SharedPreferences sharedpreferences;
    ConnectivityManager conMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_klient);
        ButterKnife.bind(this);

        cekInternet();
        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        iduser = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");

        progressDialog = new OwnProgressDialog(DetailClientPenagihan.this);
        requestQueue = Volley.newRequestQueue(DetailClientPenagihan.this);
        ImgKtp.setOnTouchListener(new ImageMatrixTouchHandler(DetailClientPenagihan.this));
        Intent intent = getIntent();
        id = intent.getStringExtra("cli_id");
        idpengajuan = intent.getStringExtra("pengajuan_id");

        load();
        viewKtpDialog();
        setSpinCol();

        swipeKlienPenagihan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
                viewKtpDialog();
                setSpinCol();
            }
        });
        spinCol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                idKar = karyawanid.get(position);
//                Log.d("KARID", idKar);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        Log.d("urlks", URL_GET_ALL + id);
        stringRequest = new StringRequest(Request.Method.GET, "http://118.98.64.44/korlap/perdata.php?cli_id=" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("datakuz", response);
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
                if (swipeKlienPenagihan != null) {
                    swipeKlienPenagihan.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (swipeKlienPenagihan != null) {
                    swipeKlienPenagihan.setRefreshing(false);
                }
                if (error instanceof TimeoutError) {
                    Toast.makeText(DetailClientPenagihan.this, "timeout", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(DetailClientPenagihan.this, "no connection", Toast.LENGTH_SHORT).show();
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
                            Glide.with(DetailClientPenagihan.this).load(urlKtp)
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

                if (swipeKlienPenagihan != null) {
                    swipeKlienPenagihan.setRefreshing(false);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                if (swipeKlienPenagihan != null) {
                    swipeKlienPenagihan.setRefreshing(false);
                }
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void AddPenagihan() {
        StringRequest strReq = new StringRequest(Request.Method.POST, "http://118.98.64.44/korlap/add_penagihan_survey.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(DetailClientPenagihan.this, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();
                finish();
                try {
                    final JSONObject jObj = new JSONObject(response.toString());
                    if (response.equals("Berhasil")) {
                        Toast.makeText(DetailClientPenagihan.this, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        Toast.makeText(DetailClientPenagihan.this,
                                jObj.getString("message"), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error: ", error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                finish();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("pengajuan_id", idpengajuan);
                params.put("pengajuan_assigned_to", idKar);

                return params;
            }

        };
        requestQueue.add(strReq);
    }

    private void setSpinCol() {

        stringRequest = new StringRequest(Request.Method.GET, "http://118.98.64.44/korlap/view_daftar_collector.php" + "?member_id_karyawan=" + iduser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        karyawanname.add(json.optString("kar_namalengkap"));
                        karyawanid.add(json.optString("kar_id"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                spinCol.setAdapter(new ArrayAdapter<String>(DetailClientPenagihan.this,
                        R.layout.custom_spinner,
                        karyawanname));

                progressDialog.dismiss();
                if (swipeKlienPenagihan != null) {
                    swipeKlienPenagihan.setRefreshing(false);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof TimeoutError) {
                    Toast.makeText(DetailClientPenagihan.this, "timeout", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(DetailClientPenagihan.this, "no connection", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                if (swipeKlienPenagihan != null) {
                    swipeKlienPenagihan.setRefreshing(false);
                }
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);


    }

    @OnClick({R.id.btNotes, R.id.btSimpan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btNotes:
                Intent intent = new Intent(DetailClientPenagihan.this, ClientNotes.class);
                intent.putExtra("cli_id", id);
                startActivity(intent);
                break;
            case R.id.btSimpan:
                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    AddPenagihan();

                } else {
                    Toast.makeText(DetailClientPenagihan.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
