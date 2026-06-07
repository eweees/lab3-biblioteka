package org.example.library.sync;

import org.example.library.dao.BookDAO;
import org.example.library.model.Book;

import java.util.List;

public class SyncService {

    private final BookDAO memoryDao;
    private final BookDAO fileDao;
    private final BookDAO dbDao;

    public SyncService(BookDAO memoryDao, BookDAO fileDao, BookDAO dbDao) {
        this.memoryDao = memoryDao;
        this.fileDao = fileDao;
        this.dbDao = dbDao;
    }

    public void syncAllFromMemory() {

        List<Book> books = memoryDao.getAll();

        fileDao.clear();
        dbDao.clear();

        for (Book b : books) {
            fileDao.add(b);
            dbDao.add(b);
        }
    }

    private void sync(BookDAO dao, List<Book> books) {

        for (Book b : dao.getAll()) {
            dao.delete(b.getId());
        }

        for (Book b : books) {
            dao.add(b);
        }
    }
}