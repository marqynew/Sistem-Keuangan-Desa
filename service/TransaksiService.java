package service;

import model.Transaksi;

import java.time.LocalDate;
import java.util.List;

public interface TransaksiService {

    void tambahTransaksi(Transaksi transaksi);

    List<Transaksi> getSemuaTransaksi();

    List<Transaksi> cariTransaksi(String keyword);

    List<Transaksi> filterTransaksi(LocalDate dari, LocalDate sampai, String kategori, Boolean isPemasukan);

    Transaksi getTransaksiById(int id);

    boolean hapusTransaksi(int id);

    boolean editTransaksi(int id, Transaksi transaksiBaru);

    double getTotalPemasukan();

    double getTotalPengeluaran();

    double getSaldoSaatIni();

}
