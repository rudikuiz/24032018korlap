package com.piramidsoft.korlap.models;

/**
 */

public class NotesModel {
    String Tgl,Notes;

    public NotesModel() {
    }

    public NotesModel(String tgl, String notes) {
        Tgl = tgl;
        Notes = notes;
    }

    public String getTgl() {
        return Tgl;
    }

    public void setTgl(String tgl) {
        Tgl = tgl;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }
}
