package com.piramidsoft.korlap.models;

/**

 */

public class ListCollectorModel {
    String nama,jmlcol,jmlsur;

    public ListCollectorModel() {
    }

    public ListCollectorModel(String nama, String jmlcol, String jmlsur) {
        this.nama = nama;
        this.jmlcol = jmlcol;
        this.jmlsur = jmlsur;
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
