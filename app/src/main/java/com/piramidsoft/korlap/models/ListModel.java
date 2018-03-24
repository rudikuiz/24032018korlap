package com.piramidsoft.korlap.models;

/**
 */

public class ListModel {
    String nama,status;

    public ListModel() {
    }

    public ListModel(String nama, String status) {
        this.nama = nama;
        this.status = status;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
