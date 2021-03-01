package com.example.appzunsrs;

public class Ucenik {

    private String Ime;
    private String Prezime;
    private String Jmbg;
    private String Razred;
    private String Mail;
    private String KorisnickoIme;
    private String Lozinka;
    private String PLozinka;

    public Ucenik(){
    }

    public Ucenik(String Ime, String Prezime, String Jmbg, String Razred, String Mail, String KorisnickoIme, String Lozinka){
        this.Ime=Ime;
        this.Prezime=Prezime;
        this.Jmbg=Jmbg;
        this.Razred=Razred;
        this.Mail=Mail;
        this.KorisnickoIme=KorisnickoIme;
        this.Lozinka=Lozinka;
    }

    public String getIme() {
        return Ime;
    }

    public void setIme(String ime) {
        Ime = ime;
    }

    public String getPrezime() {
        return Prezime;
    }

    public void setPrezime(String prezime) {
        Prezime = prezime;
    }

    public String getJmbg() {
        return Jmbg;
    }

    public void setJmbg(String jmbg) {
        Jmbg = jmbg;
    }

    public String getRazred() {
        return Razred;
    }

    public void setRazred(String razred) {
        Razred = razred;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getKorisnickoIme() {
        return KorisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        KorisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return Lozinka;
    }

    public void setLozinka(String lozinka) {
        Lozinka = lozinka;
    }

    public String getPLozinka() {
        return PLozinka;
    }

    public void setPLozinka(String PLozinka) {
        this.PLozinka = PLozinka;
    }
}
