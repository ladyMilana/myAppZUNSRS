package com.example.appzunsrs;

public class Radnje {


    private String Naziv;
    private String Adresa;
    private  String Telefon;

    public Radnje() {
    }

    public Radnje(String naziv, String adresa, String telefon) {
        Naziv = naziv;
        Adresa = adresa;
        Telefon = telefon;
    }

    public String getNaziv() {
        return Naziv;
    }

    public void setNaziv(String naziv) {
        Naziv = naziv;
    }

    public String getAdresa() {
        return Adresa;
    }

    public void setAdresa(String adresa) {
        Adresa = adresa;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }
}
