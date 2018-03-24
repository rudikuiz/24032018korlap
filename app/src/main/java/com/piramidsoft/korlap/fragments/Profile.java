package com.piramidsoft.korlap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.piramidsoft.korlap.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment {

    @BindView(R.id.header)
    ImageView header;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.imgFoto)
    CircleImageView imgFoto;
    @BindView(R.id.btEditNama)
    Button btEditNama;
    @BindView(R.id.etNoTelp)
    EditText etNoTelp;
    @BindView(R.id.btEditNoTelp)
    Button btEditNoTelp;
    @BindView(R.id.btEditEmail)
    Button btEditEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btPassword)
    Button btPassword;
    @BindView(R.id.txSaldo)
    TextView txSaldo;
    @BindView(R.id.etTarikDana)
    EditText etTarikDana;
    @BindView(R.id.btTarik)
    Button btTarik;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btEditNama, R.id.btEditNoTelp, R.id.btEditEmail, R.id.btPassword, R.id.btTarik})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btEditNama:
                break;
            case R.id.btEditNoTelp:
                break;
            case R.id.btEditEmail:
                break;
            case R.id.btPassword:
                break;
            case R.id.btTarik:
                break;
        }
    }
}
