package com.piramidsoft.korlap.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.piramidsoft.korlap.LoginPage;
import com.piramidsoft.korlap.R;
import com.piramidsoft.korlap.menus.DaftarCollector;
import com.piramidsoft.korlap.menus.DaftarPenagihan;
import com.piramidsoft.korlap.menus.DaftarSurvey;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.piramidsoft.korlap.Config.http.TAG_MEMBER_ID_KARYAWAN;

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

    public Home() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        mActivity = getActivity();
        sharedpreferences = getContext().getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        id = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");
        totaldaftarpenagihan();
        totaldaftarsurvey();
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
        stringRequest = new StringRequest(Request.Method.GET, "URL_GET_COUNT_PENAGIHAN" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("counts ", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);
    }

    private void totaldaftarsurvey() {
        stringRequest = new StringRequest(Request.Method.GET, "URL_GET_COUNT_SURVEY" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("counts ", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);
    }


}
