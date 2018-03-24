package com.piramidsoft.korlap.models;

/**
 */

public class HistoryModel {
    String Tgl,Nominal;

    public HistoryModel() {
    }

    public HistoryModel(String tgl, String nominal) {
        Tgl = tgl;
        Nominal = nominal;
    }

    public String getTgl() {
        return Tgl;
    }

    public void setTgl(String tgl) {
        Tgl = tgl;
    }

    public String getNominal() {
        return Nominal;
    }

    public void setNominal(String nominal) {
        Nominal = nominal;
    }
}
