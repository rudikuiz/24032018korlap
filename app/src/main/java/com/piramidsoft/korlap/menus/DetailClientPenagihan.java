package com.piramidsoft.korlap.menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import com.piramidsoft.korlap.R;
import com.piramidsoft.korlap.Setting.OwnProgressDialog;
import com.piramidsoft.korlap.models.ClientModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.piramidsoft.korlap.Config.AppConf.URL_GET_DETAIL;

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
    private OwnProgressDialog progressDialog;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private final int MY_SOCKET_TIMEOUT_MS = 10 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_klient);
        ButterKnife.bind(this);
        progressDialog = new OwnProgressDialog(DetailClientPenagihan.this);
        requestQueue = Volley.newRequestQueue(DetailClientPenagihan.this);
        load();
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
                        dataClient.setTelp_perusahaan(json.getString("cli_telepon_perusahaan"));
                        dataClient.setAlamat_perusahaan(json.getString("cli_alamat_perusahaan"));


                        etNama.setText(dataClient.getNama());
                        etNoHP.setText(dataClient.getNohp());
                        etAlamatKantor.setText(dataClient.getAlamat_perusahaan());
                        etNoHP.setText(dataClient.getNohp());
                        etTelpKantor.setText(dataClient.getTelp_perusahaan());

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

    @OnClick({R.id.btNotes, R.id.btSimpan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btNotes:
                Intent intent = new Intent(DetailClientPenagihan.this, ClientNotes.class);
                startActivity(intent);
                break;
            case R.id.btSimpan:
                break;
        }
    }
}
