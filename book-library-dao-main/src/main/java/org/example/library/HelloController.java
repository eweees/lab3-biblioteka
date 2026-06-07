package org.example.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import org.example.library.controller.BookController;
import org.example.library.dao.BookDAO;
import org.example.library.factory.BookFactory;
import org.example.library.model.Book;
import org.example.library.sync.SyncService;

public class HelloController {

    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book, Integer> colId;
    @FXML private TableColumn<Book, String> colTitle;
    @FXML private TableColumn<Book, String> colAuthor;
    @FXML private TableColumn<Book, Integer> colYear;
    @FXML private TableColumn<Book, String> colStatus;

    @FXML private TextField fieldTitle;
    @FXML private TextField fieldAuthor;
    @FXML private TextField fieldYear;
    @FXML private TextArea logArea;
    @FXML private ComboBox<String> sourceBox;

    private BookController controller;
    private ObservableList<Book> bookList = FXCollections.observableArrayList();

    // 🔥 DAO как поля (ВАЖНО)
    private BookDAO memoryDao;
    private BookDAO fileDao;
    private BookDAO dbDao;

    private SyncService syncService;

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        bookTable.setItems(bookList);

        sourceBox.getItems().addAll(
                BookFactory.MEMORY,
                BookFactory.FILE,
                BookFactory.DB
        );

        sourceBox.setValue(BookFactory.MEMORY);


        memoryDao = BookFactory.create(BookFactory.MEMORY);
        fileDao = BookFactory.create(BookFactory.FILE);
        dbDao = BookFactory.create(BookFactory.DB);

        syncService = new SyncService(memoryDao, fileDao, dbDao);
    }

    @FXML
    public void onLoad() {

        BookDAO dao = BookFactory.create(sourceBox.getValue());
        controller = new BookController(dao);

        bookList.setAll(controller.loadAll());

        appendLog("Загружено книг: " + bookList.size());
    }

    @FXML
    public void onAdd() {

        String title = fieldTitle.getText();
        String author = fieldAuthor.getText();
        String yearStr = fieldYear.getText();

        if (title.isEmpty() || author.isEmpty() || yearStr.isEmpty()) {
            showAlert("Заполните все поля");
            return;
        }

        int year = Integer.parseInt(yearStr);

        int id = controller.getNextId();

        Book book = new Book(id, title, author, year, "Доступна");

        controller.addBook(book);
        bookList.add(book);

        appendLog("Добавлена книга: " + title);
    }

    @FXML
    public void onDelete() {

        Book selected = bookTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Выберите книгу");
            return;
        }

        controller.deleteBook(selected.getId());
        bookList.remove(selected);

        appendLog("Удалена книга: " + selected.getTitle());
    }

    @FXML
    public void onToggleStatus() {

        Book selected = bookTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Выберите книгу");
            return;
        }

        controller.toggleStatus(selected);
        bookTable.refresh();

        appendLog("Изменён статус: " + selected.getTitle());
    }
    @FXML
    public void onSync() {

        if (syncService == null) {
            showAlert("Сначала нажмите 'Загрузить'");
            return;
        }

        syncService.syncAllFromMemory();
        appendLog("Синхронизация выполнена");
    }

    private void appendLog(String msg) {
        if (logArea != null) {
            logArea.appendText(msg + "\n");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Внимание");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}