package storage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Transaksi;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class DataStorage {
    private static final String FILE_PATH = "data/transaksi.json";
    private static final Gson gson = new Gson();

    public static void simpan(List<Transaksi> transaksiList) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(transaksiList, writer);
        } catch (IOException e) {
            System.out.println(" Gagal menyimpan data: " + e.getMessage());
        }
    }

    public static List<Transaksi> muat() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Transaksi>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            System.out.println("⚠️ Tidak ditemukan data lama atau gagal dimuat.");
            return new java.util.ArrayList<>();
        }
    }
}
