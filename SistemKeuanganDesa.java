package main;

import service.TransaksiService;
import service.TransaksiServiceImpl;
import cli.CLIHandler;

public class SistemKeuanganDesa {
    public static void main(String[] args) {
        // Inisialisasi service
        TransaksiService transaksiService = new TransaksiServiceImpl();

        // Inisialisasi dan jalankan CLI
        CLIHandler cli = new CLIHandler(transaksiService);
        cli.tampilkanMenuUtama();
    }
}
