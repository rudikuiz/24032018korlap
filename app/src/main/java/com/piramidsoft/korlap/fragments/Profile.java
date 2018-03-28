package com.piramidsoft.korlap.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
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
import com.piramidsoft.korlap.models.ListKaryawan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.piramidsoft.korlap.Config.http.TAG_MEMBER_ID_KARYAWAN;

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
    @BindView(R.id.btSave)
    Button btSave;
    @BindView(R.id.etNama)
    EditText etNama;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.Swipe)
    SwipeRefreshLayout Swipe;
    @BindView(R.id.btCancel)
    Button btCancel;
    @BindView(R.id.linAct)
    LinearLayout linAct;
    @BindView(R.id.btCamera)
    ImageButton btCamera;
    private OwnProgressDialog progressDialog;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private final int MY_SOCKET_TIMEOUT_MS = 10 * 1000;
    ConnectivityManager conMgr;
    FragmentActivity mActivity;
    SharedPreferences sharedpreferences;
    String Karid;
    private static final String TAG = LoginPage.class.getSimpleName();
    int success;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    Bitmap decoded;
    int PICK_IMAGE_REQUEST = 1, SELECT_FILE = 1;
    private String userChoosenTask;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        mActivity = getActivity();
        linAct.setVisibility(View.GONE);
        progressDialog = new OwnProgressDialog(mActivity);
        requestQueue = Volley.newRequestQueue(mActivity);
        cekInternet();
        sharedpreferences = mActivity.getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        Karid = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");
        Log.d("kasd", Karid);
        load();
        progressDialog.show();
        return view;
    }

    private void cekInternet() {
        conMgr = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(mActivity, "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void load() {
        progressDialog.show();
        stringRequest = new StringRequest(Request.Method.GET, "http://118.98.64.44/korlap/profile.php?kar_id=" + Karid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ListKaryawan karyawan = new ListKaryawan();
                        karyawan.setNama(json.getString("kar_namalengkap"));
                        karyawan.setNotelp(json.getString("kar_no_telepon"));
                        karyawan.setEmail(json.getString("kar_email_winwin"));
                        karyawan.setPass(json.getString("kar_email_password"));

                        etNama.setText(karyawan.getNama());
                        etNoTelp.setText(karyawan.getNotelp());
                        etEmail.setText(karyawan.getEmail());
                        etPassword.setText(karyawan.getPass());

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();
                if (Swipe != null) {
                    Swipe.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (Swipe != null) {
                    Swipe.setRefreshing(false);
                }
                if (error instanceof TimeoutError) {
                    Toast.makeText(mActivity, "timeout", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(mActivity, "no connection", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(mActivity,
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void updateprofile() {
        progressDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST, "http://118.98.64.44/korlap/update_profile.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("notes: ", response);
                Toast.makeText(mActivity, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();
//                txtTulisCatatan.getText().clear();
                progressDialog.dismiss();
                try {
                    final JSONObject jObj = new JSONObject(response.toString());
                    success = jObj.getInt(TAG_SUCCESS);

                    if (response.equals("Berhasil")) {
                        Toast.makeText(mActivity, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(mActivity,
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error: ", error.getMessage());
                Toast.makeText(mActivity,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("kar_namalengkap", etNama.getText().toString());
                params.put("kar_no_telepon", etNoTelp.getText().toString());
                params.put("kar_email_winwin", etEmail.getText().toString());
                params.put("member_pass", etPassword.getText().toString());
                params.put("kar_foto_selfi", getStringImage(decoded));
                params.put("kar_id", Karid);
                return params;
            }

        };
        requestQueue.add(strReq);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        imgFoto.setImageBitmap(decoded);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == PICK_IMAGE_REQUEST)
                bitmap = (Bitmap) data.getExtras().get("data");
            setToImageView(bitmap);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(mActivity.getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        imgFoto.setImageBitmap(bm);
    }

    @OnClick({R.id.btEditNama, R.id.btEditNoTelp, R.id.btEditEmail,
            R.id.btPassword, R.id.btTarik, R.id.btSave, R.id.btCancel,
            R.id.btCamera, R.id.imgFoto})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.btEditNama:
                if (etNama.requestFocus()) {
                    linAct.setVisibility(View.VISIBLE);
                    etNama.setEnabled(true);
                    etNama.requestFocus();
                } else {
                    linAct.setVisibility(View.GONE);
                }
                break;
            case R.id.btEditNoTelp:
                if (etNoTelp.requestFocus()) {
                    etNoTelp.setEnabled(true);
                    etNoTelp.requestFocus();
                    linAct.setVisibility(View.VISIBLE);
                } else {
                    linAct.setVisibility(View.GONE);
                }
                break;
            case R.id.btEditEmail:
                if (etEmail.requestFocus()) {
                    etEmail.setEnabled(true);
                    etEmail.requestFocus();
                    linAct.setVisibility(View.VISIBLE);
                } else {
                    linAct.setVisibility(View.GONE);
                }
                break;
            case R.id.btPassword:
                if (etPassword.requestFocus()) {
                    etPassword.setEnabled(true);
                    etPassword.requestFocus();
                    linAct.setVisibility(View.VISIBLE);
                } else {
                    linAct.setVisibility(View.GONE);
                }
                break;
            case R.id.btTarik:
                break;
            case R.id.btSave:
//                Toast.makeText(getContext(), "Toas", Toast.LENGTH_SHORT).show();
                updateprofile();
                break;
            case R.id.btCancel:
                etNama.setEnabled(false);
                etNoTelp.setEnabled(false);
                etEmail.setEnabled(false);
                etPassword.setEnabled(false);
                linAct.setVisibility(View.GONE);
                break;
            case R.id.btCamera:

                PopupMenu pm = new PopupMenu(mActivity, btCamera);
                pm.getMenuInflater().inflate(R.menu.option_take, pm.getMenu());
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.itcamera:
                                takeImageFromCamera();
                                return true;
                            case R.id.itgallery:
                                galleryIntent();
                                return true;
                        }
                        return true;
                    }
                });
                pm.show();
                break;
        }
    }

    public void takeImageFromCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, PICK_IMAGE_REQUEST);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }
}
