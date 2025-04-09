package service;

import model.Transaksi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransaksiServiceImpl implements TransaksiService {

    private List<Transaksi> daftarTransaksi = new ArrayList<>();

    @Override
    public void tambahTransaksi(Transaksi transaksi) {
        daftarTransaksi.add(transaksi);
    }

    @Override
    public List<Transaksi> getSemuaTransaksi() {
        return new ArrayList<>(daftarTransaksi);
    }

    @Override
    public List<Transaksi> cariTransaksi(String keyword) {
        return daftarTransaksi.stream()
                .filter(t -> t.getDeskripsi().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaksi> filterTransaksi(LocalDate dari, LocalDate sampai, String kategori, Boolean isPemasukan) {
        return daftarTransaksi.stream()
                .filter(t -> (dari == null || !t.getTanggal().isBefore(dari)) &&
                             (sampai == null || !t.getTanggal().isAfter(sampai)) &&
                             (kategori == null || t.getKategori().equalsIgnoreCase(kategori)) &&
                             (isPemasukan == null || t.isPemasukan() == isPemasukan))
                .collect(Collectors.toList());
    }

    @Override
    public Transaksi getTransaksiById(int id) {
        return daftarTransaksi.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean hapusTransaksi(int id) {
        return daftarTransaksi.removeIf(t -> t.getId() == id);
    }

    @Override
    public boolean editTransaksi(int id, Transaksi transaksiBaru) {
        Transaksi lama = getTransaksiById(id);
        if (lama != null) {
            lama.setTanggal(transaksiBaru.getTanggal());
            lama.setNominal(transaksiBaru.getNominal());
            lama.setDeskripsi(transaksiBaru.getDeskripsi());
            lama.setKategori(transaksiBaru.getKategori());
            lama.setPemasukan(transaksiBaru.isPemasukan());
            return true;
        }
        return false;
    }

    @Override
    public double getTotalPemasukan() {
        return daftarTransaksi.stream()
                .filter(Transaksi::isPemasukan)
                .mapToDouble(Transaksi::getNominal)
                .sum();
    }

    @Override
    public double getTotalPengeluaran() {
        return daftarTransaksi.stream()
                .filter(t -> !t.isPemasukan())
                .mapToDouble(Transaksi::getNominal)
                .sum();
    }

    @Override
    public double getSaldoSaatIni() {
        return getTotalPemasukan() - getTotalPengeluaran();
    }
}
