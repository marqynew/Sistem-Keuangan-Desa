package cli;

import model.Transaksi;
import service.TransaksiService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;


public class CLIHandler {
    private TransaksiService transaksiService;
    private Scanner scanner = new Scanner(System.in);

    public CLIHandler(TransaksiService transaksiService) {
        this.transaksiService = transaksiService;
    }

    public void tampilkanMenuUtama() {
        int pilihan;
        do {
            System.out.println("\n=== SISTEM KEUANGAN PERDESAAN ===");
            System.out.println("1. Tambah Transaksi");
            System.out.println("2. Lihat Semua Transaksi");
            System.out.println("3. Cari Transaksi");
            System.out.println("4. Hapus Transaksi");
            System.out.println("5. Edit Transaksi");
            System.out.println("6. Ringkasan Keuangan");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            try {
                pilihan = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Masukkan angka.");
                continue;
            }

            switch (pilihan) {
                case 1 -> tambahTransaksi();
                case 2 -> lihatSemuaTransaksi();
                case 3 -> cariTransaksi();
                case 4 -> hapusTransaksi();
                case 5 -> editTransaksi();
                case 6 -> ringkasanKeuangan();
                case 0 -> System.out.println("Terima kasih telah menggunakan sistem.");
                default -> System.out.println("Pilihan tidak tersedia.");
            }
        } while (true);
    }

    private void tambahTransaksi() {
        try {
            LocalDate tanggal = null;
            while (tanggal == null) {
                System.out.print("Tanggal (yyyy-mm-dd): ");
                String input = scanner.nextLine();
                try {
                    tanggal = LocalDate.parse(input);
                } catch (DateTimeParseException e) {
                    System.out.println("Format tanggal tidak valid.");
                }
            }

            double nominal = -1;
            while (nominal < 0) {
                System.out.print("Nominal: ");
                String input = scanner.nextLine();
                try {
                    nominal = Double.parseDouble(input);
                    if (nominal < 0) System.out.println("Nominal tidak boleh negatif.");
                } catch (NumberFormatException e) {
                    System.out.println("Masukkan angka yang valid.");
                }
            }

            String deskripsi = "";
            while (deskripsi.isBlank()) {
                System.out.print("Deskripsi: ");
                deskripsi = scanner.nextLine();
                if (deskripsi.isBlank()) System.out.println("Deskripsi tidak boleh kosong.");
            }

            String kategori = "";
            while (kategori.isBlank()) {
                System.out.print("Kategori: ");
                kategori = scanner.nextLine();
                if (kategori.isBlank()) System.out.println("Kategori tidak boleh kosong.");
            }

            int jenis = 0;
            while (jenis != 1 && jenis != 2) {
                System.out.print("Jenis (1=Pemasukan, 2=Pengeluaran): ");
                String input = scanner.nextLine();
                try {
                    jenis = Integer.parseInt(input);
                    if (jenis != 1 && jenis != 2) {
                        System.out.println("Masukkan hanya angka 1 atau 2.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input harus berupa angka.");
                }
            }

            boolean isPemasukan = (jenis == 1);
            Transaksi transaksi = new Transaksi(tanggal, nominal, deskripsi, kategori, isPemasukan);
            transaksiService.tambahTransaksi(transaksi);
            System.out.println("Transaksi berhasil ditambahkan.");

        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat menambahkan transaksi: " + e.getMessage());
        }
    }

    private void lihatSemuaTransaksi() {
        List<Transaksi> list = transaksiService.getSemuaTransaksi();
        if (list.isEmpty()) {
            System.out.println("Belum ada transaksi yang tercatat.");
        } else {
            list.forEach(System.out::println);
        }
    }

    private void cariTransaksi() {
        System.out.print("Masukkan keyword: ");
        String keyword = scanner.nextLine();
        List<Transaksi> hasil = transaksiService.cariTransaksi(keyword);
        if (hasil.isEmpty()) {
            System.out.println("Transaksi tidak ditemukan.");
        } else {
            hasil.forEach(System.out::println);
        }
    }

    private void hapusTransaksi() {
        System.out.print("Masukkan ID transaksi yang akan dihapus: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            boolean berhasil = transaksiService.hapusTransaksi(id);
            System.out.println(berhasil ? "Transaksi berhasil dihapus." : "Transaksi tidak ditemukan.");
        } catch (NumberFormatException e) {
            System.out.println("ID harus berupa angka.");
        }
    }

    private void editTransaksi() {
        System.out.print("Masukkan ID transaksi yang akan diedit: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Transaksi lama = transaksiService.getTransaksiById(id);
            if (lama == null) {
                System.out.println("Transaksi tidak ditemukan.");
                return;
            }

            System.out.println("Masukkan data baru (biarkan kosong jika tidak ingin diubah):");

            System.out.print("Tanggal baru (yyyy-mm-dd): ");
            String inputTanggal = scanner.nextLine();
            LocalDate tanggal = inputTanggal.isEmpty() ? lama.getTanggal() : LocalDate.parse(inputTanggal);

            System.out.print("Nominal baru: ");
            String inputNominal = scanner.nextLine();
            double nominal = inputNominal.isEmpty() ? lama.getNominal() : Double.parseDouble(inputNominal);

            System.out.print("Deskripsi baru: ");
            String deskripsi = scanner.nextLine();
            if (deskripsi.isEmpty()) deskripsi = lama.getDeskripsi();

            System.out.print("Kategori baru: ");
            String kategori = scanner.nextLine();
            if (kategori.isEmpty()) kategori = lama.getKategori();

            System.out.print("Jenis baru (1=Pemasukan, 2=Pengeluaran): ");
            String inputJenis = scanner.nextLine();
            boolean isPemasukan = inputJenis.isEmpty() ? lama.isPemasukan() : inputJenis.equals("1");

            Transaksi baru = new Transaksi(tanggal, nominal, deskripsi, kategori, isPemasukan);
            transaksiService.editTransaksi(id, baru);

            System.out.println("Transaksi berhasil diperbarui.");

        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat memperbarui transaksi: " + e.getMessage());
        }
    }

    private void ringkasanKeuangan() {
        System.out.println("\n=== Ringkasan Keuangan ===");
        System.out.printf("Total Pemasukan  : Rp%.2f\n", transaksiService.getTotalPemasukan());
        System.out.printf("Total Pengeluaran: Rp%.2f\n", transaksiService.getTotalPengeluaran());
        System.out.printf("Saldo Saat Ini   : Rp%.2f\n", transaksiService.getSaldoSaatIni());
    }
}
