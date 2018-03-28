package com.piramidsoft.korlap.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.piramidsoft.korlap.LoginPage;
import com.piramidsoft.korlap.R;
import com.piramidsoft.korlap.Setting.OwnProgressDialog;
import com.piramidsoft.korlap.menus.DaftarCollector;
import com.piramidsoft.korlap.menus.DaftarPenagihan;
import com.piramidsoft.korlap.menus.DaftarSurvey;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.piramidsoft.korlap.Config.AppConf.URL_GET_ALL;
import static com.piramidsoft.korlap.Config.http.TAG_MEMBER_ID_KARYAWAN;
import static com.piramidsoft.korlap.Config.http.TAG_USERNAME;

public class Home extends Fragment {

    @BindView(R.id.etUser)
    TextView etUser;
    @BindView(R.id.ic_home)
    ImageView icHome;
    @BindView(R.id.t_total)
    TextView tTotal;
    @BindView(R.id.etJmlDP)
    TextView etJmlDP;
    @BindView(R.id.btDaftarPenagihan)
    LinearLayout btDaftarPenagihan;
    @BindView(R.id.etJmlDS)
    TextView etJmlDS;
    @BindView(R.id.btTotalSurvey)
    LinearLayout btTotalSurvey;
    @BindView(R.id.etDaftarCol)
    ImageView etDaftarCol;
    @BindView(R.id.btDaftarCol)
    LinearLayout btDaftarCol;
    Unbinder unbinder;
    String id, nama_lengkap;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    SharedPreferences sharedpreferences;
    FragmentActivity mActivity;
    OwnProgressDialog loading;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshlayout;

    public Home() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        mActivity = getActivity();
        requestQueue = Volley.newRequestQueue(mActivity);
        sharedpreferences = getContext().getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        nama_lengkap = sharedpreferences.getString(TAG_USERNAME, "");
        id = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");
        etUser.setText(nama_lengkap);
        loading = new OwnProgressDialog(mActivity);

        loading.show();
        totaldaftarpenagihan();
        totaldaftarsurvey();

        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                totaldaftarpenagihan();
                totaldaftarsurvey();
            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btDaftarPenagihan, R.id.btTotalSurvey, R.id.btDaftarCol})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btDaftarPenagihan:
                Intent intent = new Intent(mActivity, DaftarPenagihan.class);
                startActivity(intent);
                break;
            case R.id.btTotalSurvey:
                intent = new Intent(mActivity, DaftarSurvey.class);
                startActivity(intent);
                break;
            case R.id.btDaftarCol:
                intent = new Intent(mActivity, DaftarCollector.class);
                startActivity(intent);
                break;
        }
    }

    private void totaldaftarpenagihan() {
        stringRequest = new StringRequest(Request.Method.GET, URL_GET_ALL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("counts ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    etJmlDP.setText(jsonObject.getString("count"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loading.dismiss();
                if (refreshlayout != null) {
                    refreshlayout.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                if (refreshlayout != null) {
                    refreshlayout.setRefreshing(false);
                }
            }
        });
        requestQueue.add(stringRequest);
    }

    private void totaldaftarsurvey() {
        stringRequest = new StringRequest(Request.Method.GET, "http://118.98.64.44/korlap/view_daftar_survey.php?member_id_karyawan=" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("counts ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    etJmlDS.setText(jsonObject.getString("count"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loading.dismiss();
                if (refreshlayout != null) {
                    refreshlayout.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                if (refreshlayout != null) {
                    refreshlayout.setRefreshing(false);
                }
            }
        });
        requestQueue.add(stringRequest);
    }


}
