package org.example.library.dao;

import org.example.library.model.Book;
import java.util.ArrayList;
import java.util.List;

public class ListBookDAO implements BookDAO {

    private final List<Book> books = new ArrayList<>();

    public ListBookDAO() {
        // 14 интересных русских книг
        books.add(new Book(1, "Мастер и Маргарита", "Михаил Булгаков", 1967, "Доступна"));
        books.add(new Book(2, "Преступление и наказание", "Фёдор Достоевский", 1866, "Выдана"));
        books.add(new Book(3, "Война и мир", "Лев Толстой", 1869, "Доступна"));
        books.add(new Book(4, "Анна Каренина", "Лев Толстой", 1878, "Доступна"));
        books.add(new Book(5, "Братья Карамазовы", "Фёдор Достоевский", 1880, "Выдана"));
        books.add(new Book(6, "Евгений Онегин", "Александр Пушкин", 1833, "Доступна"));
        books.add(new Book(7, "Мёртвые души", "Николай Гоголь", 1842, "Доступна"));
        books.add(new Book(8, "Отцы и дети", "Иван Тургенев", 1862, "Выдана"));
        books.add(new Book(9, "Собачье сердце", "Михаил Булгаков", 1925, "Доступна"));
        books.add(new Book(10, "Доктор Живаго", "Борис Пастернак", 1957, "Доступна"));
        books.add(new Book(11, "Тихий Дон", "Михаил Шолохов", 1940, "Доступна"));
        books.add(new Book(12, "Мы", "Евгений Замятин", 1924, "Выдана"));
        books.add(new Book(13, "Петербург", "Андрей Белый", 1913, "Доступна"));
        books.add(new Book(14, "Обломов", "Иван Гончаров", 1859, "Доступна"));
    }
    @Override
    public void clear() {
        books.clear();
    }
    @Override
    public List<Book> getAll() {
        return new ArrayList<>(books);
    }

    @Override
    public Book getById(int id) {
        return books.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void add(Book book) {
        books.add(book);
    }

    @Override
    public void update(Book book) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == book.getId()) {
                books.set(i, book);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        books.removeIf(b -> b.getId() == id);
    }
}