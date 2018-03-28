package com.piramidsoft.korlap.models;

/**
 */

public class ListKaryawan {
    String nama, kar_id,notelp,email,pass;

    public ListKaryawan(String nama, String kar_id) {
        this.nama = nama;
        this.kar_id = kar_id;
    }

    public ListKaryawan() {
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKar_id() {
        return kar_id;
    }

    public void setKar_id(String kar_id) {
        this.kar_id = kar_id;
    }
}
