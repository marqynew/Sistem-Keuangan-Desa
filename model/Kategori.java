package model;

public enum Kategori {
    DANA_DESA,
    BANTUAN_PEMERINTAH,
    OPERASIONAL,
    PERTANIAN,
    LAINNYA;

    public static boolean isValid(String input) {
        for (Kategori k : values()) {
            if (k.name().equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }
}
