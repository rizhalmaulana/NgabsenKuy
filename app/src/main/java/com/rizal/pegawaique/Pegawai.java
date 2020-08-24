package com.rizal.pegawaique;

class Pegawai {

    public Pegawai(){

    }

    public Pegawai(String uid, String nama, String user, String email, String pass, String phone, String alamat) {
        this.uid = uid;
        this.nama = nama;
        this.user = user;
        this.email = email;
        this.pass = pass;
        this.phone = phone;
        this.alamat = alamat;
    }

    public Pegawai(String nama, String user, String email, String pass, String phone, String alamat) {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    private String uid;
    private String nama;
    private String user;
    private String email;
    private String pass;
    private String phone;
    private String alamat;
}
