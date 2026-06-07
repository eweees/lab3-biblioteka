package org.example.library.dao;

import org.example.library.model.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBookDAO implements BookDAO {

    private static final String FILE_NAME = "books.txt";

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(";");

                if (parts.length == 5) {

                    Book book = new Book(
                            Integer.parseInt(parts[0]),
                            parts[1],
                            parts[2],
                            Integer.parseInt(parts[3]),
                            parts[4]
                    );

                    books.add(book);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public Book getById(int id) {

        return getAll().stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void add(Book book) {

        List<Book> books = getAll();
        books.add(book);

        saveAll(books);
    }

    @Override
    public void update(Book book) {

        List<Book> books = getAll();

        for (int i = 0; i < books.size(); i++) {

            if (books.get(i).getId() == book.getId()) {
                books.set(i, book);
                break;
            }
        }

        saveAll(books);
    }

    @Override
    public void delete(int id) {

        List<Book> books = getAll();

        books.removeIf(book -> book.getId() == id);

        saveAll(books);
    }
    public void clear() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(""); // очищаем файл
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveAll(List<Book> books) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Book book : books) {

                writer.write(
                        book.getId() + ";" +
                                book.getTitle() + ";" +
                                book.getAuthor() + ";" +
                                book.getYear() + ";" +
                                book.getStatus()
                );

                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}