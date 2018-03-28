package com.piramidsoft.korlap.models;

/**
 * Created by Ayo Maju on 09/02/2018.
 * Updated by Muhammad Iqbal on 09/02/2018.
 */

public class CatatanModel {
    private String xxx, txtjam, txtTanggal;

    public CatatanModel(String xxx, String txtjam, String txtTanggal) {
        this.xxx = xxx;
        this.txtjam = txtjam;
        this.txtTanggal = txtTanggal;
    }

    public CatatanModel() {
    }

    public String getXxx() {
        return xxx;
    }

    public void setXxx(String xxx) {
        this.xxx = xxx;
    }

    public String getTxtjam() {
        return txtjam;
    }

    public void setTxtjam(String txtjam) {
        this.txtjam = txtjam;
    }

    public String getTxtTanggal() {
        return txtTanggal;
    }

    public void setTxtTanggal(String txtTanggal) {
        this.txtTanggal = txtTanggal;
    }
}
