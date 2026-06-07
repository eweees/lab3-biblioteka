package org.example.library.factory;

import org.example.library.dao.*;

public class BookFactory {

    public static final String MEMORY = "Память";
    public static final String FILE = "Файл";
    public static final String DB = "База";

    // 🔥 ВАЖНО: единый экземпляр памяти (сохраняет состояние)
    private static final ListBookDAO MEMORY_INSTANCE = new ListBookDAO();

    public static BookDAO create(String type) {

        if (MEMORY.equalsIgnoreCase(type)) {
            return MEMORY_INSTANCE; // ← НЕ создаём новый объект
        }

        if (FILE.equalsIgnoreCase(type)) {
            return new FileBookDAO();
        }

        if (DB.equalsIgnoreCase(type)) {
            return new DatabaseBookDAO();
        }

        throw new IllegalArgumentException("Неизвестный источник: " + type);
    }
}