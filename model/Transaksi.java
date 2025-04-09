package model;

import java.time.LocalDate;

public class Transaksi {
    private static int counter = 1; // Untuk auto-ID
    private int id;
    private LocalDate tanggal;
    private double nominal;
    private String deskripsi;
    private Kategori kategori;

    private boolean isPemasukan;

    public Transaksi(LocalDate tanggal, double nominal, String deskripsi, String kategori, boolean isPemasukan) {
        this.id = counter++;
        this.tanggal = tanggal;
        this.nominal = nominal;
        this.deskripsi = deskripsi;
        this.kategori = kategori;
        this.isPemasukan = isPemasukan;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public double getNominal() {
        return nominal;
    }

    public void setNominal(double nominal) {
        this.nominal = nominal;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public boolean isPemasukan() {
        return isPemasukan;
    }

    public void setPemasukan(boolean isPemasukan) {
        this.isPemasukan = isPemasukan;
    }

    @Override
    public String toString() {
        return String.format(
            "[ID: %d] [%s] %s: Rp%.2f | Kategori: %s | %s",
            id,
            tanggal,
            isPemasukan ? "Pemasukan" : "Pengeluaran",
            nominal,
            kategori,
            deskripsi
        );
    }
}
