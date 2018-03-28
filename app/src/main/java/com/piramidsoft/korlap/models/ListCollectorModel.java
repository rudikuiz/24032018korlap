package com.piramidsoft.korlap.models;

/**

 */

public class ListCollectorModel {
    String nama,jmlcol,jmlsur,kar_id;

    public ListCollectorModel() {
    }

    public ListCollectorModel(String nama, String jmlcol, String jmlsur) {
        this.nama = nama;
        this.jmlcol = jmlcol;
        this.jmlsur = jmlsur;
    }

    public String getKar_id() {
        return kar_id;
    }

    public void setKar_id(String kar_id) {
        this.kar_id = kar_id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJmlcol() {
        return jmlcol;
    }

    public void setJmlcol(String jmlcol) {
        this.jmlcol = jmlcol;
    }

    public String getJmlsur() {
        return jmlsur;
    }

    public void setJmlsur(String jmlsur) {
        this.jmlsur = jmlsur;
    }
}
