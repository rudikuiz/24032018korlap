package com.piramidsoft.korlap.models;

/**
 */

public class ListModel {
    String nama,status, cli_id,pengajuan_id;

    public ListModel() {
    }

    public ListModel(String nama, String status, String cli_id) {
        this.nama = nama;
        this.status = status;
        this.cli_id = cli_id;
    }

    public ListModel(String nama, String status, String cli_id, String pengajuan_id) {
        this.nama = nama;
        this.status = status;
        this.cli_id = cli_id;
        this.pengajuan_id = pengajuan_id;
    }

    public String getPengajuan_id() {
        return pengajuan_id;
    }

    public void setPengajuan_id(String pengajuan_id) {
        this.pengajuan_id = pengajuan_id;
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

    public String getCli_id() {
        return cli_id;
    }

    public void setCli_id(String cli_id) {
        this.cli_id = cli_id;
    }
}
