package org.example.library.dao;

import org.example.library.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseBookDAO implements BookDAO {

    private static final String URL = "jdbc:sqlite:library.db";

    public DatabaseBookDAO() {
        createTableIfNotExists();
    }

    // 🔥 создаём таблицу, если её нет
    private void createTableIfNotExists() {

        try (Connection conn = DriverManager.getConnection(URL);
             Statement st = conn.createStatement()) {

            st.execute("""
                CREATE TABLE IF NOT EXISTS books (
                    id INTEGER PRIMARY KEY,
                    title TEXT,
                    author TEXT,
                    year INTEGER,
                    status TEXT
                )
            """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔥 ЧИТАЕМ ВСЕ КНИГИ
    @Override
    public List<Book> getAll() {

        List<Book> list = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM books")) {

            while (rs.next()) {
                list.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year"),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Book getById(int id) {
        return getAll().stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // 🔥 ДОБАВЛЕНИЕ
    @Override
    public void add(Book book) {

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO books (id, title, author, year, status) VALUES (?, ?, ?, ?, ?)")) {

            ps.setInt(1, book.getId());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setInt(4, book.getYear());
            ps.setString(5, book.getStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔥 ОБНОВЛЕНИЕ
    @Override
    public void update(Book book) {

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE books SET title=?, author=?, year=?, status=? WHERE id=?")) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getYear());
            ps.setString(4, book.getStatus());
            ps.setInt(5, book.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔥 УДАЛЕНИЕ
    @Override
    public void delete(int id) {

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM books WHERE id=?")) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔥 НОВОЕ: очистка базы (ВАЖНО ДЛЯ СИНХРОНИЗАЦИИ)
    public void clear() {

        try (Connection conn = DriverManager.getConnection(URL);
             Statement st = conn.createStatement()) {

            st.execute("DELETE FROM books");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}