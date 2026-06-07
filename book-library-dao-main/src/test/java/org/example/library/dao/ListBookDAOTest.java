package org.example.library.dao;

import org.example.library.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListBookDAOTest {

    private ListBookDAO dao;

    @BeforeEach
    void setUp() {
        dao = new ListBookDAO();
    }

    @Test
    void testПолучитьВсеКниги_ВозвращаетНепустойСписок() {
        List<Book> books = dao.getAll();
        assertFalse(books.isEmpty(), "Список книг не должен быть пустым");
        assertTrue(books.size() >= 14, "Должно быть минимум 14 начальных книг");
    }

    @Test
    void testПолучитьПоId_ВозвращаетКорректнуюКнигу() {
        Book book = dao.getById(1);
        assertNotNull(book, "Книга с ID 1 должна существовать");
        assertEquals(1, book.getId());
        assertEquals("Мастер и Маргарита", book.getTitle());
    }

    @Test
    void testДобавитьКнигу_УвеличиваетРазмерСписка() {
        int sizeBefore = dao.getAll().size();

        dao.add(new Book(99, "Тестовая книга", "Тестовый Автор", 2025, "Доступна"));

        assertEquals(sizeBefore + 1, dao.getAll().size(),
                "После добавления размер списка должен увеличиться на 1");
    }

    @Test
    void testОбновитьКнигу_ИзменяетДанные() {
        Book book = dao.getById(1);
        assertNotNull(book);

        book.setStatus("Выдана");
        dao.update(book);

        Book updatedBook = dao.getById(1);
        assertEquals("Выдана", updatedBook.getStatus(),
                "Статус книги должен измениться после обновления");
    }

    @Test
    void testУдалитьКнигу_УдаляетИзСписка() {
        int sizeBefore = dao.getAll().size();

        dao.delete(1);

        assertEquals(sizeBefore - 1, dao.getAll().size(),
                "После удаления размер списка должен уменьшиться на 1");
        assertNull(dao.getById(1), "Удалённая книга не должна находиться по ID");
    }
}